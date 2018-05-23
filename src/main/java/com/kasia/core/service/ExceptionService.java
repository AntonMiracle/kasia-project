package com.kasia.core.service;

public interface ExceptionService {

    default void NPE(Object ifNull) throws NullPointerException {
        if (ifNull == null) throw new NullPointerException();
    }
}
