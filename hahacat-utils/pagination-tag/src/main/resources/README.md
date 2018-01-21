# JSP分页标签

 
## 使用说明
### 1. 下载jar包至本地



### 2. 将jar包安装至本地仓库.
```
mvn install:install-file -Dfile=pagination-tag-1.0.jar -DgroupId=com.wtgroup.u tils -DartifactId=pagination-tag -Dversion=1.0 -Dpackaging=jar
```


### 3. web工程中引入jar包
```
        <!--引入自定义jsp标签  分页导航条-->
        <dependency>
            <groupId>com.wtgroup.utils</groupId>
            <artifactId>pagination-tag</artifactId>
            <version>1.0</version>
        </dependency>

```


### 4. jsp页面引入约束
jsp页面头声明处引入标签库.
```
<%@ taglib prefix="wt" uri="http://WTGroup.cn/common/"%>
```

### 5. 使用分页导航标签
在需要显示分页导航出输入类似如下格式的HTML:

```
<div class="col-md-12 text-right">
  <wt:page url="${pageContext.request.contextPath }/customer/list.action" />
</div>
```
其中:
- **url** -必须. 需要指向能够获取`Page`对象的控制层方法. 保证正确获取分页所需的数据.
- **pageBeanName** -默认为"page".
- **number** -默认5, 指定了显示的总页数.

## 效果

![效果演示](http://upload-images.jianshu.io/upload_images/3250317-46dd1c57e7481062.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 接口

本标签生成的导航, 会在您指定的`url`路径后添加两个动态参数传递至后台.
- `currentPage` : 当前页号, 动态获取, 默认为1.
- `pageSize` 页大小, 可由用户指定, 没有则默认5.




## 特色

- 基于`bootstrap`**简洁大方**的前端样式.
- 显示的总页数能够**弹性伸缩**. 若设置了显示总页数为5, 那么只要总页数多于5, 则能够显示刚好前后各2两个页码; 如果不足5, 则会自动在前或后面填补不足的页码.



