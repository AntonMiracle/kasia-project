package com.kasia.controller;

import com.kasia.controller.dto.UserDTO;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@EnableAutoConfiguration
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService uService;

    @ModelAttribute("userDTO")
    public UserDTO getNewUserDTO() {
        return new UserDTO();
    }

    @GetMapping
    public String openRegistration() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName().equals("anonymousUser")) return "registration";
        return "home";
    }

    @GetMapping("/back")
    public String backToLogin() {
        return "redirect:login";
    }

    @PostMapping
    public String finishRegistration(@Valid @ModelAttribute UserDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return "registration";

        User user = uService.createUser(dto.getEmail(), dto.getName(), dto.getPassword()
                , dto.getZoneId(), dto.getLang(), dto.getCountry());
        uService.saveUser(user);
        return "redirect:login?registration";
    }
}
