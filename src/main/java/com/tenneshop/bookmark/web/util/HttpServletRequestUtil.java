package com.tenneshop.bookmark.web.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
	
	final public static String LOGIN_REQUEST_PATH = "/login";
	final public static String REGISTER_FORM_REQUEST_PATH = "/register_form";
	final public static String REGISTER_NEW_REQUEST_PATH = "/register_new";
	final public static String HOME_REQUEST_PATH = "/";
	final public static String LOGOUT_REQUEST_PATH = "/logout";


    public static String getRequestPath(final HttpServletRequest request) {
        
        String requestURI = request.getRequestURI();
        final String contextPath = request.getContextPath();
        
        final int fragmentIndex = requestURI.indexOf(';'); 
        if (fragmentIndex != -1) {
            requestURI = requestURI.substring(0, fragmentIndex);
        }
        
        if (requestURI.startsWith(contextPath)) {
            return requestURI.substring(contextPath.length());
        }
        return requestURI;
    }
    
}
