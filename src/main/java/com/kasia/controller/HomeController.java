package com.kasia.controller;

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

@Controller
@RequestMapping("home")
public class HomeController {
    @Autowired
    private UserService uService;
    private final String OPEN_PROFILE_ATTRIBUTE_NAME = "openProfile";
    private final String USER_ATTRIBUTE_NAME = "user";
    private final String PROFILE_LOCALE_UPDATE_ATTRIBUTE_NAME = "updateLocale";
    private final String PROFILE_ZONEID_UPDATE_ATTRIBUTE_NAME = "updateZoneid";

    @GetMapping("profile")
    public String openProfile(Model model, Principal principal, PasswordUpdateDTO dto) {
        User user = uService.findUserByEmail(principal.getName());
        model.addAttribute(USER_ATTRIBUTE_NAME, user);

        model.addAttribute(OPEN_PROFILE_ATTRIBUTE_NAME, OPEN_PROFILE_ATTRIBUTE_NAME);

//

//        model.addAttribute(PROFILE_LOCALE_UPDATE_ATTRIBUTE_NAME, "");
//        model.addAttribute(PROFILE_ZONEID_UPDATE_ATTRIBUTE_NAME, "");
        return "home";
    }

    @PostMapping("profile/updatePassword")
    public String updateProfile(Model model, Principal principal, @Valid @ModelAttribute PasswordUpdateDTO dto, BindingResult bResult) {
        User user = uService.findUserByEmail(principal.getName());
        model.addAttribute(USER_ATTRIBUTE_NAME, user);


        System.out.println(dto);
        if (bResult.hasErrors()) {
            model.addAttribute(OPEN_PROFILE_ATTRIBUTE_NAME, OPEN_PROFILE_ATTRIBUTE_NAME);
        }
        return "home";
    }

    @GetMapping
    public String openHome() {
        return "home";
    }
}
