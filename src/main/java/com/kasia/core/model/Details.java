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
    private long id;

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
        firm = firm.trim().toUpperCase().replaceAll(" {2,}", " ");
        throwIAE(!FIRM.matches(firm), "PATTERN: " + FIRM.getRegEx() + "\nCURRENT " + firm);
        this.firm = firm;
    }

    public void setPosition(String position) {
        throwIAE(position == null, "Position name ain`t NULL");
        position = position.toUpperCase().trim();
        throwIAE(!POSITION.matches(position), "PATTERN: " + POSITION.getRegEx() + "\nCURRENT " + position);
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
        return this.position != null ? this.position : "";
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Details details = (Details) o;

        if (id != details.id) return false;
        if (name != null ? !name.equals(details.name) : details.name != null) return false;
        if (surname != null ? !surname.equals(details.surname) : details.surname != null) return false;
        if (firm != null ? !firm.equals(details.firm) : details.firm != null) return false;
        return position != null ? position.equals(details.position) : details.position == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (firm != null ? firm.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return getId() + ":" + getName() + ":" + getSurname() + ":" + getFirm() + ":" + getPosition();
    }

    public enum Patterns {
        NAME("^[A-Z]*$"),
        SURNAME("^[A-Z]*|([A-Z]+[-][A-Z]+)$"),
        FIRM("^[+&A-Z0-9@.\\-\\(\\) ]*$"),
        POSITION("^[A-Z0-9 \\-&]*$");

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

        @Override
        public String toString() {
            return getRegEx();

        }
    }

}
