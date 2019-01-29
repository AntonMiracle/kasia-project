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

import static com.kasia.controller.ViewNameAndControllerURL.*;

@Controller
@EnableAutoConfiguration
@RequestMapping(V_REGISTRATION)
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
        if (auth == null || auth.getName().equals("anonymousUser")) return V_REGISTRATION;
        return V_LOGIN;
    }

    @GetMapping("/back")
    public String backToLogin() {
        return redirect(U_LOGIN);
    }

    @PostMapping
    public String finishRegistration(@Valid @ModelAttribute UserDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return V_REGISTRATION;

        User user = uService.createUser(dto.getEmail(), dto.getName(), dto.getPassword()
                , dto.getZoneId(), dto.getLang(), dto.getCountry());
        uService.saveUser(user);
        return redirect(U_LOGIN + "?registration");
    }
}
