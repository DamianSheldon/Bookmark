package com.tenneshop.bookmark.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.UrlValidator;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.tenneshop.bookmark.web.util.HttpServletRequestUtil;
import com.tenneshop.bookmark.web.util.MysqlJDBCUtil;

public class AddBMController implements IController {

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
		if (requestPath.equals(HttpServletRequestUtil.ADD_BM_FORM_REQUEST_PATH)) {
			ctx.setVariable("loginUser", validUser);
			templateEngine.process("add_bm_form", ctx, response.getWriter());
		}
		else if (requestPath.equals(HttpServletRequestUtil.ADD_BM_REQUEST_PATH)) {
			// Check form filled in
			if (HttpServletRequestUtil.isFormFilledIn(request)) {
				
				String bookmarkUrlString = request.getParameter("new_bm");
				if (bookmarkUrlString == null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Form not completely filled out.");
					return;
				}
				
				// Check weather url is valid
				if (!bookmarkUrlString.startsWith("http://") || !bookmarkUrlString.startsWith("https://")) {
					bookmarkUrlString = "https://" + bookmarkUrlString;
				}
				
				String[] schemes = {"http","https"};
				UrlValidator urlValidator = new UrlValidator(schemes);
				if (urlValidator.isValid(bookmarkUrlString)) {
					// Add bm
					if (MysqlJDBCUtil.getInstance().addBM(validUser, bookmarkUrlString)) {
						List<String> urls = MysqlJDBCUtil.getInstance().getUserUrls(validUser);
						ctx.setVariable("urls", urls);
						templateEngine.process("add_bm_result", ctx, response.getWriter());
					}
					else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bookmark could not be inserted.");
					}	
				}
				else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not a valid URL.");
				}
			}
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Form not completely filled out.");
			}
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request path unavailable.");
		}
	}

}
