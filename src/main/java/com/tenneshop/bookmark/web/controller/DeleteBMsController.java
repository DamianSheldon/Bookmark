package com.tenneshop.bookmark.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.tenneshop.bookmark.web.util.HttpServletRequestUtil;
import com.tenneshop.bookmark.web.util.MysqlJDBCUtil;

public class DeleteBMsController implements IController {

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
		
		String [] deletedUrls = request.getParameterValues("del_me");
		ctx.setVariable("deleted_urls", deletedUrls);
		
//		HttpServletRequestUtil.logFormContents(request);
		
		// Delete url from database
		if (MysqlJDBCUtil.getInstance().deleteBMs(validUser, deletedUrls)) {
			List<String> urls = MysqlJDBCUtil.getInstance().getUserUrls(validUser);
			ctx.setVariable("urls", urls);
			
			templateEngine.process("delete_bms", ctx, response.getWriter());
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not delete bookmarks.");
		}
	}

}
