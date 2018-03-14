package com.kasia.core.model;


public class Util {

    public static void throwIAE(boolean whenTrue, String withMSG) {
        if (whenTrue) throw new IllegalArgumentException("%n" + withMSG);
    }
}
