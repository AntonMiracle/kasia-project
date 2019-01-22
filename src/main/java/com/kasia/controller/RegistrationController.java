package com.kasia.controller;

import com.kasia.controller.dto.UserDTO;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
        return "registration";
    }

    @PostMapping
    public String getRegistrationData(@Valid @ModelAttribute UserDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return "registration";

        User user = uService.createUser(dto.getEmail(), dto.getName(), dto.getPassword()
                , dto.getZoneId(), dto.getLang(), dto.getCountry());
        user = uService.saveUser(user);
        System.out.println(user);
        return "redirect:index";
    }

    @PostMapping(params = "back")
    public String back() {
        return "redirect:index";
    }
//
//    @GetMapping(value = "/users/{user}")
//    public User findUserById(@PathVariable Long user) {
//        return uService.findUserById(user);
//    }
//
//    @GetMapping(value = "/users/name/{name}")
//    public User findUserByName(@PathVariable String name) {
//        return uService.findUserByName(name);
//    }
//
//    @GetMapping(value = "/users/email/{email}")
//    public User findUserByEmail(@PathVariable String email) {
//        return uService.findUserByEmail(email);
//    }
//
//    @DeleteMapping(value = "/users/{user}")
//    public boolean deleteUser(@PathVariable Long user) {
//        return uService.deleteUser(user);
//    }

    @PutMapping(value = "/users/new")
    public User saveNew(@RequestBody UserDTO dto) {
        User user = uService.createUser(dto.getEmail(), dto.getName(), dto.getPassword()
                , dto.getZoneId(), dto.getLang(), dto.getCountry());
        return uService.saveUser(user);
    }

}
