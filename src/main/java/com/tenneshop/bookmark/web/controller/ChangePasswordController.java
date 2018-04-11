package com.tenneshop.bookmark.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.tenneshop.bookmark.web.util.HttpServletRequestUtil;

public class ChangePasswordController implements IController {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ITemplateEngine templateEngine) throws Exception {
		
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		String requestPath = HttpServletRequestUtil.getRequestPath(request);
		if (requestPath.equals(HttpServletRequestUtil.CHANGE_PASSWORD_FORM_REQUEST_PAHT)) {
			templateEngine.process("change_password_form", ctx, response.getWriter());
		}
		else if (requestPath.equals(HttpServletRequestUtil.CHANGE_PASSWORD_REQUEST_PAHT)) {
			templateEngine.process("change_password_result", ctx, response.getWriter());
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request path unavailable.");
		}
		
	}

}
