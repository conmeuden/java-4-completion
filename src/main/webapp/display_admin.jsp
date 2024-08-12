<%@page import="com.eazydeals.dao.AdminDao"%>
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
AdminDao adminDao = new AdminDao(ConnectionProvider.getConnection());
List<Admin> adminList = adminDao.getAllAdmin();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Thông tin Admin</title>
<%@include file="Components/common_css_js.jsp"%>
<style>
label {
	font-weight: bold;
}
</style>
</head>
<body>
	<!--navbar -->
	<%@include file="Components/navbar.jsp"%>

	<div class="container-fluid px-5 py-3">
		<div class="row">
			<div class="col-md-4">
				<div class="card">
					<div class="card-body px-3">
						<div class="container text-center">
							<img src="Images/admin.png" style="max-width: 100px;"
								class="img-fluid">
						</div>
						<h3 class="text-center">Thêm Admin</h3>
						<%@include file="Components/alert_message.jsp"%>

						<!--admin-form-->
						<form action="AdminServlet?operation=save" method="post">
							<div class="mb-3">
								<label class="form-label">Tên</label> <input type="text"
									name="name" placeholder="Nhập tên..." class="form-control"
									required>
							</div>
							<div class="mb-3">
								<label class="form-label">Email</label> <input type="email"
									name="email" placeholder="Nhập email.." class="form-control"
									required>
							</div>
							<div class="mb-3">
								<label class="form-label">Mật khẩu</label> <input
									type="password" name="password" placeholder="Nhập mật khẩu"
									class="form-control" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Số điện thoại</label> <input
									type="number" name="phone" placeholder="Nhập số điện thoại..."
									class="form-control" required>
							</div>
							<div class="d-grid gap-2 col-6 mx-auto py-3">
								<button type="submit" class="btn btn-primary me-3">Đăng
									kí</button>
							</div>
						</form>
					</div>

				</div>
			</div>
			<div class="col-md-8">
				<div class="card">
					<div class="card-body px-3">
						<table class="table table-hover">
							<tr class="text-center table-primary" style="font-size: 18px;">
								<th>Tên</th>
								<th>Email</th>
								<th>Số điện thoại</th>
								<th>Thao tác</th>
							</tr>
							<%
							for (Admin a : adminList) {
							%>
							<tr class="text-center">
								<td><%=a.getName() %></td>
								<td><%=a.getEmail() %></td>
								<td><%=a.getPhone() %></td>
								<td><a
									href="AdminServlet?operation=delete&id=<%=a.getId()%>"
									role="button" class="btn btn-danger">Xóa</a></td>
							</tr>
							<%
							}
							%>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>