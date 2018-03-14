package com.kasia.core.model;

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
        throwIAE(patterns == null || text == null, "Pattern or Text is NULL");
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
        NAME("^[A-Z]*$", 32),
        SURNAME("^[A-Z]*|([A-Z]+[-][A-Z]+)$", 32),
        FIRM("^[+&A-Z0-9@.\\-\\(\\) ]*$", 32),
        POSITION("^[A-Z0-9 \\-&]*$", 32);

        private final int MAX_LENGTH;
        private Pattern pattern;

        Patterns(String regEx, int max) {
            MAX_LENGTH = max;
            this.pattern = Pattern.compile(regEx);
        }

        public boolean matches(String text) {
            if (text.length() > MAX_LENGTH) return false;
            return getPattern().matcher(text).matches();
        }

        public Pattern getPattern() {
            return pattern;
        }

        @Override
        public String toString() {
            return getPattern().pattern();
        }
    }
}
