package com.kasia.controller;

public final class ViewNameAndControllerURL {
    public static final String V_PROFILE = "profile";
    public static final String V_LOGIN = "login";
    public static final String V_REGISTRATION = "registration";
    public static final String V_BUDGET_ADD = "budgetAdd";
    public static final String V_BUDGET_ALL = "budgetAll";
    public static final String V_BUDGET = "budget";
    public static final String V_PROVIDER = "provider";
    public static final String V_PROVIDER_EDIT = "providerEdit";
    public static final String V_ELEMENT = "element";
    public static final String V_OPERATION = "operation";

    public static final String U_ROOT = "/";
    public static final String U_LOGIN = "/login";
    public static final String U_PROFILE = "/profile";
    public static final String U_PROFILE_UPDATE = U_PROFILE + "/update";
    public static final String U_BUDGET = "/budget";
    public static final String U_BUDGET_OPEN = U_BUDGET + "/open";
    public static final String U_BUDGET_ADD = U_BUDGET + "/add";
    public static final String U_BUDGET_ALL = U_BUDGET + "/all";
    public static final String U_BUDGET_SAVE = U_BUDGET + "/save";
    public static final String U_REGISTRATION = "/registration";
    public static final String U_OPERATION = U_BUDGET + "/operation";
    public static final String U_OPERATION_PICK = U_OPERATION + "/pick";
    public static final String U_OPERATION_PICK_PROVIDER = U_OPERATION_PICK + "/provider";
    public static final String U_OPERATION_PICK_ELEMENT = U_OPERATION_PICK + "/element";
    public static final String U_OPERATION_ADD = U_OPERATION + "/add";
    public static final String U_OPERATION_NEXT = U_OPERATION + "/next";
    public static final String U_OPERATION_WEEK = U_OPERATION + "/week";
    public static final String U_OPERATION_WEEK_NEXT = U_OPERATION_WEEK + "/next";
    public static final String U_OPERATION_WEEK_PREVIOUS = U_OPERATION_WEEK + "/previous";
    public static final String U_ELEMENT = U_BUDGET + "/element";
    public static final String U_ELEMENT_ADD = U_ELEMENT + "/add";
    public static final String U_PROVIDER = U_BUDGET + "/provider";
    public static final String U_PROVIDER_ADD = U_PROVIDER + "/add";
    public static final String U_PROVIDER_EDIT = U_PROVIDER + "/edit";
    public static final String U_PROVIDER_UPDATE = U_PROVIDER + "/update";
    public static final String U_PROVIDER_DELETE = U_PROVIDER + "/delete";
    public static final String U_HOME = "/home";

    public static String redirect(String url) {
        if (url.startsWith("/")) return "redirect:" + url;
        throw new RuntimeException("url name not exist");
    }

}
