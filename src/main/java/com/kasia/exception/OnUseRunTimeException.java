package com.kasia.exception;

public class OnUseRunTimeException extends RuntimeException {
    public OnUseRunTimeException() {
        super();
    }

    public OnUseRunTimeException(String message) {
        super(message);
    }

    public OnUseRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnUseRunTimeException(Throwable cause) {
        super(cause);
    }
}
