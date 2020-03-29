package com.emil.springproject.filter;

import com.emil.springproject.service.AuthService;
import com.emil.springproject.service.CustomUserDetailsService;
import com.emil.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.emil"})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        String [] methodSecured = {"/v1/api/user", "/api/v1/employees"};
//        http.csrf().disable()
//                .authorizeRequests().antMatchers("/").permitAll()
//                .antMatchers(methodSecured).authenticated();
////                .and().formLogin().loginPage("/api/v1/auth/signin");
//        SecurityConfigurer securityConfigurerAdapter = new AuthTokenConfig();
//        http.apply(securityConfigurerAdapter);
//    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity.ignoring().antMatchers(HttpMethod.GET, "/api");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.addFilterBefore(tokenAuthorizationFilter(), BasicAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/api/**").authenticated();
    }
    private AuthTokenFilter tokenAuthorizationFilter()
    {
        return new AuthTokenFilter(authenticationService, userService);
    }



    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
