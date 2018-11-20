package com.kasia.filter;

import com.kasia.exception.NoSessionRuntimeException;
import com.kasia.page.Page;
import com.kasia.security.SecurityService;
import com.kasia.session.SessionService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/pages/*")
public class SecurityFilter implements Filter {
    @Inject
    private SessionService sessionService;
    @Inject
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpSession session = sessionService.get(servletRequest);
            if (securityService.isActiveUserExist(session)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                sessionService.redirectTo(Page.LOGIN.absolutePath(servletRequest), servletResponse);
            }
        } catch (NoSessionRuntimeException ex) {
            sessionService.redirectTo(Page.LOGIN.absolutePath(servletRequest), servletResponse);
        }
    }
}
