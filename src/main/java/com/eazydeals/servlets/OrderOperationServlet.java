package com.eazydeals.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import com.eazydeals.helper.OrderIdGenerator;

import config.VNPAYConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OrderOperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String from = (String) session.getAttribute("from");
		String paymentType = request.getParameter("payementMode");
		User user = (User) session.getAttribute("activeUser");
		String orderId = OrderIdGenerator.getOrderId();
		String status = "Order Placed";
	
		if (from.trim().equals("cart")) {
			try {
				if (paymentType.equals("VNPAY")) {
				
					CartDao cartDao = new CartDao(ConnectionProvider.getConnection());
					int priceResult = 0;
					List<Cart> listOfCart = cartDao.getCartListByUserId(user.getUserId());
					ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());

					for (Cart item : listOfCart) {

						Product prod = productDao.getProductsByProductId(item.getProductId());
						String prodName = prod.getProductName();
						int prodQty = item.getQuantity();
						float price = prod.getProductPriceAfterDiscount();
						price*=prodQty;
						priceResult+=price;
					
					}
					 String vnp_Version = "2.1.0";
			            String vnp_Command = "pay";
			            String vnp_OrderInfo = "Thanh toán hoá đơn";
			            String orderType = "5";
			            String vnp_TxnRef = VNPAYConfig.getRandomNumber(8);
			            String vnp_IpAddr = VNPAYConfig.getIpAddress(request);
			            String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;
			    
			            int amount =  priceResult;
			            Map vnp_Params = new HashMap<>();
			            vnp_Params.put("vnp_Version", vnp_Version); //Phiên bản cũ là 2.0.0, 2.0.1 thay đổi sang 2.1.0
			            vnp_Params.put("vnp_Command", vnp_Command);
			            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
			            vnp_Params.put("vnp_Amount", String.valueOf(amount*100));
			            vnp_Params.put("vnp_CurrCode", "VND");
			            
//		                vnp_Params.put("vnp_BankCode", "VNPAYQR");
			            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
			            vnp_Params.put("vnp_OrderInfo", VNPAYConfig.getRandomNumber(8));
			            vnp_Params.put("vnp_OrderType", orderType);
			    
			            String locate = "vn";
			            if (locate != null && !locate.isEmpty()) {
			                vnp_Params.put("vnp_Locale", locate);
			            } else {
			                vnp_Params.put("vnp_Locale", "vn");
			            }
			            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			            baseUrl+=VNPAYConfig.vnp_Returnurl;
			            vnp_Params.put("vnp_ReturnUrl",baseUrl );
			            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
			            
			            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
			    
			            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			            String vnp_CreateDate = formatter.format(cld.getTime());
			    
			            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

			            //Build data to hash and querystring
			            List fieldNames = new ArrayList(vnp_Params.keySet());
			            Collections.sort(fieldNames);
			            StringBuilder hashData = new StringBuilder();
			            StringBuilder query = new StringBuilder();
			            Iterator itr = fieldNames.iterator();
			           
			            
			            while (itr.hasNext()) {
			                String fieldName = (String) itr.next();
			                String fieldValue = (String) vnp_Params.get(fieldName);
			                if ((fieldValue != null) && (fieldValue.length() > 0)) {
			                    //Build hash data
			                    hashData.append(fieldName);
			                    hashData.append('=');
			                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
			                    //Build query
			                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
			                    query.append('=');
			                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
			                    if (itr.hasNext()) {
			                        query.append('&');
			                        hashData.append('&');
			                    }
			                }
			            }
			            String queryUrl = query.toString();
			            
			                //Tạo vnp_SecureHash và tạo URL chuyển hướng phiên bản cũ 2.0.0, 2.0.1
			               
			            
			            String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.vnp_HashSecret, hashData.toString());
			            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
			            
			            
			                //Trong đó với Config.hmacSHA512:
			                
			            

			            String paymentUrl = VNPAYConfig.vnp_PayUrl + "?" + queryUrl;
			            
	

			            response.sendRedirect(paymentUrl);

				} else {
					Order order = new Order(orderId, status, paymentType, user.getUserId());
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
					MailMessenger.successfullyOrderPlaced(user.getUserName(), user.getUserEmail(), orderId, new Date().toString());
					response.sendRedirect("index.jsp");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (from.trim().equals("buy")) {
			try {

				int pid = (int) session.getAttribute("pid");
				Order order = new Order(orderId, status, paymentType, user.getUserId());
				OrderDao orderDao = new OrderDao(ConnectionProvider.getConnection());
				int id = orderDao.insertOrder(order);
				OrderedProductDao orderedProductDao = new OrderedProductDao(ConnectionProvider.getConnection());
				ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());

				Product prod = productDao.getProductsByProductId(pid);
				String prodName = prod.getProductName();
				int prodQty = 1;
				float price = prod.getProductPriceAfterDiscount();
				String image = prod.getProductImages();

				OrderedProduct orderedProduct = new OrderedProduct(prodName, prodQty, price, image, id);
				orderedProductDao.insertOrderedProduct(orderedProduct);

				// updating(decreasing) quantity of product in database
				productDao.updateQuantity(pid, productDao.getProductQuantityById(pid) - 1);

				session.removeAttribute("from");
				session.removeAttribute("pid");
				session.setAttribute("order", "success");
				MailMessenger.successfullyOrderPlaced(user.getUserName(), user.getUserEmail(), orderId, new Date().toString());
				response.sendRedirect("index.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
		
	}

}
