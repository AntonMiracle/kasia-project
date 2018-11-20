package com.kasia.page;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public enum Page {
    LOGIN("/login.jsf")
    ,REGISTRATION("/registration.jsf")
    ,HOME("/pages/home.jsf")
    ;
    private final String name;

    Page(String name) {
        this.name = name;
    }

    public String relativePath() {
        return name;
    }

    public String absolutePath(ServletRequest servletRequest) {
        String contextPath = ((HttpServletRequest) servletRequest).getContextPath();
        return contextPath + relativePath();
    }
}
