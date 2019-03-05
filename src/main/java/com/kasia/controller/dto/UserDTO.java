package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.ConfirmPasswordValid;
import com.kasia.controller.dto.validator.constraint.UserEmailValid;
import com.kasia.controller.dto.validator.constraint.UserNameValid;
import com.kasia.controller.dto.validator.constraint.UserPasswordValid;

@UserEmailValid(emailFN = "email", min = 3, max = 64, regex = "^\\S+@\\S+$", message = "{validation.email.error}")
@UserNameValid(nameFN = "name", min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@UserPasswordValid(passwordFN = "password", regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,64}$"
        , message = "{validation.password.error}")
@ConfirmPasswordValid(passwordFN = "password", confirmFN = "confirm"
        , message = "{validation.password.confirm.error}")
public class UserDTO {
    private String email;
    private String name;
    private String password;
    private String confirm;
    private String zoneId;
    private String lang;
    private String country;
    private boolean updateZone;
    private boolean updateLocale;

    public UserDTO(String email, String name, String password, String confirm, String zoneId, String lang, String country, boolean updateZone, boolean updateLocale) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.confirm = confirm;
        this.zoneId = zoneId;
        this.lang = lang;
        this.country = country;
        this.updateZone = updateZone;
        this.updateLocale = updateLocale;
    }

    public UserDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (updateZone != userDTO.updateZone) return false;
        if (updateLocale != userDTO.updateLocale) return false;
        if (email != null ? !email.equals(userDTO.email) : userDTO.email != null) return false;
        if (name != null ? !name.equals(userDTO.name) : userDTO.name != null) return false;
        if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;
        if (confirm != null ? !confirm.equals(userDTO.confirm) : userDTO.confirm != null) return false;
        if (zoneId != null ? !zoneId.equals(userDTO.zoneId) : userDTO.zoneId != null) return false;
        if (lang != null ? !lang.equals(userDTO.lang) : userDTO.lang != null) return false;
        return country != null ? country.equals(userDTO.country) : userDTO.country == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (confirm != null ? confirm.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (updateZone ? 1 : 0);
        result = 31 * result + (updateLocale ? 1 : 0);
        return result;
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
                ", updateZone=" + updateZone +
                ", updateLocale=" + updateLocale +
                '}';
    }

    public String getEmail() {
        return email;
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
