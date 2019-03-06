package com.kasia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.kasia.controller.ViewNameAndControllerURL.U_ELEMENT;
import static com.kasia.controller.ViewNameAndControllerURL.V_ELEMENT;

@Controller
public class ElementController {

    @GetMapping(U_ELEMENT)
    public String openElement(){return V_ELEMENT;}
}
