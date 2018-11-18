package com.kasia.validation.jsf.registration;

import com.kasia.message.RegistrationMessage;
import com.kasia.model.service.UserService;
import com.kasia.validation.ValidationService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class EmailValidator implements Validator<String> {
    @Inject
    private ValidationService validationService;
    @Inject
    private UserService userService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String email) throws ValidatorException {
        email = email.trim();

        if (!validationService.isMatches(email, ValidationService.EMAIL)) {
            throw new ValidatorException(RegistrationMessage.EMAIL_REGEX_ERROR.get(facesContext));
        }

        if (userService.getByEmail(email) != null) {
            throw new ValidatorException(RegistrationMessage.EMAIL_EXIST.get(facesContext));
        }
    }
}
