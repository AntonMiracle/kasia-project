package com.kasia.core.model;

public class Uinfo {
    private Long id;
    private String nickName;
    private String secondName;
    private String eMail;
    private String name;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public String getEmail() {
        return this.eMail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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

        Uinfo uinfo = (Uinfo) o;

        if (nickName != null ? !nickName.equals(uinfo.nickName) : uinfo.nickName != null) return false;
        if (secondName != null ? !secondName.equals(uinfo.secondName) : uinfo.secondName != null) return false;
        if (eMail != null ? !eMail.equals(uinfo.eMail) : uinfo.eMail != null) return false;
        if (name != null ? !name.equals(uinfo.name) : uinfo.name != null) return false;
        return id != null ? id.equals(uinfo.id) : uinfo.id == null;
    }

    @Override
    public int hashCode() {
        int result = nickName != null ? nickName.hashCode() : 0;
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
