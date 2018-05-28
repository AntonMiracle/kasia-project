package com.kasia.core;

import com.kasia.core.model.Model;

import java.lang.reflect.Field;

public interface TestHelper<T extends Model> {

    default void setProtectedId(T where, Object value) {
        try {
            Field field = field = where.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(where, new Long(0));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    default String getGroupTypeNameForTesting1() {
        return "no";
    }

    default String getGroupTypeNameForTesting2() {
        return "ns";
    }

    default String getRoleNameForTesting1() {
        return "ro";
    }

    default String getRoleNameForTesting2() {
        return "rs";
    }

}
