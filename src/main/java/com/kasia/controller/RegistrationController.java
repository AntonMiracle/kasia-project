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

    @GetMapping
    public String injectUserDTO(UserDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName().equals("anonymousUser")) return "registration";
        return "home";
    }

    @GetMapping("/back")
    public String injectUserDTO() {
        return "redirect:login";
    }

    @PostMapping
    public String getRegistrationData(@Valid @ModelAttribute UserDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return "registration";

        User user = uService.createUser(dto.getEmail(), dto.getName(), dto.getPassword()
                , dto.getZoneId(), dto.getLang(), dto.getCountry());
        uService.saveUser(user);
        System.out.println("================== RegistrationController#getRegistrationData ");
        System.out.println(user);
        return "redirect:login?registration";
    }

    @PostMapping(params = "back")
    public String back() {
        return "redirect:login";
    }
}
