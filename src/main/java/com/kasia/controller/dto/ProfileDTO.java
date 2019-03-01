package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.UserDTOConfirmPassIsValid;
import com.kasia.controller.dto.validator.constraint.UserDTOPasswordIsValid;

@UserDTOPasswordIsValid(passwordFN = "password", message = "{validation.password.error}")
@UserDTOConfirmPassIsValid(passwordFN = "password", confirmFN = "confirm", message = "{validation.password.confirm.error}")
public class ProfileDTO {
    private boolean updateZone;
    private boolean updateLocale;
    private String lang;
    private String country;
    private String zoneId;
    private String password;
    private String confirm;

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "updateZone=" + updateZone +
                ", updateLocale=" + updateLocale +
                ", lang='" + lang + '\'' +
                ", country='" + country + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                '}';
    }

    public boolean isUpdateZone() {
        return updateZone;
    }

    public void setUpdateZone(boolean updateZone) {
        this.updateZone = updateZone;
    }

    public boolean isUpdateLocale() {
        return updateLocale;
    }

    public void setUpdateLocale(boolean updateLocale) {
        this.updateLocale = updateLocale;
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

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
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
