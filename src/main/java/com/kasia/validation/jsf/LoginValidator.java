package com.kasia.validation.jsf;

import com.kasia.message.LoginMessage;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import com.kasia.validation.ValidationService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import static com.kasia.validation.ValidationService.*;

@Named
@RequestScoped
public class LoginValidator implements Validator<String> {
    @Inject
    private UserService userService;
    @Inject
    private ValidationService validationService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String email) throws ValidatorException {
        email = email.trim();
        UIInput inputConfirmPassword = (UIInput) uiComponent.getAttributes().get("password");
        String password = inputConfirmPassword.getSubmittedValue().toString().trim();

        if (!validationService.isMatches(email, EMAIL) || !validationService.isMatches(password, PASSWORD)) {
            throw new ValidatorException(LoginMessage.LOGIN_ERROR.get(facesContext));
        }

        password = userService.cryptPassword(password);
        User user = userService.getByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new ValidatorException(LoginMessage.LOGIN_ERROR.get(facesContext));
        }
    }
}
