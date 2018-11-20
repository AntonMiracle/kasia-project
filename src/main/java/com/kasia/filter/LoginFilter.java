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

@WebFilter(urlPatterns = "/login.jsf")
public class LoginFilter implements Filter {
    @Inject
    private SessionService sessionService;
    @Inject
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpSession session = sessionService.get(servletRequest);
            if (securityService.isActiveUserExist(session)) {
                sessionService.redirectTo(Page.HOME.absolutePath(servletRequest), servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (NoSessionRuntimeException ex) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
