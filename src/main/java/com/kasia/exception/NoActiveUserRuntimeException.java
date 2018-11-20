package com.kasia.exception;

public class NoActiveUserRuntimeException extends RuntimeException {
    public NoActiveUserRuntimeException() {
        super("NO login user");
    }

    public NoActiveUserRuntimeException(String message) {
        super(message);
    }

    public NoActiveUserRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoActiveUserRuntimeException(Throwable cause) {
        super(cause);
    }
}
