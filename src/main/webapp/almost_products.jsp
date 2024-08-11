<%@page import="com.eazydeals.entities.Message"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page errorPage="error_exception.jsp"%>
<%@page import="com.eazydeals.dao.UserDao"%>
<%@page import="com.eazydeals.entities.Product"%>
<%@page import="com.eazydeals.dao.ProductDao"%>
<%
Admin activeAdmin = (Admin) session.getAttribute("activeAdmin");
if (activeAdmin == null) {
    Message message = new Message("You are not logged in! Login first!!", "error", "alert-danger");
    session.setAttribute("message", message);
    response.sendRedirect("adminlogin.jsp");
    return;
}
UserDao userDao = new UserDao(ConnectionProvider.getConnection());
ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Almost  Product's</title>
<%@include file="Components/common_css_js.jsp"%>
</head>
<body>
    <!--navbar -->
    <%@include file="Components/navbar.jsp"%>

    <!-- update product -->
    <div class="container mt-3">
        <%@include file="Components/alert_message.jsp"%>
        <table class="table table-hover">
            <tr class="table-primary text-center" style="font-size: 20px;">
                <th>Hình ảnh</th>
                <th>Tên sản phẩm</th>
                <th class="text-start">Danh mục</th>
                <th>Đơn giá</th>
                <th class="text-start">Tồn kho</th>
                <th class="text-start">Chiết khấu</th>
                <th>Thao tác</th>
            </tr>
            <%
            List<Product> productList = productDao.getAllProducts();
            for (Product prod : productList) {
                if (prod.getProductQunatity() < 10) {
                    String category = catDao.getCategoryName(prod.getCategoryId());
            %>
            <tr class="text-center">
                <td><img src="Product_imgs/<%=prod.getProductImages()%>"
                    style="width: 60px; height: 60px; width: auto;"></td>
                <td class="text-start"><%=prod.getProductName()%></td>
                <td><%=category%></td>
                <td><%=prod.getProductPriceAfterDiscount()%>k</td>
                <td><%=prod.getProductQunatity()%></td>
                <td><%=prod.getProductDiscount()%>%</td>
                <td><a href="update_product.jsp?pid=<%=prod.getProductId()%>" role="button" class="btn btn-secondary">Sửa</a>&emsp;<a
                    href="AddOperationServlet?pid=<%=prod.getProductId()%>&operation=deleteProduct"
                    class="btn btn-danger" role="button">Xóa</a></td>
            </tr>
            <%
                }
            }
            %>
        </table>
    </div>
</body>
</html>
