package com.tenneshop.bookmark.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.EmailValidator;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.tenneshop.bookmark.web.util.HttpServletRequestUtil;
import com.tenneshop.bookmark.web.util.MysqlJDBCUtil;

public class RegisterController implements IController {
	
	private String invalidMessage;
	
	// Get an EmailValidator
	final private EmailValidator emailValidator = EmailValidator.getInstance();
	
	public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ITemplateEngine templateEngine) throws Exception {
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        
		final String path = HttpServletRequestUtil.getRequestPath(request);
		
		if (path.equals(HttpServletRequestUtil.REGISTER_FORM_REQUEST_PATH)) {
			templateEngine.process("register_form", ctx, response.getWriter());
		}
		else if (path.equals(HttpServletRequestUtil.REGISTER_NEW_REQUEST_PATH)) {
			// start session which may be needed later
			// start it now because it must go before headers
			HttpSession session = request.getSession();
			
			// Valid form contents
			if (!isValidForm(request)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, invalidMessage);
				return;
			}
			
			// Attempt to register
			String username = request.getParameter("user_name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			
			if (registerUser(username, password, email)) {
				
				session.setAttribute("valid_user", username);
				
				templateEngine.process("register_new", ctx, response.getWriter());
			}
			else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, invalidMessage);
			}
		}
		else {
			throw new UnavailableException("Request path unavailable!");
		}
	}
	
	private boolean isValidForm(final HttpServletRequest request) {
		// check form filled in
		if (!HttpServletRequestUtil.isFormFilledIn(request)) {
			invalidMessage = "You have not filled the form out correctly. please go back and try again.";
			return false;
		}
		
		// email address not valid
		String email = request.getParameter("email");
		if (!emailValidator.isValid(email)) {
			invalidMessage = "That is not a valid email address. Please go back and try again.";
			return false;
		}
		
		// passwords not the same
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		if (!password.equals(confirmPassword)) {
			invalidMessage = "The passwords you entered do not match. please go back and try again.";
			return false;
		}
		
		// check password length is ok
		if (password.length() < 6) {
			invalidMessage = "Your password must be at least 6 characters long. Please go back and try again.";
			return false;
		}
		
		// check username length is ok
		String username = request.getParameter("user_name");
		if (username.length() > 16) {
			invalidMessage = "Your username must be less than 17 characters long. Please go back and try again.";
			return false;
		}
		
		return true;
	}
	
	private boolean registerUser(String username, String password, String email) {
		
		if (MysqlJDBCUtil.getInstance().registerUser(username, password, email)) {
			return true;	
		}
		else {
			invalidMessage = "Could not register you in database. please try again later.";
			return false;
		}
	}
	
}
