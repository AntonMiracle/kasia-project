package com.kasia.controller;

import com.kasia.controller.dto.LocaleZoneIdDTO;
import com.kasia.controller.dto.PasswordUpdateDTO;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

import static com.kasia.controller.ControllerUrl.*;

@Controller
@RequestMapping(PROFILE)
public class ProfileController {

    @Autowired
    private UserService uService;
    @Autowired
    private AppControllerAdvice appCA;
    private final String UPDATE_ATT = "update";

    @ModelAttribute("passwordUpdateDTO")
    public PasswordUpdateDTO getNewPasswordUpdateDTO() {
        return new PasswordUpdateDTO();
    }

    @ModelAttribute("localeZoneIdDTO")
    public LocaleZoneIdDTO getLocaleZoneIdDTO() {
        return new LocaleZoneIdDTO();
    }

    @GetMapping
    public String openProfile(Model model) {
        model.addAttribute("openProfile", "openProfile");
        return HOME;
    }

    @PostMapping("updatePassword")
    public String updateProfilePassword(Model model, Principal principal, @Valid @ModelAttribute PasswordUpdateDTO dto, BindingResult bResult) {

        User user = appCA.getAuthenticationUser(principal);

        if (bResult.hasErrors()) return openProfile(model);

        user.setPassword(uService.cryptPassword(dto.getPassword()));
        uService.saveUser(user);

        model.addAttribute(UPDATE_ATT, "password");
        return HOME;
    }

    @PostMapping("updateZoneLocale")
    public String updateProfile(Model model, Principal principal, @ModelAttribute LocaleZoneIdDTO dto) {
        openProfile(model);
        User user = appCA.getAuthenticationUser(principal);
        boolean anyChanges = false;

        if (dto.isUpdateZone() && dto.getZoneId().length() > 0) {
            user.setZoneId(uService.zoneIdOf(dto.getZoneId()));
            anyChanges = true;
            dto.setUpdateZone(false);
        }
        if (dto.isUpdateLocale() && dto.getLang().length() > 0 && dto.getCountry().length() > 0) {
            user.setLocale(uService.localeOf(dto.getLang(), dto.getCountry()));
            anyChanges = true;
            dto.setUpdateLocale(false);
        }
        if (anyChanges) {
            uService.saveUser(user);
            model.addAttribute(UPDATE_ATT, "zoneLocal");
        }
        return HOME;
    }

}
