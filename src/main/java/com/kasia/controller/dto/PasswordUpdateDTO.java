package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.UserConfirmPasswordIsValid;
import com.kasia.controller.dto.validator.constraint.UserPasswordIsValid;

@UserPasswordIsValid(fieldName = "password", message = "{validation.password.error}")
@UserConfirmPasswordIsValid(passwordFN = "password", confirmFN = "confirm", message = "{validation.password.confirm.error}")
public class PasswordUpdateDTO {
    private String password;
    private String confirm;

    @Override
    public String toString() {
        return "PasswordUpdateDTO{" +
                "password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                '}';
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
}
