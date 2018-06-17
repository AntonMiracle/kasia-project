package com.kasia.service;

import com.kasia.model.Model;

import javax.validation.Validator;
import java.util.Map;

public interface ValidationService<T extends Model> {

    Validator getValidator();

    Map<String, String> mapErrorFieldsWithMsg(T model);

}
