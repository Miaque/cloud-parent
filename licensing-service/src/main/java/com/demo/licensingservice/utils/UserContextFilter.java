package com.demo.licensingservice.utils;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@Component
public class UserContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        UserContextHolder.getContext().setCorrelationId(request.getHeader(UserContext.CORRELATION_ID));

        UserContextHolder.getContext().setUserId(request.getHeader(UserContext.USER_ID));

        UserContextHolder.getContext().setAuthToken(request.getHeader(UserContext.AUTH_TOKEN));

        UserContextHolder.getContext().setOrgId(request.getHeader(UserContext.ORG_ID));

        filterChain.doFilter(request, response);
    }
}
