package com.tenneshop.bookmark.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
	
	final public static String LOGIN_REQUEST_PATH = "/login";
	final public static String REGISTER_FORM_REQUEST_PATH = "/register_form";
	final public static String REGISTER_NEW_REQUEST_PATH = "/register_new";
	final public static String HOME_REQUEST_PATH = "/";
	final public static String LOGOUT_REQUEST_PATH = "/logout";
	final public static String CHANGE_PASSWORD_REQUEST_PAHT = "/change_password";
	final public static String CHANGE_PASSWORD_FORM_REQUEST_PAHT = "/change_password_form";

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
    
    public static boolean isFormFilledIn(final HttpServletRequest request) {
		Map<String, String[]> postedFormData = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : postedFormData.entrySet()) {
			String[] values = entry.getValue();
			
			if (values.length < 1) {
				return false;
			}
			
			String value = values[0];
			if (value == null || value.length() < 2) {
				return false;
			}
		}
		
		return true;
    }
    
}
