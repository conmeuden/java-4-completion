<%@page import="com.eazydeals.dao.WishlistDao"%>
<%@page import="com.eazydeals.dao.ProductDao"%>
<%@page import="com.eazydeals.entities.Product"%>
<%@page import="com.eazydeals.entities.User"%>
<%@page import="com.eazydeals.dao.CategoryDao"%>
<%@page import="java.util.List"%>
<%@page import="com.eazydeals.helper.ConnectionProvider"%>


<%@page errorPage="error_exception.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
int productId = Integer.parseInt(request.getParameter("pid"));
ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());
Product product = productDao.getProductsByProductId(productId);

List<Product> productList = productDao.getAllLatestProducts();

User u = (User) session.getAttribute("activeUser");
WishlistDao wishlistDao = new WishlistDao(ConnectionProvider.getConnection());
CategoryDao categoryDao = new CategoryDao(ConnectionProvider.getConnection());

List<Product> similarProducts = productDao.getAllProductsByCategoryId(product.getCategoryId());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>View Product</title>
<%@include file="Components/common_css_js.jsp"%>

<style type="text/css">
.real-price {
	font-size: 26px !important;
	font-weight: 600;
}

.product-price {
	font-size: 18px !important;
	text-decoration: line-through;
}

.product-discount {
	font-size: 16px !important;
	color: #027a3e;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		CKEDITOR.replace('description-product')
	})
</script>

</head>
<body>

	<!--navbar -->
	<%@include file="Components/navbar.jsp"%>

	<div class="container mt-5">
		<%@include file="Components/alert_message.jsp"%>
		<div class="row border border-3">
			<div class="col-md-6">
				<div class="container-fluid text-end my-3">
					<img src="Product_imgs/<%=product.getProductImages()%>"
						class="card-img-top"
						style="max-width: 100%; max-height: 500px; width: auto;">
				</div>
			</div>
			<div class="col-md-6">
				<div class="container-fluid my-5">
					<h4><%=product.getProductName()%></h4>
					<span class="fs-5"><b>Mô tả</b></span><br>
					<div id="#description-product"><%=product.getProductDescription()%></div>
					<br> <span class="real-price"><%=product.getProductPriceAfterDiscount()%>k</span>&ensp;
					<span class="product-price"><%=product.getProductPrice()%>k</span>&ensp;
					<span class="product-discount">Ưu đãi <%=product.getProductDiscount()%>&#37;
					</span><br> <span class="fs-5"><b>Trạng thái: </b></span> <span
						id="availability"> <%
 if (product.getProductQunatity() > 0) {
 	out.println("Có sẵn");
 } else {
 	out.println("Hiện đang hết hàng");
 }
 %>
					</span><br> <span class="fs-5"><b>Danh mục: </b></span> <span><%=categoryDao.getCategoryName(product.getCategoryId())%></span>
					<form method="post">
						<div class="container-fluid text-center mt-3">
							<%
							if (u == null) {
							%>
							<button type="button" onclick="window.open('login.jsp', '_self')"
								class="btn btn-primary text-white btn-lg">Thêm vào giỏ
								hàng</button>
							&emsp;

							<%
							} else {
							%>
							<button type="submit"
								formaction="./AddToCartServlet?uid=<%=u.getUserId()%>&pid=<%=product.getProductId()%>"
								class="btn btn-primary text-white btn-lg">Thêm vào giỏ
								hàng</button>
							&emsp;
							<%
							}
							%>
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- Phần hiển thị sản phẩm cùng danh mục -->
		<div style="margin-top: 20px;" class="row border border-3">
			<h3>Sản phẩm cùng danh mục</h3>
			<%
			for (Product p : similarProducts) {
				if (p.getProductId() != productId) { // Loại trừ sản phẩm hiện tại
			%>
			<div class="col-md-3">
				<div class="card h-100 px-2 py-2">
					<a href="viewProduct.jsp?pid=<%=p.getProductId()%>">
						<div class="container text-center">
							<img src="Product_imgs/<%=p.getProductImages()%>"
								class="card-img-top m-2"
								style="max-width: 100%; max-height: 200px; width: auto;">
							<h5 class="card-title text-center"><%=p.getProductName()%></h5>
							<div class="container text-center">
								<span class="real-price"><%=p.getProductPrice()%>k</span>&ensp;
								<span class="product-price"><%=p.getProductPrice()%>k</span>&ensp;
								<span class="product-discount">Ưu đãi <%=p.getProductDiscount()%>&#37;
								</span><br>
							</div>
						</div>
					</a>
				</div>
			</div>
			<%
			}
			}
			%>
		</div>
	</div>

</body>

</html>
