package com.kasia.controller;

import com.kasia.model.User;
import com.kasia.service.ValidationService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RegistrationController {
    @Inject
    private ValidationService<User> userValidationService;

    public void registration(User user){

    }

}
