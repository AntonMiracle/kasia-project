package com.kasia.validation;

import com.kasia.validation.jsf.PasswordValidator;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UpdatePasswordValidator implements Validator<String> {
    @Inject
    private PasswordValidator passwordValidator;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String password) throws ValidatorException {
        if (password != null && password.length() > 0) {
            passwordValidator.validate(facesContext, uiComponent, password);
        }
    }
}
