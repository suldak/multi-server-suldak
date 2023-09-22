package com.sulsul.suldaksuldak.config;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class CustomWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        addFilters(servletContext);
    }

    private void addFilters(ServletContext servletContext) {
        addEncodingFilter(servletContext);
        addCORSFilter(servletContext);
    }

    private void addEncodingFilter(ServletContext servletContext) {
        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("encodingFilter",
                new org.springframework.web.filter.CharacterEncodingFilter());
        characterEncoding.setInitParameter("encoding", "UTF-8");
        characterEncoding.setInitParameter("forceEncoding", "true");
        characterEncoding.addMappingForUrlPatterns(null, false, "*.do");
    }

    private void addCORSFilter(ServletContext servletContext) {
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("WebSecurityConfig",
                new WebSecurityConfig());
        corsFilter.addMappingForUrlPatterns(null, false, "/api/*");
    }
}
