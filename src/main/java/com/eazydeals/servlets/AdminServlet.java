package com.eazydeals.servlets;

import java.io.IOException;

import com.eazydeals.dao.AdminDao;
import com.eazydeals.entities.Admin;
import com.eazydeals.entities.Message;
import com.eazydeals.helper.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String operation = request.getParameter("operation");
		AdminDao adminDao = new AdminDao(ConnectionProvider.getConnection());
		HttpSession session = request.getSession();
		Message message = null;

		if (operation.trim().equals("save")) {

			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");

			Admin admin = new Admin(name, email, phone, password);
			boolean flag = adminDao.saveAdmin(admin);

			if (flag) {
				message = new Message("Đăng ký Admin mới thành công!", "success", "alert-success");
			} else {
				message = new Message("Rất tiếc! Có gì đó đã sai!", "error", "alert-danger");
			}

		} else if (operation.trim().equals("delete")) {

			int id = Integer.parseInt(request.getParameter("id"));
			boolean flag = adminDao.deleteAdmin(id);
			if (flag) {
				message = new Message("Đã xóa Admin thành công!", "success", "alert-success");
			} else {
				message = new Message("Rất tiếc! Có gì đó đã sai!", "error", "alert-danger");
			}
		}
		session.setAttribute("message", message);
		response.sendRedirect("display_admin.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
