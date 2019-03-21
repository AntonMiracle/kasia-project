package com.kasia.controller;

import com.kasia.controller.dto.Registration;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class RegistrationController {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private UserService userS;

    @ModelAttribute("registration")
    public Registration getRegistration() {
        return new Registration();
    }

    @GetMapping(U_REGISTRATION)
    public String openRegistration() {
        return sessionC.isUserLogin() ? redirect(U_HOME) : V_REGISTRATION;
    }

    @GetMapping(U_REGISTRATION_BACK)
    public String backToLogin() {
        return redirect(U_LOGIN);
    }

    @PostMapping(U_REGISTRATION)
    public String registration(@Valid @ModelAttribute Registration registration, BindingResult bResult) {
        if (bResult.hasErrors()) return V_REGISTRATION;
        userS.save(userS.convert(registration));
        return redirect(U_LOGIN + "?registration");
    }
}
