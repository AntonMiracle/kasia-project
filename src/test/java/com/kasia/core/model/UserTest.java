package com.kasia.core.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
    }

    @Test
    public void setAndGetId() {
        user.setId(10);
        assertThat(user.getId()).isEqualTo(10);
    }

    //NICKNAME
    @Test
    public void setNicknameWithAlphabetsOnly() {
        user.setNickname("NickName");
        assertThat(user.getNickname()).isEqualTo("NickName");
    }

    @Test
    public void setNicknameWithAlphabetsAndDownDashInMiddle() {
        user.setNickname("Nick_Name");
        assertThat(user.getNickname()).isEqualTo("Nick_Name");
    }

    @Test
    public void setNicknameWithAlphabetsDownDashInMiddleAndNumbers() {
        user.setNickname("1Nick_Name2");
        assertThat(user.getNickname()).isEqualTo("1Nick_Name2");
    }

    @Test
    public void setNickNameIgnoreExtraWhiteSymbolsInStartAndEnd() {
        user.setNickname("   1Nick_Name2    ");
        assertThat(user.getNickname()).isEqualTo("1Nick_Name2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNickNameWithDownDashInTheEndThenIAE() {
        user.setNickname("1Nick_Name2_");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNickNameWithDownDashInTheStartThenIAE() {
        user.setNickname("_1Nick_Name2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNickNameWithNotOnlyWithAZ09_ThenIAE() {
        user.setNickname("-1Nick_Name2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNickNameGreaterMaxLengthThenIAE() {
        user.setNickname("NameNameNameNameD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNickNameLowerMinLengthThenIAE() {
        user.setNickname("Name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNickNameNullThenIAE() {
        user.setNickname(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNickNameIsCompositeThenIAE() {
        user.setNickname("Nick name");
    }

    @Test
    public void whenNickNameNullReturnNull() {
        assertThat(user.getNickname()).isNull();
    }

//LOGIN

    @Test
    public void setLoginWithAlphabetsOnly() {
        user.setLogin("login2");
        assertThat(user.getLogin()).isEqualTo("login2");
    }

    @Test
    public void setLoginWithAlphabetsAndDownDashInMiddle() {
        user.setLogin("Lo_gin");
        assertThat(user.getLogin()).isEqualTo("Lo_gin");
    }

    @Test
    public void setloginWithAlphabetsDownDashInMiddleAndNumbers() {
        user.setLogin("1Lo_gin2");
        assertThat(user.getLogin()).isEqualTo("1Lo_gin2");
    }

    @Test
    public void setLoginIgnoreExtraWhiteSymbolsInStartAndEnd() {
        user.setLogin("   1Lo_gin2    ");
        assertThat(user.getLogin()).isEqualTo("1Lo_gin2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoginWithDownDashInTheEndThenIAE() {
        user.setLogin("1Lo_gin2_");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoginWithDownDashInTheStartThenIAE() {
        user.setLogin("_1Lo_gin2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoginWithNotOnlyWithAZ09_ThenIAE() {
        user.setLogin("-_1Lo_gin2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoginGreaterMaxLengthThenIAE() {
        user.setLogin("LoginLoginLoginDD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoginLowerMinLengthThenIAE() {
        user.setLogin("Logi");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoginNullThenIAE() {
        user.setLogin(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoginIsCompositeThenIAE() {
        user.setLogin("Lo gin");
    }

    @Test
    public void whenLoginNullReturnNull() {
        assertThat(user.getLogin()).isNull();
    }
//PASSWORD

    @Test
    public void setPasswordWithAlphabetsOnly() {
        user.setPassword("_aA09-@!?.");
        assertThat(user.getPassword()).isNotEqualTo("_aA09-@!?.");
    }

    @Test
    public void setPasswordIgnoreExtraWhiteSymbolsInStartAndEnd() {
        user.setPassword("Password");
        String crypt = user.getPassword();
        user = new User();
        user.setPassword("   Password    ");
        assertThat(user.getPassword()).isEqualTo(crypt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPasswordWithWrongSymbolsThenIAE() {
        user.setPassword("+;Password:");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPasswordGreaterMaxLengthThenIAE() {
        user.setPassword("PassPassPassPassP");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPasswordLowerMinLengthThenIAE() {
        user.setPassword("Passw");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPasswordNullThenIAE() {
        user.setPassword(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPasswordIsCompositeThenIAE() {
        user.setPassword("Pass word");
    }

    @Test
    public void whenPasswordNullReturnNull() {
        assertThat(user.getPassword()).isNull();
    }

    @Test
    public void setPasswordCryptPassword() {
        String origin = "_aA09-@!?.";
        user.setPassword(origin);
        String crypt = user.getPassword();
        user = new User();
        user.setPassword(origin);
        assertThat(crypt).isEqualTo(user.getPassword());
    }

    //MAIL

    @Test
    public void setAndGetMail() {
        user.setMail("ma@il.com");
        assertThat(user.getMail()).isEqualTo("ma@il.com");
    }

    @Test
    public void setMailIgnoreExtraWhiteSymbolsInStartAndEnd() {
        user.setMail("   ma@il.com    ");
        assertThat(user.getMail()).isEqualTo("ma@il.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMailWithoutSpecialSymbolThenIAE() {
        user.setMail("mail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMailWithPointAfterSpecialSymbolThenIAE() {
        user.setMail("ma@.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMailWithoutPointAfterSpecialSymbolThenIAE() {
        user.setMail("ma@ilcom");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMailWithSpecialSymbolInStartThenIAE() {
        user.setMail("@il.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMailWithTwoSpecialSymbolInStartThenIAE() {
        user.setMail("ma@i@l.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMailGreaterMaxLengthThenIAE() {
        user.setMail("ma@il.commAAAAABBBBBAAAAABBBBBAAAAABBBBBd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLMailLowerMinLengthThenIAE() {
        user.setMail("m@l.m");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMailNullThenIAE() {
        user.setMail(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMailIsCompositeThenIAE() {
        user.setMail("Lo gin");
    }

    @Test
    public void whenMailNullReturnNull() {
        assertThat(user.getMail()).isNull();
    }

    //CRETE ON
    @Test
    public void setAndGetCreateOn() {
        Instant instant = Instant.now();
        user.setCreateOn(instant);
        assertThat(user.getCreateOn()).isEqualTo(instant);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetCreateOnWithNullTheIAE() {
        user.setCreateOn(null);
    }

    @Test
    public void whenCreateOnIsNullThenReturnNull() {
        assertThat(user.getCreateOn()).isNull();
    }

    @Test
    public void setCreateOnByTimestamp() {
        Instant instant = Instant.now();
        Timestamp ts = Timestamp.from(instant);
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        System.out.println(instant);
        System.out.println(ts);
        System.out.println(ts.toInstant());
        System.out.println(ldt);
    }
}