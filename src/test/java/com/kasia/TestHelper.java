package com.kasia;

import java.lang.reflect.Field;

public interface TestHelper<T> {

    default void setProtectedId(T where, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = field = where.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(where, value);
    }
}
