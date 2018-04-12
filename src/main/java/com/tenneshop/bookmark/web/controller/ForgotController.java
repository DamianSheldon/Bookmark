package com.tenneshop.bookmark.web.controller;

import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.ITemplateEngine;

public class ForgotController implements IController {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ITemplateEngine templateEngine) throws Exception {
		
		String username = request.getParameter("user_name");
		
		// Reset password
		String newPassword = getRandomWord(6, 13);
		
		// Notify password

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
