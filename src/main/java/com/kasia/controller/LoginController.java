package com.kasia.controller;

import com.kasia.controller.dto.LoginDTO;
import com.kasia.model.service.UserModelService;
import com.kasia.page.Page;
import com.kasia.page.PageService;
import com.kasia.validation.ValidationService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class LoginController {
    public static final String PARAM_LOGIN_ERROR = "param_login_error";

    @Inject
    private PageService pageService;
    @Inject
    private UserModelService userService;
    @Inject
    private ValidationService validationService;

    public String login(LoginDTO loginDTO) {
// add principal!
        return pageService.get(Page.HOME);
    }

}
