package com.kasia.validation.jsf;

import com.kasia.message.Message;
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

        if (!ValidationService.FieldRegex.USER_NICK.isMatch(nick)) {
            throw new ValidatorException(Message.NICK_REGEX_ERROR.get(facesContext));
        }

        if (userService.getByNick(nick) != null) {
            throw new ValidatorException(Message.NICK_EXIST.get(facesContext));
        }
    }
}
