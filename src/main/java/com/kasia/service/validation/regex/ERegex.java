package com.kasia.service.validation.regex;

public enum ERegex implements ModelRegex {
    NAME("^[A-Za-z0-9+_.-]{1,}$");

    private final String REGEX;

    ERegex(String regex) {
        this.REGEX = regex;
    }

    @Override
    public String getREGEX() {
        return REGEX;
    }
}
