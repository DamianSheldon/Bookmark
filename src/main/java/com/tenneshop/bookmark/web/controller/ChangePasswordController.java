package com.tenneshop.bookmark.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.tenneshop.bookmark.web.util.HttpServletRequestUtil;
import com.tenneshop.bookmark.web.util.MysqlJDBCUtil;

public class ChangePasswordController implements IController {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ITemplateEngine templateEngine) throws Exception {
		
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		HttpSession session = request.getSession();
		String validUser = (String) session.getAttribute("valid_user");
		if (validUser == null) {
			templateEngine.process("login", ctx, response.getWriter());
			return;
		}

		String requestPath = HttpServletRequestUtil.getRequestPath(request);
		if (requestPath.equals(HttpServletRequestUtil.CHANGE_PASSWORD_FORM_REQUEST_PATH)) {
			
			ctx.setVariable("loginUser", validUser);
			
			templateEngine.process("change_password_form", ctx, response.getWriter());	
		}
		else if (requestPath.equals(HttpServletRequestUtil.CHANGE_PASSWORD_REQUEST_PATH)) {
			// Check form filled in
			if (!HttpServletRequestUtil.isFormFilledIn(request)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You have not filled out the form completely.Please try again.");
				return;
			}
			
			// passwords not the same
			String oldPassword = request.getParameter("old_password");
			String newPassword = request.getParameter("new_password");
			String newRepeatPassword = request.getParameter("repeat_new_password");
			
			if (!newPassword.equals(newRepeatPassword)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Passwords entered were not the same. Not changed.");
				return;
			}
			
			// check password length is ok
			if (newPassword.length() < 6) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "New password must be at least 6 characters. Try again.");
				return;
			}
			
			// Change password
			if (MysqlJDBCUtil.getInstance().changePassword(validUser, newPassword, oldPassword)) {
				templateEngine.process("change_password_result", ctx, response.getWriter());	
			}
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password could not be changed.");
			}
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request path unavailable.");
		}
		
	}

}
