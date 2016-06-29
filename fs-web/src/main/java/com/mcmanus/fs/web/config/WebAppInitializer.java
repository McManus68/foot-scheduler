package com.mcmanus.fs.web.config;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    private static final String DISPATCH_SERVLET_NAME = "dispatcher";
    private static final String DISPATCH_SERVLET_MAPPING = "/rest/*";

    // Force charset UTF-8
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }

    // OSIV-filter aims to set JPA transaction for the entire processing of the request
    @Override
    protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
        final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        FilterRegistration.Dynamic osivRegistration = servletContext.addFilter("OSIV-filter", OpenEntityManagerInViewFilter.class);
        osivRegistration.addMappingForUrlPatterns(null, true, "/*");
        final ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCH_SERVLET_NAME, new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(DISPATCH_SERVLET_MAPPING);
    }
}
