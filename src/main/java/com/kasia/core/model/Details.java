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

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Details details = (Details) o;

        if (id != details.id) return false;
        if (name != null ? !name.equals(details.name) : details.name != null) return false;
        if (surname != null ? !surname.equals(details.surname) : details.surname != null) return false;
        if (position != null ? !position.equals(details.position) : details.position != null) return false;
        if (nick != null ? !nick.equals(details.nick) : details.nick != null) return false;
        return email != null ? email.equals(details.email) : details.email == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
/**
 private Patterns NAME = Patterns.NAME;
 private Patterns SURNAME = Patterns.SURNAME;
 private Patterns FIRM = Patterns.FIRM;
 private Patterns POSITION = Patterns.POSITION;
 private String name;
 private String surname;
 private String firm;
 private String position;
 private long id;

 public Details() {

 }

 public Details(String name, String surname, String firm, String position) {
 setName(name);
 setSurname(surname);
 setFirm(firm);
 setPosition(position);
 }

 protected Details(Details details) {
 setId(details.getId());
 setName(details.getName());
 setSurname(details.getSurname());
 setFirm(details.getFirm());
 setPosition(details.getPosition());
 }

 public void setName(String name) {
 throwIAE(name == null, "Name is NULL");
 name = name.trim().toUpperCase();
 throwIAE(!NAME.matches(name), errorMsgWithPatterns(NAME, name));
 this.name = name;
 }

 public void setSurname(String surname) {
 throwIAE(surname == null, "Surname is NULL");
 surname = surname.toUpperCase().trim();
 throwIAE(!SURNAME.matches(surname), errorMsgWithPatterns(SURNAME, surname));
 this.surname = surname;
 }

 public void setFirm(String firm) {
 throwIAE(firm == null, "Firm is NULL");
 firm = firm.trim().toUpperCase().replaceAll(" {2,}", " ");
 throwIAE(!FIRM.matches(firm), errorMsgWithPatterns(FIRM, firm));
 this.firm = firm;
 }

 public void setPosition(String position) {
 throwIAE(position == null, "Position is NULL");
 position = position.toUpperCase().trim();
 throwIAE(!POSITION.matches(position), errorMsgWithPatterns(POSITION, position));
 this.position = position;
 }

 protected String errorMsgWithPatterns(Patterns patterns, String text) {
 throwIAE(patterns == null || text == null, "Patterns or Text is NULL");
 return "PATTERN: " + patterns.toString()
 + " LENGTH: [0," + patterns.MAX_LENGTH + "]"
 + "%nCURRENT: " + text;
 }

 public String getName() {
 return this.name != null ? this.name : "";
 }

 public String getSurname() {
 return this.surname != null ? this.surname : "";
 }

 public String getFirm() {
 return this.firm != null ? this.firm : "";
 }

 public String getPosition() {
 return this.position != null ? this.position : "";
 }

 public void setId(long id) {
 this.id = id;
 }

 public long getId() {
 return id;
 }

 @Override public boolean equals(Object o) {
 if (this == o) return true;
 if (o == null || getClass() != o.getClass()) return false;

 Details details = (Details) o;

 if (id != details.id) return false;
 if (name != null ? !name.equals(details.name) : details.name != null) return false;
 if (surname != null ? !surname.equals(details.surname) : details.surname != null) return false;
 if (firm != null ? !firm.equals(details.firm) : details.firm != null) return false;
 return position != null ? position.equals(details.position) : details.position == null;
 }

 @Override public int hashCode() {
 int result = name != null ? name.hashCode() : 0;
 result = 31 * result + (surname != null ? surname.hashCode() : 0);
 result = 31 * result + (firm != null ? firm.hashCode() : 0);
 result = 31 * result + (position != null ? position.hashCode() : 0);
 result = 31 * result + (int) (id ^ (id >>> 32));
 return result;
 }

 @Override public String toString() {
 return getId() + ":" + getName() + ":" + getSurname() + ":" + getFirm() + ":" + getPosition();
 }

 public enum Patterns {
 NAME("^[A-Z]*$", 32),
 SURNAME("^[A-Z]*|([A-Z]+[-][A-Z]+)$", 32),
 FIRM("^[+&A-Z0-9@.\\-\\(\\) ]*$", 32),
 POSITION("^[A-Z0-9 \\-&]*$", 32);

 private final int MAX_LENGTH;
 private Patterns pattern;

 Patterns(String regEx, int max) {
 MAX_LENGTH = max;
 this.pattern = Patterns.compile(regEx);
 }

 public boolean matches(String text) {
 if (text.length() > MAX_LENGTH) return false;
 return getPattern().matcher(text).matches();
 }

 public Patterns getPattern() {
 return pattern;
 }

 @Override public String toString() {
 return getPattern().pattern();
 }
 }
 */
}
