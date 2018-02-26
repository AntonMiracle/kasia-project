package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UinfoTest {
    private Uinfo uInfo;

    @Before
    public void before() {
        uInfo = new Uinfo();
    }

    @Test
    public void setAndGetNickName() {
        String nickName = "nickName";
        uInfo.setNickName(nickName);
        Assert.assertEquals("set and get nickName", nickName, uInfo.getNickName());
    }

    @Test
    public void setAndGetSecondName() {
        String secondName = "secondName";
        uInfo.setSecondName(secondName);
        Assert.assertEquals("set and get secondName", secondName, uInfo.getSecondName());
    }

    @Test
    public void setAndGetName() {
        String name = "name";
        uInfo.setName(name);
        Assert.assertEquals("set and get name", name, uInfo.getName());
    }

    @Test
    public void setAndGetEMail() {
        String eMail = "email";
        uInfo.setEmail(eMail);
        Assert.assertEquals("set and get eMail", eMail, uInfo.getEmail());
    }

    @Test
    public void setAndGetId() {
        Long id = 100L;
        uInfo.setId(id);
        Assert.assertEquals("set and get id", id, uInfo.getId());
    }

    @Test
    public void checkEqualsAndHashCode() {
        Uinfo uInfo1 = createUinfo(100L, "name", "secondName", "nickName", "mail");
        Uinfo uInfo2 = createUinfo(100L, "name", "secondName", "nickName", "mail");
        Assert.assertEquals("uInfo1.equals(uInfo2)",uInfo1, uInfo2);
        Assert.assertEquals("uInfo1.equals(uInfo2)",uInfo1.hashCode(), uInfo2.hashCode());
        uInfo2.setName("testName");
        Assert.assertNotEquals("uInfo1.equals(uInfo2)",uInfo1, uInfo2);
        Assert.assertNotEquals("uInfo1.equals(uInfo2)",uInfo1.hashCode(), uInfo2.hashCode());
    }

    private Uinfo createUinfo(Long id, String name, String seconName, String nickName, String eMail) {
        Uinfo uInfo = new Uinfo();
        uInfo.setId(id);
        uInfo.setName(name);
        uInfo.setSecondName(seconName);
        uInfo.setNickName(nickName);
        uInfo.setEmail(eMail);
        return uInfo;
    }
}