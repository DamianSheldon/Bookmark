package com.tenneshop.bookmark.web.application;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.tenneshop.bookmark.web.controller.AddBMController;
import com.tenneshop.bookmark.web.controller.ChangePasswordController;
import com.tenneshop.bookmark.web.controller.ForgotController;
import com.tenneshop.bookmark.web.controller.HomeController;
import com.tenneshop.bookmark.web.controller.IController;
import com.tenneshop.bookmark.web.controller.LoginController;
import com.tenneshop.bookmark.web.controller.LogoutController;
import com.tenneshop.bookmark.web.controller.RegisterController;
import com.tenneshop.bookmark.web.util.HttpServletRequestUtil;


public class Application {
    private TemplateEngine templateEngine;
    private Map<String, IController> controllersByURL;

    
    
    public Application(final ServletContext servletContext) {

        super();

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        
        // HTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        
        // FIXME: Disable cache during development
        templateResolver.setCacheable(false);
        // Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
//        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        
        // Cache is set to true by default. Set to false if you want templates to
        // be automatically updated when modified.
//        templateResolver.setCacheable(true);

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);

        this.controllersByURL = new HashMap<String, IController>();
        this.controllersByURL.put(HttpServletRequestUtil.LOGIN_REQUEST_PATH, new LoginController());
        this.controllersByURL.put(HttpServletRequestUtil.REGISTER_FORM_REQUEST_PATH, new RegisterController());
        this.controllersByURL.put(HttpServletRequestUtil.REGISTER_NEW_REQUEST_PATH, new RegisterController());
        this.controllersByURL.put(HttpServletRequestUtil.HOME_REQUEST_PATH, new HomeController());
        this.controllersByURL.put(HttpServletRequestUtil.LOGOUT_REQUEST_PATH, new LogoutController());
        this.controllersByURL.put(HttpServletRequestUtil.CHANGE_PASSWORD_REQUEST_PAHT, new ChangePasswordController());
        this.controllersByURL.put(HttpServletRequestUtil.CHANGE_PASSWORD_FORM_REQUEST_PAHT, new ChangePasswordController());
        this.controllersByURL.put(HttpServletRequestUtil.FORGOT_PASSWORD_FORM_REQUEST_PAHT, new ForgotController());
        this.controllersByURL.put(HttpServletRequestUtil.FORGOT_PASSWORD_REQUEST_PAHT, new ForgotController());
        this.controllersByURL.put(HttpServletRequestUtil.ADD_BM_FORM_REQUEST_PATH, new AddBMController());
        this.controllersByURL.put(HttpServletRequestUtil.ADD_BM_REQUEST_PATH, new AddBMController());
    }

    
    public IController resolveControllerForRequest(final HttpServletRequest request) {
//        final String path = getRequestPath(request);
        final String path = HttpServletRequestUtil.getRequestPath(request);

        return this.controllersByURL.get(path);
    }
    
    
    public ITemplateEngine getTemplateEngine() {
        return this.templateEngine;
    }

}
