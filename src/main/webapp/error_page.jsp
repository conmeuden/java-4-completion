<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Không tìm thấy trang này</title>
<%@include file="Components/common_css_js.jsp"%>
</head>
<body>

	<div class="container p-5 text-center">
		<img src="Images\no-results.png" class="img-fluid" style="max-width: 400px;">
		<h1>Không tìm thấy trang này...</h1>
		<a href="index.jsp" class="btn btn-outline-primary btn-lg mt-3">Quay lại trang chủ</a>
	</div>
</body>
</html>