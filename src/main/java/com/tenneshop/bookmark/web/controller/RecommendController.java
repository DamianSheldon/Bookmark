package com.tenneshop.bookmark.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.tenneshop.bookmark.web.util.MysqlJDBCUtil;

public class RecommendController implements IController {

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

		ctx.setVariable("loginUser", validUser);

		List<String> urls = MysqlJDBCUtil.getInstance().getRecommendUrlsOfUser(validUser);
		ctx.setVariable("urls", urls);
		
		templateEngine.process("recommend", ctx, response.getWriter());
	}

}
