package com.emil.springproject.filter;

import com.emil.springproject.beans.Authentication;
import com.emil.springproject.beans.User;
import com.emil.springproject.service.AuthService;
import com.emil.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class AuthTokenFilter extends GenericFilterBean {


    private AuthService authenticationService;

    private UserService userService;
    private static final String authTokenHeaderName = "x-auth-token";


    public AuthTokenFilter(AuthService authRepo, UserService userService) {
        this.authenticationService = authRepo;
        this.userService = userService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest.getHeader(authTokenHeaderName);
            if (StringUtils.hasText(authToken)) {
                Authentication authentication = authenticationService.findById(authToken);
                if (authentication != null) {
                    Optional<User> userOptional = userService.findUserById(authentication.getUserId());
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,
                                user.getPassword());
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                } else {
                    throw new AuthenticationException();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
