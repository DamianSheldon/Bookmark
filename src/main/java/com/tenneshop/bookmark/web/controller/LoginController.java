package com.tenneshop.bookmark.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.tenneshop.bookmark.web.util.MysqlJDBCUtil;

public class LoginController implements IController {

	public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ITemplateEngine templateEngine) throws Exception {
		
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		HttpSession session = request.getSession();
		
		// Check username and password
		String username = request.getParameter("user_name");
		String password = request.getParameter("user_password");
		
		if (username != null && password != null) {
			// TODO: More strict validate
			// Login
			if (MysqlJDBCUtil.getInstance().login(username, password)) {
				session.setAttribute("valid_user", username);
				
				ctx.setVariable("loginUser", username);
				
				// Fetch URLs from database
				List<String> urls = MysqlJDBCUtil.getInstance().getUserUrls(username);
				ctx.setVariable("urls", urls);
				
				templateEngine.process("home", ctx, response.getWriter());
			}
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not log you in.");
			}
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not log you in.");
		}
	}
}
