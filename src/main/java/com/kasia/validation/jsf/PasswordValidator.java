package com.kasia.validation.jsf;

import com.kasia.message.Message;
import com.kasia.validation.ValidationService;
import com.kasia.validation.ValidationService.FieldRegex;

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
        UIInput inputConfirmPassword = (UIInput) uiComponent.getAttributes().get("confirmPassword");
        String confirm = inputConfirmPassword.getSubmittedValue().toString();
        confirm = confirm.trim();
        password = password.trim();
        System.out.println("=========== VALIDATE PASSWORD ============");
        if (!FieldRegex.USER_PASSWORD.isMatch(password)) {
            throw new ValidatorException(Message.PASSWORD_REGEX_ERROR.get(facesContext));
        }

        if (!password.equals(confirm)) {
            throw new ValidatorException(Message.CONFIRM_ERROR.get(facesContext));
        }
    }
}
