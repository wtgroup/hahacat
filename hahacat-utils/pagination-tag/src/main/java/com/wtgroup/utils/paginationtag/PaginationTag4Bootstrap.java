package com.wtgroup.utils.paginationtag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Map;


/**
 * @ClassName: PaginationTag4Bootstrap
 * @Company: http://www.itcast.cn/
 * @Description: 自定义标签，配合分页对象page，实现分页功能。      显示格式 ：上一页 1 2 3 4 5 下一页
 * @date 2016年7月25日 下午4:08:32
 */
public class PaginationTag4Bootstrap extends TagSupport {

    static final long serialVersionUID = 2372405317744358833L;

    /**
     * request 中用于保存Page<E> 对象的变量名,默认为“page”
     */
    private String pageBeanName = "page";

    /**
     * 分页跳转的url地址,此属性必须
     */
    private String url = null;

    /**
     * 显示页码数量
     */
    private int number = 5;

    @SuppressWarnings("rawtypes")
    @Override
    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Page page = (Page) request.getAttribute(pageBeanName);   //获取Request域中的page<T>对象
        if (page == null)
            return SKIP_BODY;
        url = resolveUrl(url, pageContext);
        try {
            //计算总页数
            Integer pageCount = page.getPageCount();
            if (pageCount == null) {
                pageCount = page.getRowCount().intValue() / page.getPageSize();
                if (page.getRowCount() % page.getPageSize() > 0) {
                    pageCount++;
                }
            }
            writer.print("<nav><ul class=\"pagination\">");
            //显示"首页"
            String beginUrl = append(url, "currentPage", 1);
            if (page.getCurrentPage() == 1) {
                //激活状态
                writer.print("<li class=\"active\"><a href=\"#\">" + "首页" + "<span class=\"sr-only\">(current)</span></a></li>");
            } else {
                //非激活状态
                writer.print("<li><a href=\"" + beginUrl + "\">" + "首页" + "<span class=\"sr-only\">(current)</span></a></li>");
            }

            //显示“上一页”按钮
            //当前页>1 上一页可点击跳转; ==1 上一页不可点击
            if (page.getCurrentPage() > 1) {
                String preUrl = append(url, "currentPage", page.getCurrentPage() - 1);    //请求页号
                preUrl = append(preUrl, "pageSize", page.getPageSize());    //请求页大小
                writer.print("<li><a href=\"" + preUrl + "\">上一页</a></li>");
            } else {
                writer.print("<li class=\"disabled\"><a href=\"#\">上一页</a></li>");
            }
            //显示当前页码的前2页码和后两页码 
            //若1 则 1 2 3 4 5, 若2 则 1 2 3 4 5, 若3 则1 2 3 4 5,
            //若4 则 2 3 4 5 6 ,若10  则 8 9 10 11 12
            int around = number / 2;  //前后各一半
            int indexPage = (page.getCurrentPage() - around > 0) ? page.getCurrentPage() - around : 1;   //计算初始页号
            //[[
            //经分析, 旧的逻辑会造成尾部几页, 显示不足number指定的页数. 但首部几页不会出现
            //优化: 根据初始起始页号和总页数计算将要显示页数, 得出它和number的差距, 如果不足, 则在前面补
            int maxShowedNumber = pageCount - indexPage + 1;
            int startPageWillOffset = number - maxShowedNumber > 0 ? number - maxShowedNumber : 0;
            indexPage = indexPage - startPageWillOffset > 0 ? indexPage - startPageWillOffset : 1;    //初始起始页号向前位移后不能小于1
            //]]
            for (int i = 1; i <= number && indexPage <= pageCount; indexPage++, i++) {
                if (indexPage == page.getCurrentPage()) {   //当前页页码样式为激活状态
                    writer.print("<li class=\"active\"><a href=\"#\">" + indexPage + "<span class=\"sr-only\">(current)</span></a></li>");
                    continue;
                }
                String pageUrl = append(url, "currentPage", indexPage);
                pageUrl = append(pageUrl, "pageSize", page.getPageSize());
                writer.print("<li><a href=\"" + pageUrl + "\">" + indexPage + "</a></li>");
            }
            //显示“下一页”按钮
            if (page.getCurrentPage() < pageCount) {
                String nextUrl = append(url, "currentPage", page.getCurrentPage() + 1);
                nextUrl = append(nextUrl, "pageSize", page.getPageSize());
                writer.print("<li><a href=\"" + nextUrl + "\">下一页</a></li>");
            } else {
                writer.print("<li class=\"disabled\"><a href=\"#\">下一页</a></li>");
            }

            //显示"末页"
            String endUrl = append(url, "currentPage", page.getPageCount());
            if (page.getCurrentPage() == page.getPageCount()) {
                //激活状态
                writer.print("<li class=\"active\"><a href=\"#\">" + "末页" + "<span class=\"sr-only\">(current)</span></a></li>");
            } else {
                //非激活状态
                writer.print("<li><a href=\"" + endUrl + "\">" + "末页" + "<span class=\"sr-only\">(current)</span></a></li>");
            }

            writer.print("</nav>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    private String append(String url, String key, int value) {

        return append(url, key, String.valueOf(value));
    }

    /**
     * 为url 增加参数对儿
     *
     * @param url
     * @param key
     * @param value
     * @return
     */
    private String append(String url, String key, String value) {
        if (url == null || url.trim().length() == 0) {
            return "";
        }

        if (url.indexOf("?") == -1) {   //源url没有带参数
            url = url + "?" + key + "=" + value;
        } else {
            if (url.endsWith("?")) {
                url = url + key + "=" + value;
            } else {
                url = url + "&amp;" + key + "=" + value;    //新增键值对
            }
        }

        return url;
    }

    /**
     * 为url 添加翻页请求参数
     *
     * @param url
     * @param pageContext
     * @return
     * @throws JspException
     */
    @SuppressWarnings("rawtypes")
    private String resolveUrl(String url, javax.servlet.jsp.PageContext pageContext) throws JspException {
        //UrlSupport.resolveUrl(url, context, pageContext)
        //获取Request中参数map
        Map params = pageContext.getRequest().getParameterMap();
        for (Object key : params.keySet()) {
            //currentPage和pageSize除外, 这两个生成分页html时在处理, 它们需要动态生成.
            if ("currentPage".equals(key) || "pageSize".equals(key)) continue;
            Object value = params.get(key);
            if (value == null) continue;
            if (value.getClass().isArray()) {
                url = append(url, key.toString(), ((String[]) value)[0]);    //?value一定就是string[]吗?<-url中的参数一定是字符
            } else if (value instanceof String) {
                url = append(url, key.toString(), value.toString());
            }
        }
        return url;
    }


    /**
     * @return the bean
     */
    public String getPageBeanName() {
        return pageBeanName;
    }

    /**
     * @param pageBeanName the bean to set
     */
    public void setPageBeanName(String pageBeanName) {
        this.pageBeanName = pageBeanName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
