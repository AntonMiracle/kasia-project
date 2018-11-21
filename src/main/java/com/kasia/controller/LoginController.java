package com.kasia.controller;

import com.kasia.controller.dto.LoginDTO;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import com.kasia.page.Page;
import com.kasia.security.SecurityService;
import com.kasia.validation.ValidationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginController implements Controller {
    @Inject
    private UserService userService;
    @Inject
    private ValidationService validationService;
    @Inject
    private SecurityService securityService;

    public String login(LoginDTO loginDTO) {
        User user = userService.getByEmail(loginDTO.getEmail());
        securityService.setActiveUser(user);
        return Page.HOME.relativePath();
    }

    public String logOut() {
        securityService.removeActiveUser();
        return Page.LOGIN.relativePath();
    }
}
