package com.kasia.exception;

public class FieldNotExistRuntimeException extends RuntimeException {
    public FieldNotExistRuntimeException() {
        super();
    }

    public FieldNotExistRuntimeException(String message) {
        super(message);
    }

    public FieldNotExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldNotExistRuntimeException(Throwable cause) {
        super(cause);
    }
}
