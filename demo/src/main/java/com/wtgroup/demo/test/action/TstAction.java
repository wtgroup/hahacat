package com.wtgroup.demo.test.action;

import com.wtgroup.demo.web.action.CommonAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class TstAction extends CommonAction {

    @Action(value = "tstAction_test")
    public void test() throws IOException {

        String s = "{\"name\":\"zhangfei\"}";
        // 必须这么转一下, 否则前台报 Json.parse错误
//        String json = JSONObject.fromObject(s).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(s);

    }
//    @Action(value = "tstAction_test")
    public void test_bak() throws IOException {

        JSONObject o = new JSONObject();
        o.put("name", "zhangfei");
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(o.toString());

    }
}
