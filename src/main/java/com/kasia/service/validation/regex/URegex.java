package com.kasia.service.validation.regex;

public enum URegex implements ModelRegex {
    EMAIL("^[A-Za-z0-9+_.-]+@(.+)$"), PASSWORD("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$"), NICK("^[A-Za-z0-9+_.-]{3,}$");

    private final String REGEX;

    URegex(String regex) {
        this.REGEX = regex;
    }

    @Override
    public String getREGEX() {
        return REGEX;
    }
}
