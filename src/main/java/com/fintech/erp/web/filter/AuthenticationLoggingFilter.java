package com.fintech.erp.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationLoggingFilter.class);
    private static final String ATTR_LOGGED = "AUTH_LOGGED";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && request.getAttribute(ATTR_LOGGED) == null) {
                String principal = String.valueOf(authentication.getPrincipal());
                String authorities = String.valueOf(authentication.getAuthorities());
                String path = request.getRequestURI();
                log.info("Successful authentication: principal='{}', authorities={}, path={}", principal, authorities, path);
                request.setAttribute(ATTR_LOGGED, Boolean.TRUE);
            }
        } catch (Exception e) {
            log.debug("Error while logging authentication", e);
        }

        filterChain.doFilter(request, response);
    }
}
