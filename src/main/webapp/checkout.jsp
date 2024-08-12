<%@page import="com.eazydeals.entities.Message"%>
<%@page import="com.eazydeals.dao.ProductDao"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.eazydeals.dao.CartDao"%>
<%@page errorPage="error_exception.jsp"%>
<%
User activeUser = (User) session.getAttribute("activeUser");
if (activeUser == null) {
	Message message = new Message("Bạn chưa đăng nhập! xin hãy đăng nhập trước!", "error", "alert-danger");
	session.setAttribute("message", message);
	response.sendRedirect("login.jsp");
	return;
}
String from = (String) session.getAttribute("from");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Mua hàng</title>
<%@include file="Components/common_css_js.jsp"%>
</head>
<body>
	<!--navbar -->
	<%@include file="Components/navbar.jsp"%>

	<div class="container mt-5" style="font-size: 17px;">
		<div class="row">

			<!-- left column -->
			<div class="col-md-8">
				<div class="card">
					<div class="container px-3 py-3">
						<div class="card">
							<div class="container-fluid text-white"
								style="background-color: #389aeb;">
								<h4>Địa chỉ giao hàng</h4>
							</div>
						</div>
						<div class="mt-3 mb-3">
							<h5>
								<b><%=user.getUserName()%></b> &nbsp;</br>
								<%=user.getUserPhone()%></h5>
							<%
							StringBuilder str = new StringBuilder();
							str.append(user.getUserAddress() );
							
							out.println(str);
							%>
							<br>
							<div class="text-end">
								<button type="button" class="btn btn-outline-primary"
									data-bs-toggle="modal" data-bs-target="#exampleModal">
									Chọn địa chỉ</button>
							</div>
						</div>
						<hr>
						<div class="card">
							<div class="container-fluid text-white"
								style="background-color: #389aeb;">
								<h4>Phương thức thanh toán</h4>
							</div>
						</div>
						<form action="OrderOperationServlet" method="post">
							<div class="form-check mt-2">
								<input class="form-check-input" type="radio" name="payementMode"
									value="Đã thanh toán" required><label
									class="form-check-label">Thẻ Credit /Debit /ATM </label><br>
								<div class="mb-3">

									<input class="form-control mt-3" type="number"
										placeholder="Nhập số thẻ..." name="cardno">
									<div class="row gx-5">
										<div class="col mt-3">
											<input class="form-control" type="number"
												placeholder="Nhập CVV..." name="cvv">
										</div>
										<div class="col mt-3">
											<input class="form-control" type="text"
												placeholder="Ngày hết hạn(mm/dd)...">
										</div>
									</div>
									<input class="form-control mt-3" type="text"
										placeholder="Nhập tên chủ thẻ..." name="name">
								</div>
								<input class="form-check-input" type="radio" name="payementMode"
									value="Thanh toán khi nhận hàng"><label
									class="form-check-label">Thanh toán khi nhận hàng</label>

							</div>
							<div>
								<input class="form-check-input" type="radio" name="payementMode"
									value="VNPAY"><label class="form-check-label">
									VNPAY</label>
							</div>
							<div class="text-end">
								<button type="submit"
									class="btn btn-lg btn-outline-primary mt-3">Đặt hàng 2</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- end of column -->

			<!-- right column -->
			<div class="col-md-4">
				<div class="card">
					<div class="container px-3 py-3">
						<h4>Chi tiết đơn hàng</h4>
						<hr>
						<%
						if (from.trim().equals("cart")) {
							CartDao cartDao = new CartDao(ConnectionProvider.getConnection());
							int totalProduct = cartDao.getCartCountByUserId(user.getUserId());
							float totalPrice = (float) session.getAttribute("totalPrice");
						%>
						<table class="table table-borderless">
							<tr>
								<td>Tổng đơn hàng</td>
								<td><%=totalProduct%></td>
							</tr>
							<tr>
								<td>Tổng giá</td>
								<td><%=totalPrice%></td>
							</tr>
							<tr>
								<td><h5>Số tiền phải trả:</h5></td>
								<td><h5>
										<%=totalPrice%>
									</h5></td>
							</tr>
						</table>
						<%
						} else {
						ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());
						int pid = (int) session.getAttribute("pid");
						float price = productDao.getProductPriceById(pid);
						%>
						<table class="table table-borderless">
							<tr>
								<td>Tổng đơn hàng</td>
								<td>1</td>
							</tr>
							<tr>
								<td>Tổng giá</td>
								<td><%=price%>k</td>
							</tr>
							<tr>
								<td>Phi vận chuyển</td>
								<td>40k</td>
							</tr>
							<tr>
								<td>Phí đóng gói</td>
								<td>7k</td>
							</tr>
							<tr>
								<td><h5>Số tiền phải trả :</h5></td>
								<td><h5>

										<%=price + 47%>k
									</h5></td>
							</tr>
						</table>
						<%
						}
						%>
					</div>
				</div>
			</div>
			<!-- end of column -->
		</div>
	</div>


	<!--Change Address Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Chọn địa
						chỉ</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<form action="UpdateUserServlet" method="post">
					<input type="hidden" name="operation" value="changeAddress">
					<div class="modal-body mx-3">


						<div class="mt-2">
							<label class="form-label fw-bold">Địa chỉ</label> <input
								class="form-control" type="text" name="user_address"
								placeholder="Nhập địa chỉ..." required>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- end modal -->

</body>
</html>