package com.znvks.salon.web.initializer;

import com.znvks.salon.model.config.DbConfig;
import com.znvks.salon.web.config.SecurityConfig;
import com.znvks.salon.web.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public static final String DISPATCHER_SERVLET_URL_PATTERN = "/";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DbConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{DISPATCHER_SERVLET_URL_PATTERN};
    }
}
