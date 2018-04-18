package com.kasia.core.model;

public class User {
    /**
    private User.Patterns NICKNAME = Patterns.NICKNAME;
    private User.Patterns LOGIN = Patterns.LOGIN;
    private User.Patterns PASSWORD = Patterns.PASSWORD;
    private User.Patterns MAIL = Patterns.MAIL;
    private long id;
    private String nickname;
    private String login;
    private String password;
    private String mail;
    private Instant createOn;
    private ZoneId zoneId;
    private Locale locale;
    private Details details;

    protected User() {
    }

    public User(String login, String password, String nickname, String mail, Locale locale, ZoneId zoneId) {
        setLogin(login);
        setPassword(password);
        setNickname(nickname);
        setMail(mail);
        setLocale(locale);
        setZoneId(zoneId);
    }

    protected void setId(long id) {
        this.id = id;
    }

    protected void setNickname(String nickname) {
        throwIAE(nickname == null, "Nickname is NULL");
        nickname = nickname.trim();
        throwIAE(!NICKNAME.matches(nickname), errorMsgWithPatterns(NICKNAME, nickname));
        this.nickname = nickname;
    }

    protected void setLogin(String login) {
        throwIAE(login == null, "login is NULL");
        login = login.trim();
        throwIAE(!LOGIN.matches(login), errorMsgWithPatterns(LOGIN, login));
        this.login = login;
    }

    public void setPassword(String password) {
        throwIAE(password == null, "Password is NULL");
        password = password.trim();
        throwIAE(!PASSWORD.matches(password), errorMsgWithPatterns(PASSWORD, password));
        this.password = crypt(password);
    }

    public void setMail(String mail) {
        throwIAE(mail == null, "Mail is NULL");
        mail = mail.trim();
        throwIAE(!MAIL.matches(mail), errorMsgWithPatterns(MAIL, mail));
        this.mail = mail;
    }

    protected void setCreateOn(Instant createOn) {
        throwIAE(createOn == null, "CreateOn is null");
        this.createOn = createOn;
    }

    public void setZoneId(ZoneId zoneId) {
        throwIAE(zoneId == null, "ZnoId is NULL");
        this.zoneId = zoneId;
    }

    public void setLocale(Locale locale) {
        throwIAE(locale == null, "Locale is NULL");
        this.locale = locale;
    }

    public void setDetails(Details details) {
        throwIAE(details == null, "Details is NULL");
        this.details = new Details(details);
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public Instant getCreateOn() {
        return createOn;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public Locale getLocale() {
        return locale;
    }

    public Details getDetails() {
        return details != null ? details : new Details();
    }

    protected MessageDigest getMessageDigester(String algorithmName) {
        throwIAE(algorithmName == null, "Algorithm name is NULL");
        try {
            return MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Unknown algorithm name : " + algorithmName
                    + "%nExceptionStackTrace : " + e);
        }
    }

    private String crypt(String password) {
        MessageDigest md5 = getMessageDigester("MD5");
        md5.update(password.getBytes(), 0, password.length());
        return new BigInteger(1, md5.digest()).toString(PASSWORD.MAX_LENGTH);
    }

    protected String errorMsgWithPatterns(Patterns patterns, String text) {
        throwIAE(patterns == null || text == null, "Patterns or Text is NULL");
        return "PATTERN: " + patterns.toString()
                + " LENGTH: [" + patterns.MIN_LENGTH + "," + patterns.MAX_LENGTH + "]"
                + "%nCURRENT: " + text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (mail != null ? !mail.equals(user.mail) : user.mail != null) return false;
        if (createOn != null ? !createOn.equals(user.createOn) : user.createOn != null) return false;
        if (zoneId != null ? !zoneId.equals(user.zoneId) : user.zoneId != null) return false;
        if (locale != null ? !locale.equals(user.locale) : user.locale != null) return false;
        return details != null ? details.equals(user.details) : user.details == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }

    public boolean comparePassword(String password) {
        throwIAE(password == null, "Password is NULL");
        String cryptPassword = crypt(password);
        return getPassword().equals(cryptPassword);
    }

    public enum Patterns {
        NICKNAME("^[a-zA-Z0-9]+|([a-zA-Z0-9]+[_][a-zA-Z0-9]+)$", 5, 16),
        LOGIN("^[a-zA-Z0-9]+|([a-zA-Z0-9]+[_][a-zA-Z0-9]+)$", 6, 16),
        PASSWORD("^[a-zA-Z0-9\\-_@\\!\\?.]*$", 6, 16),
        MAIL("^[^@]+@[^.@]+\\.[^.@]+$", 6, 40);

        private final int MAX_LENGTH;
        private final int MIN_LENGTH;
        private Patterns pattern;

        Patterns(String regEx, int min, int max) {
            MAX_LENGTH = max;
            MIN_LENGTH = min;
            this.pattern = Patterns.compile(regEx);
        }

        public boolean matches(String text) {
            if (text.length() > MAX_LENGTH) return false;
            if (text.length() < MIN_LENGTH) return false;
            return getPattern().matcher(text).matches();
        }

        public Patterns getPattern() {
            return pattern;
        }

        @Override
        public String toString() {
            return getPattern().pattern();
        }
    }
        */
}
