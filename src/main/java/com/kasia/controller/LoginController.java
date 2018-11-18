package com.kasia.controller;

import com.kasia.controller.dto.LoginDTO;
import com.kasia.model.service.UserService;
import com.kasia.controller.page.Page;
import com.kasia.validation.ValidationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginController implements Controller{
    @Inject
    private UserService userService;
    @Inject
    private ValidationService validationService;

    public String login(LoginDTO loginDTO) {
// add principal!
        return Page.HOME.get();
    }

}
