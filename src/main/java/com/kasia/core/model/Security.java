package com.kasia.core.model;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    private Long id;
    private String login;
    private String password;
    private MessageDigest md5;
    private boolean isCrypt;

    protected Security() {

    }

    public Security(String login, String password) {
        setLogin(login);
        setPassword(password);
        crypt();
    }

    protected Security(Long id, String login, String password) {
        setId(id);
        setLogin(login);
        setPassword(password);
    }

    public Security(Security security) {
        this(security.getId(), security.getLogin(), security.getPassword());
    }

    protected void setCrypt(boolean crypt) {
        isCrypt = crypt;
    }

    public boolean isCrypt() {
        return this.isCrypt;
    }

    protected String getPassword() {
        return this.password;
    }

    protected void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Security ulap = (Security) o;

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
