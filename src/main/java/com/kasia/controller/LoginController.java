package com.kasia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class LoginController {
    @Autowired
    private MySessionController sessionC;

    @GetMapping({U_LOGIN, U_ROOT})
    public String openLogin() {
        return sessionC.isUserLogin() ? redirect(U_MAIN) : V_LOGIN;
    }

}
