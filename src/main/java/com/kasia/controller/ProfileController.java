package com.kasia.controller;

import com.kasia.controller.dto.Profile;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProfileController {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private UserService userS;

    @ModelAttribute("profile")
    public Profile getProfile() {
        Profile profile = new Profile();
        profile.setUserId(sessionC.getUser().getId());
        return profile;
    }

    @GetMapping(U_PROFILE)
    public String openProfile() {
        return sessionC.isUserLogin() ? V_PROFILE : redirect(U_LOGIN);
    }

    @PostMapping(U_PROFILE_UPDATE)
    public String updateProfile(Model model, @Valid @ModelAttribute Profile dto, BindingResult bResult) {
        User user = sessionC.getUser();
        if ((dto.getPassword().length() > 0 || dto.getConfirm().length() > 0) && !bResult.hasErrors()) {
            user.setPassword(userS.cryptPassword(dto.getPassword()));
            dto.setPasswordUpdated(true);
            userS.save(user);
        }
        if (dto.isUpdateZone()) {
            try {
                user.setZoneId(ZoneId.of(dto.getZoneId()));
                userS.save(user);
                dto.setUpdateZone(false);
                dto.setZoneIdUpdated(true);
            } catch (DateTimeException ex) {
            }
        }
        if (dto.isUpdateLocale()) {
            try {
                Locale locale = new Locale(dto.getLang(), dto.getCountry());
                if (availableLocales().contains(locale)) {
                    user.setLocale(locale);
                    userS.save(user);
                    dto.setUpdateLocale(false);
                    dto.setLocaleUpdated(true);
                }
            } catch (NullPointerException ex) {
            }
        }
        model.addAttribute("profile", dto);
        return openProfile();
    }

    private Set<Locale> availableLocales() {
        Set<Locale> locales = new HashSet<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            if (locale.getLanguage().length() > 0 && locale.getCountry().length() > 0)
                locales.add(locale);
        }
        return locales;
    }
}
