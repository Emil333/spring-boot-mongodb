package com.emil.springproject.filter;

import com.emil.springproject.service.AuthService;
import com.emil.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {"com.emil"})
public class AuthTokenConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthService authenticationService;

    @Autowired
    private UserService userService;


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthTokenFilter authTokenFilter = new AuthTokenFilter(authenticationService, userService);
        builder.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
