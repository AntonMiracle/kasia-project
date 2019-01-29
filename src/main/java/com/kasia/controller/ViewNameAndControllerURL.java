package com.kasia.controller;

final class ViewNameAndControllerURL {
    static final String V_PROFILE = "profile";
    static final String V_HOME = "home";
    static final String V_LOGIN = "login";
    static final String V_REGISTRATION = "registration";
    static final String V_BUDGET_ADD = "budgetAdd";

    static final String U_ROOT = "/";
    static final String U_LOGIN = "/login";
    static final String U_HOME = "/home";
    static final String U_PROFILE = "/profile";
    static final String U_BUDGET = "/budget";
    static final String U_REGISTRATION = "/registration";

    static String redirect(String url) {
        if (url.startsWith("/")) return "redirect:" + url;
        throw new RuntimeException("view name not exist");
    }

}
