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
public class NickValidator implements Validator<String> {
    @Inject
    private ValidationService validationService;
    @Inject
    private UserService userService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String nick) throws ValidatorException {
        nick = nick.trim();

        if (!validationService.isMatches(nick, ValidationService.NICK)) {
            throw new ValidatorException(RegistrationMessage.NICK_REGEX_ERROR.get(facesContext));
        }

        if (userService.getByNick(nick) != null) {
            throw new ValidatorException(RegistrationMessage.NICK_EXIST.get(facesContext));
        }
    }
}
