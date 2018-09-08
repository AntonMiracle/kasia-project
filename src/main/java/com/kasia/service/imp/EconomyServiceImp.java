package com.kasia.service.imp;

import com.kasia.model.Economy;
import com.kasia.service.EconomyService;

import javax.validation.ValidatorFactory;

public class EconomyServiceImp implements EconomyService {
    @Override
    public boolean isValid(Economy model) throws NullPointerException {
        if (model == null) throw new NullPointerException();

        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }
}
