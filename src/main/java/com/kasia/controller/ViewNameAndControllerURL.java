package com.kasia.controller;

public final class ViewNameAndControllerURL {
    public static final String V_PROFILE = "profile";
    public static final String V_LOGIN = "login";
    public static final String V_REGISTRATION = "registration";
    public static final String V_BUDGET_ADD = "budgetAdd";
    public static final String V_BUDGET_ALL = "budgetAll";
    public static final String V_BUDGET = "budget";
    public static final String V_PROVIDER = "provider";

    public static final String U_ROOT = "/";
    public static final String U_LOGIN = "/login";
    public static final String U_PROFILE = "/profile";
    public static final String U_PROFILE_UPDATE = U_PROFILE + "/updateProfile";
    public static final String U_BUDGET = "/budget";
    public static final String U_BUDGET_OPEN = U_BUDGET + "/open";
    public static final String U_BUDGET_ADD = U_BUDGET + "/add";
    public static final String U_BUDGET_ALL = U_BUDGET + "/all";
    public static final String U_BUDGET_SAVE = U_BUDGET + "/save";
    public static final String U_REGISTRATION = "/registration";
    public static final String U_OPERATION = U_BUDGET + "/operation";
    public static final String U_OPERATION_ADD = U_OPERATION + "/add";
    public static final String U_ELEMENT = U_BUDGET + "/element";
    public static final String U_PROVIDER = U_BUDGET + "/provider";
    public static final String U_PROVIDER_ADD = U_PROVIDER + "/add";

    public static String redirect(String url) {
        if (url.startsWith("/")) return "redirect:" + url;
        throw new RuntimeException("url name not exist");
    }

}