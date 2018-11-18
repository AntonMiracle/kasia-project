package com.kasia.controller.page;

public enum Page {
    LOGIN("login")
    ,REGISTRATION("registration")
    ,HOME("home")
    ;
    private final String name;

    Page(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
