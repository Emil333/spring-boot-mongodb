package com.emil.springproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/api/v1/auth").setViewName("authentication");
        registry.addViewController("/api/v1/employees").setViewName("employees");
        registry.addViewController("/v1/api/user").setViewName("user");
    }
}
