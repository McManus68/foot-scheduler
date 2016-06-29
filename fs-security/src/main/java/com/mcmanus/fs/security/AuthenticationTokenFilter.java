package com.mcmanus.fs.security;

import com.mcmanus.fs.security.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    private AuthenticationService authSrv;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String authToken = httpRequest.getHeader("X-Auth-Token");
        if (authToken != null) {
            authSrv.validateToken(authToken);
        }
        try {
            chain.doFilter(request, response);
        } catch (ServletException e) {
            throw new IOException("ServletException", e);
        }
    }
}
