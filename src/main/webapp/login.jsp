<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Login</title>
<%@include file="Components/common_css_js.jsp"%>
<style>
label{
	font-weight: bold;
}
</style>
</head>
<body >  

	<!--navbar -->
	<%@include file="Components/navbar.jsp"%>

	<div class="container-fluid">
		<div class="row mt-5">
			<div class="col-md-4 offset-md-4">
				<div class="card">
					<div class="card-body px-5">

						<div class="container text-center">
							<img src="Images/login.png" style="max-width: 100px;"
								class="img-fluid">
						</div>
						<h3 class="text-center">Đăng nhập</h3>
						<%@include file="Components/alert_message.jsp" %>
						
						<!--login-form-->
						<form id="login-form" action="LoginServlet" method="post">
							<input type="hidden" name="login" value="user"> 
							<div class="mb-3">
								<label class="form-label">Email</label> <input
									type="email" name="user_email" placeholder="Nhập email..."
									class="form-control" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Mật khẩu</label>
								<input type="password" name="user_password"
									placeholder="Nhập mật khẩu..." class="form-control" required>
							</div>
							<div id="login-btn" class="container text-center">
								<button type="submit" class="btn btn-outline-primary me-3"> Đăng nhập </button>
							</div>
						</form>
						<div class="mt-3 text-center">
							<h6><a href="forgot_password.jsp" style="text-decoration: none">Quên mật khẩu?</a></h6>
							<h6>
								Bạn chưa có tài khoản?<a href="register.jsp"
									style="text-decoration: none"> Đăng kí </a>
							</h6>
						</div>
					</div>  

				</div>
			</div>
		</div>
	</div>
</body>
</html>