package com.tenneshop.bookmark.web.controller;

import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.tenneshop.bookmark.web.util.EmailUtil;
import com.tenneshop.bookmark.web.util.HttpServletRequestUtil;
import com.tenneshop.bookmark.web.util.MysqlJDBCUtil;

public class ForgotController implements IController {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ITemplateEngine templateEngine) throws Exception {
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		String requestPath = HttpServletRequestUtil.getRequestPath(request);
		if (requestPath.equals(HttpServletRequestUtil.FORGOT_PASSWORD_REQUEST_PATH)) {
			String username = request.getParameter("user_name");
			
			// Reset password
			String newPassword = getRandomWord(6, 13);
			if (MysqlJDBCUtil.getInstance().resetPassword(username, newPassword)) {
				// Notify password	
				String email = MysqlJDBCUtil.getInstance().getEmailOfUser(username);
				if (email == null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not find email address.");
				}
				else {
					boolean success = EmailUtil.email("dongmeilianghy@sina.com", email, "bookmark login information", 
							"Your bookmark password has been changed to " + newPassword + " .Please change it next time you log in.");
					ctx.setVariable("success", success);
					
					templateEngine.process("forgot_result", ctx, response.getWriter());	
				}
			}
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not be reset password.");
			}
		}
		else if (requestPath.equals(HttpServletRequestUtil.FORGOT_PASSWORD_FORM_REQUEST_PATH)) {
			templateEngine.process("forgot_form", ctx, response.getWriter());
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request path unavailable.");
		}
	}

	private String getRandomWord(int minLength, int maxLength)
	{
	    Random random = new Random();
        char[] word = new char[random.nextInt(maxLength - minLength + 1) + minLength]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for(int j = 0; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextInt(26));
        }
        return new String(word);
	}
	
}
