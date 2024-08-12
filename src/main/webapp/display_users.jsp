<%@page import="com.eazydeals.entities.Message"%>
<%@page import="com.eazydeals.dao.UserDao"%>
<%@page errorPage="error_exception.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
Admin activeAdmin = (Admin) session.getAttribute("activeAdmin");
if (activeAdmin == null) {
	Message message = new Message("You are not logged in! Login first!!", "error", "alert-danger");
	session.setAttribute("message", message);
	response.sendRedirect("adminlogin.jsp");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>View User's</title>
<%@include file="Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="Components/navbar.jsp"%>

	<div class="container-fluid px-5 py-3">
		<table class="table table-hover">
			<tr class="text-center table-primary" style="font-size: 18px;">
				<th>Tên người dùng</th>
				<th>Email</th>
				<th>Số điện thoại.</th>
				<th>Giới thiệu</th>
				<th>Địa chỉ</th>
				<th>Ngày đăng kí</th>
				<th>Thao tác</th>
			</tr>
			<%
			UserDao userDao = new UserDao(ConnectionProvider.getConnection());
			List<User> userList = userDao.getAllUser();
			for (User u : userList) {
			%>
			<tr>
				<td><%=u.getUserName()%></td>
				<td><%=u.getUserEmail()%></td>
				<td><%=u.getUserPhone()%></td>
				<td><%=u.getUserGender()%></td>
				<td><%=userDao.getUserAddress(u.getUserId())%></td>
				<td><%=u.getDateTime()%></td>
				<td><a
					href="UpdateUserServlet?operation=deleteUser&uid=<%=u.getUserId()%>"
					role="button" class="btn btn-danger">Xóa</a></td>
			</tr>
			<%
			}
			%>
		</table>
	</div>
</body>
</html>