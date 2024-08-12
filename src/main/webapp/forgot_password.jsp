<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Quên mật khẩu</title>
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

	<div class="container-fluid ">
		<div class="row mt-5">
			<div class="col-md-4 offset-md-4">
				<div class="card">
					<div class="card-body px-5">

						<div class="container text-center">
							<img src="Images/forgot-password.png" style="max-width: 100px;"
								class="img-fluid">
						</div>
						<h3 class="text-center mt-3">Quên mật khẩu</h3>
						<%@include file="Components/alert_message.jsp"%>

						<!--change password-->
						<form action="ChangePasswordServlet" method="post">
							<div class="mb-3">
								<label class="form-label">Email</label> <input type="email"
									name="email" placeholder="Nhập email..." class="form-control"
									required>
							</div>
							<div class="container text-center">
								<button type="submit" class="btn btn-outline-primary me-3">Gửi
									mã xác nhận</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>