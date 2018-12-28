package com.kasia.service;

import com.kasia.model.Model;

public interface ValidationService<T extends Model> {
    boolean isValid(T model);
}
