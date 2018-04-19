package com.tenneshop.bookmark.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {

	public static final String LOGIN_REQUEST_PATH = "/login";
	public static final String REGISTER_FORM_REQUEST_PATH = "/register_form";
	public static final String REGISTER_NEW_REQUEST_PATH = "/register_new";
	public static final String HOME_REQUEST_PATH = "/";
	public static final String LOGOUT_REQUEST_PATH = "/logout";
	public static final String CHANGE_PASSWORD_REQUEST_PATH = "/change_password";
	public static final String CHANGE_PASSWORD_FORM_REQUEST_PATH = "/change_password_form";
	public static final String FORGOT_PASSWORD_FORM_REQUEST_PATH = "/forgot_password_form";
	public static final String FORGOT_PASSWORD_REQUEST_PATH = "/forgot_password";
	public static final String ADD_BM_FORM_REQUEST_PATH = "/add_bm_form";
	public static final String ADD_BM_REQUEST_PATH = "/add_bm";
	public static final String DELETE_BMS_REQUEST_PATH = "/delete_bms";
	public static final String RECOMMEND_BMS_REQUEST_PATH = "/recommend_bms";

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

	public static void logFormContents(final HttpServletRequest request) {
		Map<String, String[]> postedFormData = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : postedFormData.entrySet()) {
			String[] values = entry.getValue();

			for (String value : values) {
				if (value != null && value.length() > 0) {
					System.out.println("key: " + entry.getKey() + " value: " + value);
				}
			}
		}
	}
}
