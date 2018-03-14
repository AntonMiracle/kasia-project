package com.kasia.core.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.regex.Pattern;

import static com.kasia.core.model.Util.throwIAE;

public class User {
    private MessageDigest md5;
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

    protected User() {

    }

    protected void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    protected void setNickname(String nickname) {
        throwIAE(nickname == null, "Nickname is NULL");
        nickname = nickname.trim();
        throwIAE(!NICKNAME.matches(nickname), errorMsgWithPatterns(NICKNAME, nickname));
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    protected String errorMsgWithPatterns(Patterns patterns, String text) {
        throwIAE(patterns == null || text == null, "Pattern or Text is NULL");
        return "PATTERN: " + patterns.toString()
                + " LENGTH: [" + patterns.MIN_LENGTH + "," + patterns.MAX_LENGTH + "]"
                + "%nCURRENT: " + text;
    }

    public void setLogin(String login) {
        throwIAE(login == null, "login is NULL");
        login = login.trim();
        throwIAE(!LOGIN.matches(login), errorMsgWithPatterns(LOGIN, login));
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        throwIAE(password == null, "Password is NULL");
        password = password.trim();
        throwIAE(!PASSWORD.matches(password), errorMsgWithPatterns(PASSWORD, password));
        this.password = crypt(password);
    }

    public String getPassword() {
        return password;
    }

    private void getMD5() {
        try {
            if (md5 == null) md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Error when create MD5");
        }
    }

    private String crypt(String password) {
        getMD5();
        md5.update(password.getBytes(), 0, password.length());
        return new BigInteger(1, md5.digest()).toString(PASSWORD.MAX_LENGTH);
    }

    public void setMail(String mail) {
        throwIAE(mail == null, "Mail is NULL");
        mail = mail.trim();
        throwIAE(!MAIL.matches(mail), errorMsgWithPatterns(MAIL, mail));
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setCreateOn(Instant createOn) {
        throwIAE(createOn == null, "CreateOn is null");
        this.createOn = createOn;
    }

    public Instant getCreateOn() {
        return createOn;
    }

    public enum Patterns {
        NICKNAME("^[a-zA-Z0-9]+|([a-zA-Z0-9]+[_][a-zA-Z0-9]+)$", 5, 16),
        LOGIN("^[a-zA-Z0-9]+|([a-zA-Z0-9]+[_][a-zA-Z0-9]+)$", 6, 16),
        PASSWORD("^[a-zA-Z0-9\\-_@\\!\\?.]*$", 6, 16),
        MAIL("^[^@]+@[^.@]+\\.[^.@]+$", 6, 40);

        private final int MAX_LENGTH;
        private final int MIN_LENGTH;
        private Pattern pattern;

        Patterns(String regEx, int min, int max) {
            MAX_LENGTH = max;
            MIN_LENGTH = min;
            this.pattern = Pattern.compile(regEx);
        }

        public boolean matches(String text) {
            if (text.length() > MAX_LENGTH) return false;
            if (text.length() < MIN_LENGTH) return false;
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
//    private Long id;
//    private Details details;
//    private LocalDateTime createOn;
//    private Set<Role> roles;
//    private Security security;
//
//    public User() {
//    }
//
//    public User(Long id, Details details, LocalDateTime createOn, Set<Role> roles, Security security) {
//        this.id = id;
////        this.details = new Details(details);
//        this.createOn = createOn;
//        for (Role role : roles) addRole(role);
//        this.security = new Security(security);
//    }
//
//    public User(User user) {
//        this(user.getId(), user.getDetails(), user.getCreateOn(), user.getRoles(), user.getSecurity());
//    }
//
//    public void setCreateOn(LocalDateTime createOn) {
//        this.createOn = createOn;
//    }
//
//    public LocalDateTime getCreateOn() {
//        return this.createOn;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setSecurity(Security security) {
//        this.security = security;
//    }
//
//    public Security getSecurity() {
//        return this.security;
//    }
//
//    public void setDetails(Details details) {
////        this.details = new Details(details);
//    }
//
//    public Details getDetails() {
//        return this.details;
//    }
//
//    public Set<Role> getRoles() {
//        initRoles();
//        return this.roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        initRoles();
//        for (Role role : roles) this.roles.add(role);
//    }
//
//    public boolean addRole(Role role) {
//        initRoles();
//        return roles.add(new Role(role));
//    }
//
//    public boolean isHas(Role role) {
//        return roles != null ? roles.contains(role) : false;
//    }
//
//    public boolean removeRole(Role role) {
//        return roles != null ? roles.remove(role) : false;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        User user = (User) o;
//
//        if (id != null ? !id.equals(user.id) : user.id != null) return false;
//        if (details != null ? !details.equals(user.details) : user.details != null) return false;
//        if (createOn != null ? !createOn.equals(user.createOn) : user.createOn != null) return false;
//        if(roles !=null && user.getRoles() != null){
//            for(Role role : user.getRoles()) if(!isHas(role)) return false;
//        }else{
//            return false;
//        }
//        return security != null ? security.equals(user.security) : user.security == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id != null ? id.hashCode() : 0;
//        result = 31 * result + (details != null ? details.hashCode() : 0);
//        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
//        result = 31 * result + (roles != null ? roles.hashCode() : 0);
//        result = 31 * result + (security != null ? security.hashCode() : 0);
//        return result;
//    }
//
//    private void initRoles() {
//        if (roles == null) {
//            roles = new HashSet<>();
//        }
//    }
}
