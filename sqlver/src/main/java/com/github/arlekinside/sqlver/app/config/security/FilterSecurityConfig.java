package com.github.arlekinside.sqlver.app.config.security;

import com.github.arlekinside.sqlver.app.config.security.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterSecurityConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        var filterRegistration = new FilterRegistrationBean<LoggingFilter>();
        filterRegistration.setFilter(new LoggingFilter());
        filterRegistration.addUrlPatterns("*");
        return filterRegistration;
    }

}
