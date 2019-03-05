package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.UserDTOConfirmPassIsValid;
import com.kasia.controller.dto.validator.constraint.UserDTOEmailIsValid;
import com.kasia.controller.dto.validator.constraint.UserNameValid;
import com.kasia.controller.dto.validator.constraint.UserDTOPasswordIsValid;

@UserDTOEmailIsValid(message = "{validation.email.error}")
@UserNameValid(nameFN = "name", min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$",message = "{validation.name.error}")
@UserDTOPasswordIsValid(passwordFN = "password", message = "{validation.password.error}")
@UserDTOConfirmPassIsValid(passwordFN = "password", confirmFN = "confirm", message = "{validation.password.confirm.error}")
public class UserDTO {
    private String email;
    private String name;
    private String password;
    private String confirm;
    private String zoneId;
    private String lang;
    private String country;

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", lang='" + lang + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
