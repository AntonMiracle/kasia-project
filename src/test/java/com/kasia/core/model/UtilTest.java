package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest extends Util {

    @Test(expected = NullPointerException.class)
    public void whenArgumentNullThenThrowNullPointerException() {
        Util.ifNullThrowNPEWithMsg(null, "NPE");
    }

    @Test
    public void whenArgumentNullThrowExceptionWithMsg() {
        String expected = "msg";
        try {
            Util.ifNullThrowNPEWithMsg(null, expected);
        } catch (RuntimeException e) {
            Assert.assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void whenNullReturnEmptyString() {
        Assert.assertTrue(Util.ifNullReturnEmptyString(null).length() == 0);
    }

    @Test
    public void whenNotNullReturnArgument() {
        String argument = "some text";
        Assert.assertEquals(argument, Util.ifNullReturnEmptyString(argument));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenTrueReturnIllegalArgumentExceptionWithMsg() {
        Util.throwIAE(true, "msg");
    }
}