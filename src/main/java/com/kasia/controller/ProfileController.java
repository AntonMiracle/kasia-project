package com.kasia.controller;

import com.kasia.controller.dto.ProfileDTO;
import com.kasia.model.User;
import com.kasia.model.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.DateTimeException;
import java.time.ZoneId;

@Named
@RequestScoped
public class ProfileController {
    @Inject
    private UserService userService;

    public void update(ProfileDTO dto, User user) {
        ZoneId zone;
        try {
            zone = ZoneId.of(dto.getZoneId());
        } catch (DateTimeException ex) {
            zone = ZoneId.systemDefault();
        }
        if (dto.getPassword() != null && dto.getPassword().length() > 0) {
            String password = userService.cryptPassword(dto.getPassword());
            user.setPassword(password);
        }
        if (dto.isUpdateZoneId()) {
            user.setZoneId(zone);
        }
        userService.update(user);
    }
    public void defaultBudget(ProfileDTO dto, User user){
        if(dto.getDefaultBudgetId() > 0){
            System.out.println("======== default budget ID ==== : " + dto.getDefaultBudgetId());
            // make default budget
        }
    }
}
