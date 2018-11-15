package com.kasia.exception;

public class RegexNotExistRunTimeException extends RuntimeException {
    public RegexNotExistRunTimeException() {
        super("Regular expression not exist");
    }

    public RegexNotExistRunTimeException(String message) {
        super(message);
    }

    public RegexNotExistRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegexNotExistRunTimeException(Throwable cause) {
        super(cause);
    }
}
