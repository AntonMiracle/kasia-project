package com.kasia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.kasia.controller.ViewNameAndControllerURL.U_HOME;
import static com.kasia.controller.ViewNameAndControllerURL.V_HOME;

@Controller
@RequestMapping(U_HOME)
public class HomeController {
    @GetMapping
    public String openHome() {
        return V_HOME;
    }
}
