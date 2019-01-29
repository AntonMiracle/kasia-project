package com.kasia.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.kasia.controller.ViewNameAndControllerURL.*;

@Controller
@EnableAutoConfiguration
public class LoginController {

    @GetMapping({U_LOGIN, U_ROOT})
    public String openLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName().equals("anonymousUser")) return V_LOGIN;
        return redirect(U_HOME);
    }
}
