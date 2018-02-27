package com.kasia.core.model;

public class Details {
    private Long id;
    private String nickName;
    private String secondName;
    private String eMail;
    private String name;

    public Details() {
    }

    public Details(Details details){
        this(details.getId(),details.getNickName(),details.getSecondName(),details.getEmail(),details.getName());
    }

    public Details(Long id, String nickName, String secondName, String eMail, String name) {
        this.id = id;
        this.nickName = nickName;
        this.secondName = secondName;
        this.eMail = eMail;
        this.name = name;
    }

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

        Details details = (Details) o;

        if (nickName != null ? !nickName.equals(details.nickName) : details.nickName != null) return false;
        if (secondName != null ? !secondName.equals(details.secondName) : details.secondName != null) return false;
        if (eMail != null ? !eMail.equals(details.eMail) : details.eMail != null) return false;
        if (name != null ? !name.equals(details.name) : details.name != null) return false;
        return id != null ? id.equals(details.id) : details.id == null;
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
