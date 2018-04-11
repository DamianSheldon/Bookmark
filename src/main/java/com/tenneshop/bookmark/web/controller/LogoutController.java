package com.tenneshop.bookmark.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

public class LogoutController implements IController {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ITemplateEngine templateEngine) throws Exception {
		
		HttpSession session = request.getSession();
		
		String oldUser = (String) session.getAttribute("valid_user");
		
		session.removeAttribute("valid_user");
		session.invalidate();
		
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("oldUser", oldUser);
		
		templateEngine.process("logout", ctx, response.getWriter());
	}

}
