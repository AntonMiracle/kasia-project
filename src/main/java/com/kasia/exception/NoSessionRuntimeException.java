package com.kasia.exception;

public class NoSessionRuntimeException extends RuntimeException {
    public NoSessionRuntimeException() {
        super("session not exist");
    }

    public NoSessionRuntimeException(String message) {
        super(message);
    }

    public NoSessionRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSessionRuntimeException(Throwable cause) {
        super(cause);
    }
}
