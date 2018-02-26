package com.kasia.core.model;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Ulap {
    private Long id;
    private String login;
    private String password;
    private MessageDigest md5;
    private boolean isCrypt;

    public Ulap() {

    }

    public Ulap(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean isCrypt() {
        return this.isCrypt;
    }

    public String getPassword() {
        return this.password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordEquals(String password) {
        if (isCrypt) {
            return this.password != null ? this.password.equals(getMD5Hash(password)) : false;
        }
        return this.password != null ? this.password.equals(password) : false;
    }

    public boolean crypt() {
        if (!isCrypt && password != null) {
            this.password = getMD5Hash(this.password);
            isCrypt = true;
        }
        return isCrypt;
    }

    private void getMD5() {
        if (md5 == null) {
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new RuntimeException("Try to create MD5 algorythm");
            }
        }
    }

    private String getMD5Hash(String text) {
        getMD5();
        md5.update(text.getBytes(), 0, text.length());
        return new BigInteger(1, md5.digest()).toString(16);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ulap ulap = (Ulap) o;

        if (isCrypt != ulap.isCrypt) return false;
        if (login != null ? !login.equals(ulap.login) : ulap.login != null) return false;
        if (password != null ? !password.equals(ulap.password) : ulap.password != null) return false;
        return id != null ? id.equals(ulap.id) : ulap.id == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (isCrypt ? 1 : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
