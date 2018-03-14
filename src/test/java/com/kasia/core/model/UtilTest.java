package com.kasia.core.model;

import org.junit.Test;

public class UtilTest extends Util {

    @Test(expected = IllegalArgumentException.class)
    public void whenTrueReturnIllegalArgumentExceptionWithMsg() {
        Util.throwIAE(true, "msg");
    }
}