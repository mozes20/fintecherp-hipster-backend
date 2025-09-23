package com.fintech.erp.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String query = request.getQueryString();
            String remote = request.getRemoteAddr();
            String auth = request.getHeader("Authorization");
            boolean hasAuth = auth != null && !auth.isBlank();
            String authInfo = hasAuth ? (auth.startsWith("Bearer ") ? "Bearer ****" : "present") : "none";
            log.info("Incoming request: method={} uri={}{} remote={} Authorization={}", method, uri, (query == null ? "" : "?" + query), remote, authInfo);
        } catch (Exception e) {
            log.debug("Error while logging request", e);
        }

        filterChain.doFilter(request, response);
    }
}
