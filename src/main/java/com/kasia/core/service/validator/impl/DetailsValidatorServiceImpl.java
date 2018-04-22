package com.kasia.core.service.validator.impl;

import com.kasia.core.model.Details;
import com.kasia.core.service.validator.DetailsValidatorService;

public class DetailsValidatorServiceImpl implements DetailsValidatorService {

    @Override
    public void trimFields(Details details) {
        if (details.getPosition() != null) details.setPosition(details.getPosition().trim());
        if (details.getSurname() != null) details.setSurname(details.getSurname().trim());
        if (details.getName() != null) details.setName(details.getName().trim());
        if (details.getEmail() != null) details.setEmail(details.getEmail().trim());
        if (details.getNick() != null) details.setNick(details.getNick().trim());
    }
}
