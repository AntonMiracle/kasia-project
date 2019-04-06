package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.ConfirmPasswordValid;
import com.kasia.controller.dto.validator.constraint.EmailValid;
import com.kasia.controller.dto.validator.constraint.PasswordValid;

@EmailValid(emailFN = "email", min = 3, max = 64, regex = "^\\S+@\\S+$", message = "{validation.email.error}")
@PasswordValid(passwordFN = "password", regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,64}$"
        , message = "{validation.password.error.simple}")
@ConfirmPasswordValid(passwordFN = "password", confirmFN = "confirm"
        , message = "{validation.password.confirm.error}")
public class Registration {
    private String email;
    private String password;
    private String confirm;
    private String zoneId;
    private String lang;
    private String country;
    private boolean updateZone;
    private boolean updateLocale;

    public Registration(String email, String password, String confirm, String zoneId, String lang, String country, boolean updateZone, boolean updateLocale) {
        this.email = email;
        this.password = password;
        this.confirm = confirm;
        this.zoneId = zoneId;
        this.lang = lang;
        this.country = country;
        this.updateZone = updateZone;
        this.updateLocale = updateLocale;
    }

    public Registration() {
    }

    @Override
    public String toString() {
        return "Registration{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", lang='" + lang + '\'' +
                ", country='" + country + '\'' +
                ", updateZone=" + updateZone +
                ", updateLocale=" + updateLocale +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Registration that = (Registration) o;

        if (updateZone != that.updateZone) return false;
        if (updateLocale != that.updateLocale) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (confirm != null ? !confirm.equals(that.confirm) : that.confirm != null) return false;
        if (zoneId != null ? !zoneId.equals(that.zoneId) : that.zoneId != null) return false;
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        return country != null ? country.equals(that.country) : that.country == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (confirm != null ? confirm.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (updateZone ? 1 : 0);
        result = 31 * result + (updateLocale ? 1 : 0);
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
