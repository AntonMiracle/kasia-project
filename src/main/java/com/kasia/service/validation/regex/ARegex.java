package com.kasia.service.validation.regex;

public enum ARegex implements ModelRegex {
    NAME("^[A-Za-z0-9+_.-]{1,}$");

    private final String REGEX;

    ARegex(String regex) {
        this.REGEX = regex;
    }

    @Override
    public String getREGEX() {
        return REGEX;
    }
}
