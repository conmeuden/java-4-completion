package com.eazydeals.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eazydeals.dao.CartDao;
import com.eazydeals.dao.OrderDao;
import com.eazydeals.dao.OrderedProductDao;
import com.eazydeals.dao.ProductDao;
import com.eazydeals.entities.Cart;
import com.eazydeals.entities.Order;
import com.eazydeals.entities.OrderedProduct;
import com.eazydeals.entities.Product;
import com.eazydeals.entities.User;
import com.eazydeals.helper.ConnectionProvider;
import com.eazydeals.helper.MailMessenger;

import config.VNPAYConfig;

/**
 * Servlet implementation class VnpayPaymentReturn
 */
public class VnpayPaymentReturn extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        int paymentStatus = orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
		User user = (User) session.getAttribute("activeUser");
		System.out.println(session.getAttribute("activeUser"));
        if(paymentStatus ==1) {
        	Order order = new Order(orderInfo, "Order Placed", "VNPAY", user.getUserId());
			OrderDao orderDao = new OrderDao(ConnectionProvider.getConnection());
			int id = orderDao.insertOrder(order);

			CartDao cartDao = new CartDao(ConnectionProvider.getConnection());
			List<Cart> listOfCart = cartDao.getCartListByUserId(user.getUserId());
			OrderedProductDao orderedProductDao = new OrderedProductDao(ConnectionProvider.getConnection());
			ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());
			for (Cart item : listOfCart) {

				Product prod = productDao.getProductsByProductId(item.getProductId());
				String prodName = prod.getProductName();
				int prodQty = item.getQuantity();
				float price = prod.getProductPriceAfterDiscount();
				String image = prod.getProductImages();

				OrderedProduct orderedProduct = new OrderedProduct(prodName, prodQty, price, image, id);
				orderedProductDao.insertOrderedProduct(orderedProduct);
			}
			session.removeAttribute("from");
			session.removeAttribute("totalPrice");

			// removing all product from cart after successful order
			cartDao.removeAllProduct();
			session.setAttribute("order", "success");
			MailMessenger.successfullyOrderPlaced(user.getUserName(), user.getUserEmail(), orderInfo, new Date().toString());
			response.sendRedirect("index.jsp");
        }else {
        	
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public int orderReturn(HttpServletRequest request){
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = VNPAYConfig.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

}
