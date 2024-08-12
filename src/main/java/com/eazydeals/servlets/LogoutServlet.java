package com.eazydeals.servlets;

import java.io.IOException;

import com.eazydeals.entities.Message;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user = request.getParameter("user");
		HttpSession session = request.getSession();
		if (user.trim().equals("user")) {
			session.removeAttribute("activeUser");
			Message message = new Message("Đăng xuất thành công!!", "success", "alert-success");
			session.setAttribute("message", message);
			response.sendRedirect("login.jsp");
		} else if (user.trim().equals("admin")) {
			session.removeAttribute("activeAdmin");
			Message message = new Message("Đăng xuất thành công!!", "success", "alert-success");
			session.setAttribute("message", message);
			response.sendRedirect("adminlogin.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
