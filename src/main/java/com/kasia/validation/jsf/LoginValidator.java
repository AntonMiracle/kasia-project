package com.kasia.validation.jsf;

import com.kasia.message.Message;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
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
import java.time.ZoneId;

import static com.kasia.validation.ValidationService.FieldRegex.USER_PASSWORD;

@Named
@RequestScoped
public class LoginValidator implements Validator<String> {
    @Inject
    private UserService userService;
    @Inject
    private ValidationService validationService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String email) throws ValidatorException {
        // for testing
        if (userService.getByEmail("anton@gmail.com") == null)
            userService.create("anton@gmail.com", "Password2", "Anton", ZoneId.systemDefault());
        // end test


        UIInput inputConfirmPassword = (UIInput) uiComponent.getAttributes().get("password");
        String password = inputConfirmPassword.getSubmittedValue().toString();
        email = email.trim();
        password = password.trim();

        if (!FieldRegex.USER_EMAIL.isMatch(email) || !USER_PASSWORD.isMatch(password)) {
            throw new ValidatorException(Message.LOGIN_ERROR.get(facesContext));
        }

        password = userService.cryptPassword(password);
        User user = userService.getByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new ValidatorException(Message.LOGIN_ERROR.get(facesContext));
        }
    }
}
