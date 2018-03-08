package com.kasia.core.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kasia.core.model.Util.throwIAE;

public class Details {
    private Patterns NAME = Patterns.NAME;
    private Patterns SURNAME = Patterns.SURNAME;
    private Patterns FIRM = Patterns.FIRM;
    private Patterns POSITION = Patterns.POSITION;
    private String name;
    private String surname;
    private String firm;
    private String position;

    public Details() {

    }

    public Details(String name, String surname, String firm, String position) {
        setName(name);
        setSurname(surname);
        setFirm(firm);
        setPosition(position);
    }

    public void setName(String name) {
        throwIAE(name == null, "Name ain`t NULL");
        name = name.trim().toUpperCase();
        throwIAE(!NAME.matches(name), "PATTERN: " + NAME.getRegEx() + "\nCURRENT " + name);
        this.name = name;
    }

    public void setSurname(String surname) {
        throwIAE(surname == null, "Surname ain`t NULL");
        surname = surname.toUpperCase().trim();
        throwIAE(!SURNAME.matches(surname), "PATTERN: " + SURNAME.getRegEx() + "\nCURRENT " + surname);
        this.surname = surname;
    }

    public void setFirm(String firm) {
        throwIAE(firm == null, "Firm name ain`t NULL");
        firm = firm.trim().replaceAll(" {2,}", " ");
        throwIAE(!FIRM.matches(firm), "PATTERN: " + FIRM.getRegEx() + "\nCURRENT " + firm);
        this.firm = firm;
    }

    public void setPosition(String position) {
        this.position = position;
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
        return position;
    }

    public enum Patterns {
        NAME("^[A-Z]*$"),
        SURNAME("^[A-Z]*|([A-Z]+[-][A-Z]+)$"),
        FIRM("^[+&a-zA-Z0-9@.:\\-'\\(\\)\\[\\]\\{\\}<>!«»“ ]*$"),
        POSITION("^[a-zA-Z0-9]*$");

        private Pattern pattern;

        Patterns(String regEx) {
            this.pattern = Pattern.compile(regEx);
        }

        public Matcher getMatcher(String text) {
            return getPattern().matcher(text);
        }

        public boolean matches(String text) {
            return getMatcher(text).matches();
        }

        public Pattern getPattern() {
            return pattern;
        }

        public String getRegEx() {
            return getPattern().pattern();
        }
    }
//    private Long id;
//    private String nickName;
//    private String secondName;
//    private String email;
//    private String name;
//
//    public Details() {
//    }
//
//    public Details(Details details) {
//        this(details.getId(), details.getNickName(), details.getSecondName(), details.getEmail(), details.getName());
//    }
//
//    public Details(Long id, String nickName, String secondName, String email, String name) {
//        setId(id);
//        setNickName(nickName);
//        setSecondName(secondName);
//        setEmail(email);
//        setName(name);
//    }
//
//    public void setNickName(String nickName) {
//        Util.ifNullThrowNPEWithMsg(nickName, "nickName cant be null");
//        this.nickName = nickName.trim();
//    }
//
//    public String getNickName() {
//        return Util.ifNullReturnEmptyString(this.nickName);
//    }
//
//    public void setSecondName(String secondName) {
//        Util.ifNullThrowNPEWithMsg(secondName, "secondName cant be null");
//        this.secondName = secondName.trim();
//    }
//
//    public String getSecondName() {
//        return Util.ifNullReturnEmptyString(this.secondName);
//    }
//
//    public void setEmail(String email) {
//        Util.ifNullThrowNPEWithMsg(email, "email cant be null");
//        this.email = email.trim();
//    }
//
//    public String getEmail() {
//        return Util.ifNullReturnEmptyString(this.email);
//    }
//
//    public void setName(String name) {
//        Util.ifNullThrowNPEWithMsg(name, "name cant be null");
//        this.name = name.trim();
//    }
//
//    public String getName() {
//        return Util.ifNullReturnEmptyString(this.name);
//    }
//
//    protected void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Details details = (Details) o;
//
//        if (nickName != null ? !nickName.equals(details.nickName) : details.nickName != null) return false;
//        if (secondName != null ? !secondName.equals(details.secondName) : details.secondName != null) return false;
//        if (email != null ? !email.equals(details.email) : details.email != null) return false;
//        if (name != null ? !name.equals(details.name) : details.name != null) return false;
//        return id != null ? id.equals(details.id) : details.id == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = nickName != null ? nickName.hashCode() : 0;
//        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
//        result = 31 * result + (email != null ? email.hashCode() : 0);
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (id != null ? id.hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return nickName + " " + name + " " + secondName + " " + email;
//    }
}
