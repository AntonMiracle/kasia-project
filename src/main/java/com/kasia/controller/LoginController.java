package com.kasia.controller;

import com.kasia.model.User;
import com.kasia.service.UserService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class LoginController {
    public static final String PARAM_LOGIN_ERROR = "param_login_error";

    @Inject
    private UserService userService;

    public String login(User user) {
        if (user == null || user.getPassword() == null || user.getEmail() == null) {
            return loginError();
        } else {
            String password = userService.cryptPassword(user.getPassword());
            user = userService.getByEmail(user.getEmail());
            if (user != null && user.getPassword().equals(password)) {
                return "home";
            } else {
                return loginError();
            }
        }
    }

    private String loginError() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setAttribute(PARAM_LOGIN_ERROR, "email or password incorrect");
        return "login";
    }
}
