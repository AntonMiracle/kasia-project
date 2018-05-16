package com.kasia.core.model;

public interface CoreModel {
    long getId();

    /**
     * Is model representation nothing.
     * Default return false.
     *
     * @return true if and only if model representation nothing
     */
    boolean isNull();

    void setNull(boolean isNull);
}
