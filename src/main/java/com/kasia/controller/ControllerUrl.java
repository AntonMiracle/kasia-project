package com.kasia.controller;

public final class ControllerUrl {
    protected static final String PROFILE = "profile";
    protected static final String HOME = "home";
    protected static final String LOGIN = "login";
    protected static final String REGISTRATION = "registration";
    protected static final String ROOT = "/";

    protected static String redirect(String controllerUrl) {
        return "redirect:" + controllerUrl;
    }

}
