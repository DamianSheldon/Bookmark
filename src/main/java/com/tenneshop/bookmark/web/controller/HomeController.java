package com.tenneshop.bookmark.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

public class HomeController implements IController {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ITemplateEngine templateEngine) throws Exception {
		
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        
        templateEngine.process("home", ctx, response.getWriter());
		
	}

}
