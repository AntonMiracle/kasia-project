package com.kasia.core.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kasia.core.model.Util.throwIAE;

public class Role {
    private String name;
    private Patterns NAME = Patterns.NAME;
    private long id;

    protected Role() {

    }

    public Role(String name) {
        setName(name);
    }

    public void setName(String name) {
        throwIAE(name == null, "Name ain`t NULL");
        name = name.toUpperCase().trim();
        throwIAE(!NAME.matches(name), "PATTERN: " + NAME.getRegEx() + "\nCURRENT " + name);
        this.name = name;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;
        return name != null ? name.equals(role.name) : role.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public enum Patterns {
        NAME("^([A-Z0-9]+\\-[A-Z0-9]+)|([A-Z0-9]+)$", 4, 16);
        private Pattern pattern;
        private final int MIN_LENGTH;
        private final int MAX_LENGTH;

        Patterns(String pattern, int min, int max) {
            MIN_LENGTH = min;
            MAX_LENGTH = max;
            this.pattern = Pattern.compile(pattern);
        }

        public Matcher getMatcher(String text) {
            return getPattern().matcher(text);
        }

        public boolean matches(String text) {
            if (text.length() < MIN_LENGTH) return false;
            if (text.length() > MAX_LENGTH) return false;
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
