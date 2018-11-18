package com.kasia.validation.jsf.registration;

import com.kasia.message.RegistrationMessage;
import com.kasia.validation.ValidationService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PasswordValidator implements Validator<String> {
    @Inject
    private ValidationService validationService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String password) throws ValidatorException {
        password = password.trim();
        UIInput inputConfirmPassword = (UIInput) uiComponent.getAttributes().get("confirmPassword");
        String confirm = inputConfirmPassword.getSubmittedValue().toString().trim();

        if (!validationService.isMatches(password, ValidationService.PASSWORD)) {
            throw new ValidatorException(RegistrationMessage.PASSWORD_REGEX_ERROR.get(facesContext));
        }

        if (!password.equals(confirm)) {
            throw new ValidatorException(RegistrationMessage.CONFIRM_ERROR.get(facesContext));
        }
    }
}
