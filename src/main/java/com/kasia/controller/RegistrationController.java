package com.kasia.controller;

import com.kasia.dto.RegistrationDTO;
import com.kasia.page.Page;
import com.kasia.page.Attribute;
import com.kasia.message.Message;
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
        addAttribute(Attribute.LOGIN_INFORMATION, Message.REGISTRATION_SUCCESS);
        return Page.LOGIN.relativePath();
    }

}
