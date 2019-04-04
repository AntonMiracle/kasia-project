package com.kasia.controller;

public final class ViewAndURLController {
    public static final String V_PROFILE = "profile";
    public static final String V_LOGIN = "login";
    public static final String V_REGISTRATION = "registration";
    public static final String V_BUDGET_ADD = "budgetAdd";
    public static final String V_BUDGET = "budget";
    public static final String V_PLACE = "place";
    public static final String V_PLACE_EDIT = "placeEdit";
    public static final String V_ELEMENT = "element";
    public static final String V_ELEMENT_EDIT = "elementEdit";
    public static final String V_MAIN = "main";
    public static final String V_OPERATION = "operation";
    public static final String V_OPERATION_EDIT = "operationEdit";
    public static final String V_STATISTIC = "statistic";
    public static final String V_SETTINGS = "settings";

    public static final String U_ROOT = "/";
    public static final String U_LOGIN = "/login";
    public static final String U_PROFILE = "/profile";
    public static final String U_PROFILE_UPDATE = U_PROFILE + "/update";
    public static final String U_BUDGET = "/budget";
    public static final String U_BUDGET_OPEN = U_BUDGET + "/open";
    public static final String U_BUDGET_ADD = U_BUDGET + "/add";
    public static final String U_BUDGET_SAVE = U_BUDGET + "/save";
    public static final String U_BUDGET_STATISTIC = U_BUDGET + "/statistic";
    public static final String U_REGISTRATION = "/registration";
    public static final String U_REGISTRATION_BACK = U_REGISTRATION + "/back";
    public static final String U_OPERATION = U_BUDGET + "/operation";
    public static final String U_OPERATION_PICK = U_OPERATION + "/pick";
    public static final String U_OPERATION_PICK_PLACE = U_OPERATION_PICK + "/place";
    public static final String U_OPERATION_PICK_ELEMENT = U_OPERATION_PICK + "/element";
    public static final String U_OPERATION_ADD = U_OPERATION + "/add";
    public static final String U_OPERATION_INCOME = U_OPERATION + "/income";
    public static final String U_OPERATION_CONSUMPTION = U_OPERATION + "/consumption";
    public static final String U_OPERATION_EDIT = U_OPERATION + "/edit";
    public static final String U_OPERATION_DELETE = U_OPERATION + "/delete";
    public static final String U_OPERATION_WEEK = U_OPERATION + "/week";
    public static final String U_OPERATION_WEEK_NEXT = U_OPERATION_WEEK + "/next";
    public static final String U_OPERATION_WEEK_PREVIOUS = U_OPERATION_WEEK + "/previous";
    public static final String U_ELEMENT = U_BUDGET + "/element";
    public static final String U_ELEMENT_ADD = U_ELEMENT + "/add";
    public static final String U_ELEMENT_EDIT = U_ELEMENT + "/edit";
    public static final String U_ELEMENT_UPDATE = U_ELEMENT + "/update";
    public static final String U_ELEMENT_DELETE = U_ELEMENT + "/delete";
    public static final String U_PLACE = U_BUDGET + "/place";
    public static final String U_PLACE_ADD = U_PLACE + "/add";
    public static final String U_PLACE_EDIT = U_PLACE + "/edit";
    public static final String U_PLACE_UPDATE = U_PLACE + "/update";
    public static final String U_PLACE_DELETE = U_PLACE + "/delete";
    public static final String U_MAIN = "/main";
    public static final String U_MAIN_CONNECTION_ACCEPT = U_MAIN + "/connection/accept";
    public static final String U_MAIN_CONNECTION_REJECT = U_MAIN + "/connection/reject";
    public static final String U_MAIN_DISCONNECT_FROM_BUDGET = U_MAIN + "/disconnection/budget";
    public static final String U_MAIN_DISCONNECT_USER_FROM_BUDGET = U_MAIN + "/disconnection/user";
    public static final String U_MAIN_SETTINGS = U_MAIN + "/settings";
    public static final String U_MAIN_SETTINGS_DELETE_BUDGET = U_MAIN_SETTINGS + "/delete/budget";

    public static String redirect(String url) {
        if (url.startsWith("/")) return "redirect:" + url;
        throw new RuntimeException("url name not exist");
    }
}
