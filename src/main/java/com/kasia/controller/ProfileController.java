package com.kasia.controller;

import com.kasia.controller.dto.UserDTO;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.Validator;

import static com.kasia.controller.ViewNameAndControllerURL.*;

@Controller
public class ProfileController {
    @Autowired
    private MySessionController sessionController;
    @Autowired
    private UserService uService;

    @ModelAttribute("userDTO")
    public UserDTO getUserDTO() {
        User user = sessionController.getUser();
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setCountry(user.getLocale().getCountry());
        dto.setLang(user.getLocale().getLanguage());
        dto.setZoneId(user.getZoneId().toString());
        return dto;
    }

    @GetMapping(U_PROFILE)
    public String openProfile() {
        return V_PROFILE;
    }

    @Autowired
    private Validator validator;

    @PostMapping(U_PROFILE_UPDATE)
    public String updateProfile(Model model, @Valid @ModelAttribute UserDTO dto, BindingResult bResult) {
        User user = sessionController.getUser();
        boolean isAnyChanges = false;
        if (validator.validate(dto).stream().filter(cv -> cv.getPropertyPath().toString().equals("password")).count() == 0
                && validator.validate(dto).stream().filter(cv -> cv.getPropertyPath().toString().equals("confirm")).count() == 0) {
            user.setPassword(uService.cryptPassword(dto.getPassword()));
            isAnyChanges = true;
            model.addAttribute("updatePass", "passUpdate");
        }

        if (dto.isUpdateZone() && dto.getZoneId().length() > 0) {
            user.setZoneId(uService.zoneIdOf(dto.getZoneId()));
            isAnyChanges = true;
            dto.setUpdateZone(false);
            model.addAttribute("updateZoneId", "zoneIdUpdate");
        }
        if (dto.isUpdateLocale() && dto.getLang().length() > 0 && dto.getCountry().length() > 0) {
            user.setLocale(uService.localeOf(dto.getLang(), dto.getCountry()));
            isAnyChanges = true;
            dto.setUpdateLocale(false);
            model.addAttribute("updateLocale", "localeUpdate");
        }
        if (isAnyChanges) {
            uService.saveUser(user);
            System.out.println("user save");
        }
        return openProfile();
    }
}
