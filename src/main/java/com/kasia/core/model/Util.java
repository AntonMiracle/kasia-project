package com.kasia.core.model;


public class Util {

    protected static void ifNullThrowNPEWithMsg(Object object, String msg) {
        if (object == null) throw new NullPointerException(msg);
    }

    protected static String ifNullReturnEmptyString(String text) {
        return text == null ? "" : text;
    }

    public static void throwIAE(boolean whenTrue, String withMSG) {
        if(whenTrue) throw new IllegalArgumentException(withMSG);
    }
}
