package com.kasia.controller;

import com.kasia.model.User;
import com.kasia.service.model.UserService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class RegistrationController {
    public static final String PARAM_EMAIL_ERROR = "param_email_error";
    public static final String PARAM_PASSWORD_ERROR = "param_password_error";
    public static final String PARAM_REPEAT_PASSWORD_ERROR = "param_repeat_password_error";
    public static final String PARAM_NICK_ERROR = "param_nick_error";
    @Inject
    private UserService userService;

    public void registration(User user, String repeatPassword) {
        if (user == null) {
            addEmailError();
            addPasswordError();
            addRepeatPasswordError();
            addNickError();
            return;
        }
        if (user.getEmail() == null || user.getEmail().length() < 5 || userService.getByEmail(user.getEmail()) != null){

        }
    }

    private void addError(String paramName, String message) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setAttribute(paramName, message);
    }

    private void addEmailError() {
        addError(PARAM_EMAIL_ERROR, "Email incorrect or already registered");
    }

    private void addPasswordError() {
        addError(PARAM_PASSWORD_ERROR, "Password incorrect do not match");
    }

    private void addRepeatPasswordError() {
        addError(PARAM_REPEAT_PASSWORD_ERROR, "Passwords do not match");
    }

    private void addNickError() {
        addError(PARAM_NICK_ERROR, "Nick incorect");

    }
}
