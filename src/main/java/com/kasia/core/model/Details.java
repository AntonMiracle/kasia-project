package com.kasia.core.model;

public class Details {
    private Long id;
    private String nickName;
    private String secondName;
    private String email;
    private String name;

    public Details() {
    }

    public Details(Details details) {
        this(details.getId(), details.getNickName(), details.getSecondName(), details.getEmail(), details.getName());
    }

    public Details(Long id, String nickName, String secondName, String email, String name) {
        setId(id);
        setNickName(nickName);
        setSecondName(secondName);
        setEmail(email);
        setName(name);
    }

    public void setNickName(String nickName) {
        Util.ifNullThrowNPEWithMsg(nickName, "nickName cant be null");
        this.nickName = nickName.trim();
    }

    public String getNickName() {
        return Util.ifNullReturnEmptyString(this.nickName);
    }

    public void setSecondName(String secondName) {
        Util.ifNullThrowNPEWithMsg(secondName, "secondName cant be null");
        this.secondName = secondName.trim();
    }

    public String getSecondName() {
        return Util.ifNullReturnEmptyString(this.secondName);
    }

    public void setEmail(String email) {
        Util.ifNullThrowNPEWithMsg(email, "email cant be null");
        this.email = email.trim();
    }

    public String getEmail() {
        return Util.ifNullReturnEmptyString(this.email);
    }

    public void setName(String name) {
        Util.ifNullThrowNPEWithMsg(name, "name cant be null");
        this.name = name.trim();
    }

    public String getName() {
        return Util.ifNullReturnEmptyString(this.name);
    }

    protected void setId(Long id) {
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
        if (email != null ? !email.equals(details.email) : details.email != null) return false;
        if (name != null ? !name.equals(details.name) : details.name != null) return false;
        return id != null ? id.equals(details.id) : details.id == null;
    }

    @Override
    public int hashCode() {
        int result = nickName != null ? nickName.hashCode() : 0;
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return nickName + " " + name + " " + secondName + " " + email;
    }
}
