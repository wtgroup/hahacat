<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改订单信息</title>

</head>
<body>
	<!-- 附件上传时再用：enctype="multipart/form-data" -->
	<form id="ordersForm" action="${pageContext.request.contextPath }/orders/updateOrders.action" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${ordersCustom.id }" /> 修改商品信息：
		<table width="100%" border=1>
			<tr>
				<td>用户ID</td>
				<td><input type="text" name="userId" value="${ordersCustom.userId }" /></td>
			</tr>
			<tr>
				<td>订单编号</td>
				<td><input type="text" name="number" value="${ordersCustom.number }" /></td>
			</tr>
			<tr>
				<td>订单生成日期</td>
				<td><input type="text" name="createtime"
					value="<fmt:formatDate value="${ordersCustom.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
			</tr>
			 <tr>
				<td>商品图片</td>
				<td><c:if test="${ordersCustom.image !=null}">
						<img src="/io/${ordersCustom.image}" width=100 height=100 />
						<br />
					</c:if> <input type="file" name="imageFile" /></td>
			</tr>
			<tr>
				<td>订单描述</td>
				<td><textarea rows="3" cols="30" name="note">${ordersCustom.note }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="提交" />
				</td>
			</tr>
		</table>

	</form>
</body>

</html>