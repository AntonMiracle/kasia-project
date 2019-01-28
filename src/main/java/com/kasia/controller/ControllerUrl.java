package com.kasia.controller;

final class ControllerUrl {
    static final String PROFILE = "profile";
    static final String HOME = "home";
    static final String LOGIN = "login";
    static final String REGISTRATION = "registration";
    static final String ROOT = "/";
    static final String BUDGET = "budget";

    static String redirect(String controllerUrl) {
        return "redirect:" + controllerUrl;
    }

}
