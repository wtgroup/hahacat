<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查询商品列表</title>
</head>
<body>


<form action="${pageContext.request.contextPath }/orders/queryOrders.action" method="post">
    查询条件：
    <table width="100%" border=1>
        <tr>
            <td><input type="submit" value="查询"/></td>
        </tr>
    </table>
</form>

<%--<form action="${pageContext.request.contextPath }/orders/deleteInBatch.action" method="post"">--%>
<form action="${pageContext.request.contextPath }/orders/updateInBatch.action" method="post"
">
商品列表：
<table width="100%" border=1>
    <tr>
        <td><input type="checkbox" name="ids"></td>
        <td>订单ID</td>
        <td>用户ID</td>
        <td>订单生成日期</td>
        <td>订单描述</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${orders}" var="item" varStatus="vs">
        <tr>
            <td><input type="checkbox" name="ordersList[${vs.index}].ids" value="${item.id }"/></td>
            <%--订单ID  不可更改    --%>
            <td><input readonly="readonly" type="text" name="ordersList[${vs.index}].id" value="${item.id }"/></td>
            <td><input name="ordersList[${vs.index}].userId" value="${item.userId }"/> </td>
                <%--<td>${item.createtime}</td>--%>
            <td><input type="text" name="ordersList[${vs.index}].createtime" value="
<fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
            <td><input name="ordersList[${vs.index}].note" type="text" value="${item.note }"/></td>

            <td><a href="${pageContext.request.contextPath }/orders/editOrders.action?id=${item.id}">修改</a></td>

        </tr>
    </c:forEach>

</table>
<input type="submit" value="删除"/>
<input type="submit" value="保存所有修改"/>
</form>
</body>

</html>
