package com.kasia.controller;

import com.kasia.controller.dto.RegistrationDTO;
import com.kasia.controller.page.Page;
import com.kasia.controller.page.parameter.RegistrationLoginParameter;
import com.kasia.message.RegistrationMessage;
import com.kasia.model.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.DateTimeException;
import java.time.ZoneId;

@Named
@RequestScoped
public class RegistrationController implements Controller {
    @Inject
    private UserService userService;

    public String registration(RegistrationDTO dto) {
        ZoneId zone;
        try {
            zone = ZoneId.of(dto.getZone());
        } catch (DateTimeException ex) {
            zone = ZoneId.systemDefault();
        }
        userService.create(dto.getEmail(), dto.getPassword(), dto.getNick(), zone);
        addParam(RegistrationLoginParameter.REGISTRATION_INFORMATION, RegistrationMessage.REGISTRATION_SUCCESS);
        return Page.LOGIN.get();
    }

}
