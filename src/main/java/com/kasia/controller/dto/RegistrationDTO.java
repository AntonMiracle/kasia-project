package com.kasia.controller.dto;

import com.kasia.model.Model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class RegistrationDTO implements Model {
    private String nick;
    private String password;
    private String confirm;
    private String zone;
    private String email;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String nick, String password, String confirm, String zone, String email) {
        this.nick = nick;
        this.password = password;
        this.confirm = confirm;
        this.zone = zone;
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public String toString() {
        return "Registration{" +
                "nick='" + nick + '\'' +
                ", password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                ", zone='" + zone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationDTO that = (RegistrationDTO) o;

        if (nick != null ? !nick.equals(that.nick) : that.nick != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (confirm != null ? !confirm.equals(that.confirm) : that.confirm != null) return false;
        if (zone != null ? !zone.equals(that.zone) : that.zone != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = nick != null ? nick.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (confirm != null ? confirm.hashCode() : 0);
        result = 31 * result + (zone != null ? zone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
