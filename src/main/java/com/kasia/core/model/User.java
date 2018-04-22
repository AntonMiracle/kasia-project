package com.kasia.core.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;

public class User implements Serializable {
    private Long id;
    private Details details;
    private String login;
    private String password;
    private Instant createOn;
    private Locale locale;
    private ZoneId zoneId;

    public User() {

    }

    public User(User user) {
        setId(user.getId());
        setDetails(new Details(user.getDetails()));
        setLogin(user.getLogin());
        setPassword(user.getPassword());
        setCreateOn(user.getCreateOn());
        setLocale(user.getLocale());
        setZoneId(user.getZoneId());
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Details getDetails() {
        return details;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreateOn(Instant createOn) {
        this.createOn = createOn;
    }

    public Instant getCreateOn() {
        return createOn;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (details != null ? !details.equals(user.details) : user.details != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (createOn != null ? !createOn.equals(user.createOn) : user.createOn != null) return false;
        if (locale != null ? !locale.equals(user.locale) : user.locale != null) return false;
        return zoneId != null ? zoneId.equals(user.zoneId) : user.zoneId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", details=" + details +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createOn=" + createOn +
                ", locale=" + locale +
                ", zoneId=" + zoneId +
                '}';
    }

    /**

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


     public boolean comparePassword(String password) {
     throwIAE(password == null, "Password is NULL");
     String cryptPassword = crypt(password);
     return getPassword().equals(cryptPassword);
     }

     */
}
