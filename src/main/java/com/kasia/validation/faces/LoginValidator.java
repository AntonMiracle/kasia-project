package com.kasia.validation.faces;

import com.kasia.message.LoginMessage;
import com.kasia.model.User;
import com.kasia.model.service.UserModelService;
import com.kasia.validation.ValidationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class LoginValidator implements Validator<String> {
    @Inject
    private UserModelService userService;
    @Inject
    private ValidationService validationService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String email) throws ValidatorException {
        email = email.trim();
        String password = ((String) uiComponent.getAttributes().get("password")).trim();
        boolean isSuccess = false;

        if (validationService.isMatches(email, ValidationService.EMAIL) && userService.getByEmail(email) != null) {
            password = userService.cryptPassword(password);
            User user = userService.getByEmail(email);
            isSuccess = user.getPassword().equals(password);
        }

        if (!isSuccess) throw new ValidatorException(LoginMessage.LOGIN_ERROR.get(facesContext));

    }
}
