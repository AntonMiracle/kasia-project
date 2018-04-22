package com.kasia.core.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Details implements Serializable {
    private Long id;
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{0,15}$")
    private String name;
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{0,25}|([A-Za-z0-9]{1,25}[ \\-][A-Za-z0-9]{1,25})$")
    private String surname;
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{0,30}|([A-Za-z0-9]{1,25}[ \\-][A-Za-z0-9]{1,25})$")
    private String position;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_\\-]{5,20}$")
    private String nick;
    @NotNull
    @Size(max = 64)
    @Pattern(regexp = "^.+@.+\\..+$")
    private String email;

    public Details() {

    }

    public Details(Details details) {
        setId(details.getId());
        setName(details.getName());
        setSurname(details.getSurname());
        setPosition(details.getPosition());
        setNick(details.getNick());
        setEmail(details.getEmail());
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Details details = (Details) o;

        if (id != null ? !id.equals(details.id) : details.id != null) return false;
        if (name != null ? !name.equals(details.name) : details.name != null) return false;
        if (surname != null ? !surname.equals(details.surname) : details.surname != null) return false;
        if (position != null ? !position.equals(details.position) : details.position != null) return false;
        if (nick != null ? !nick.equals(details.nick) : details.nick != null) return false;
        return email != null ? email.equals(details.email) : details.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Details{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", position='" + position + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
