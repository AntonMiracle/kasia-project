package com.kasia.core.util;

public class ExceptionThrower {

    private ExceptionThrower() {
    }

    /**
     * Throws NPE if and only if ifNull is null
     *
     * @param ifNull
     * @throws NullPointerException if and only if ifNull is null
     */
    public static void NPE(Object ifNull) throws NullPointerException {
        if (ifNull == null) throw new NullPointerException();
    }
}
