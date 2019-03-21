package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.ConfirmPasswordValid;
import com.kasia.controller.dto.validator.constraint.PasswordValid;

@PasswordValid(passwordFN = "password", regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,64}$"
        , message = "{validation.password.error}")
@ConfirmPasswordValid(passwordFN = "password", confirmFN = "confirm"
        , message = "{validation.password.confirm.error}")
public class Profile {
    private long userId;
    private String password;
    private String confirm;
    private boolean passwordUpdated;
    private boolean localeUpdated;
    private boolean zoneIdUpdated;
    private boolean updateZone;
    private boolean updateLocale;
    private String lang;
    private String country;
    private String zoneId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public boolean isPasswordUpdated() {
        return passwordUpdated;
    }

    public void setPasswordUpdated(boolean passwordUpdated) {
        this.passwordUpdated = passwordUpdated;
    }

    public boolean isLocaleUpdated() {
        return localeUpdated;
    }

    public void setLocaleUpdated(boolean localeUpdated) {
        this.localeUpdated = localeUpdated;
    }

    public boolean isZoneIdUpdated() {
        return zoneIdUpdated;
    }

    public void setZoneIdUpdated(boolean zoneIdUpdated) {
        this.zoneIdUpdated = zoneIdUpdated;
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

    @Override
    public String toString() {
        return "Profile{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                ", passwordUpdated=" + passwordUpdated +
                ", localeUpdated=" + localeUpdated +
                ", zoneIdUpdated=" + zoneIdUpdated +
                ", updateZone=" + updateZone +
                ", updateLocale=" + updateLocale +
                ", lang='" + lang + '\'' +
                ", country='" + country + '\'' +
                ", zoneId='" + zoneId + '\'' +
                '}';
    }
}
