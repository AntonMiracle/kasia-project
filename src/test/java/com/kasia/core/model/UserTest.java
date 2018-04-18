package com.kasia.core.model;

public class UserTest {
    /**
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
    public void whenSetNickNameNullThenIAE() {
        user.setNickname(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNickNameIsCompositeThenIAE() {
        user.setNickname("Nick name");
    }

    @Test
    public void whenNickNameNullGetterReturnNull() {
        assertThat(user.getNickname()).isNull();
    }

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
    public void setLoginWithAlphabetsDownDashInMiddleAndNumbers() {
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
    public void whenSetLoginNullThenIAE() {
        user.setLogin(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoginIsCompositeThenIAE() {
        user.setLogin("Lo gin");
    }

    @Test
    public void whenLoginNullGetterReturnNull() {
        assertThat(user.getLogin()).isNull();
    }

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
    public void whenSetPasswordNullThenIAE() {
        user.setPassword(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPasswordIsCompositeThenIAE() {
        user.setPassword("Pass word");
    }

    @Test
    public void whenPasswordNullGetterThenReturnNull() {
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
    public void whenSetMailWithoutSpecialSymbolThenIAE() {
        user.setMail("mail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetMailWithPointAfterSpecialSymbolThenIAE() {
        user.setMail("ma@.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetMailWithoutPointAfterSpecialSymbolThenIAE() {
        user.setMail("ma@ilcom");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetMailWithSpecialSymbolInStartThenIAE() {
        user.setMail("@il.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetMailWithTwoSpecialSymbolInStartThenIAE() {
        user.setMail("ma@i@l.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetMailGreaterMaxLengthThenIAE() {
        user.setMail("ma@il.commAAAAABBBBBAAAAABBBBBAAAAABBBBBd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetMailLowerMinLengthThenIAE() {
        user.setMail("m@l.m");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetMailNullThenIAE() {
        user.setMail(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetMailIsCompositeThenIAE() {
        user.setMail("Lo gin");
    }

    @Test
    public void whenMailNullGetterReturnNull() {
        assertThat(user.getMail()).isNull();
    }

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
    public void whenCreateOnNullGetterReturnNull() {
        assertThat(user.getCreateOn()).isNull();
    }

    @Test
    public void setAndGetZoneId() {
        ZoneId zoneId = ZoneId.systemDefault();
        user.setZoneId(zoneId);
        assertThat(user.getZoneId()).isEqualTo(zoneId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetZoneIdWithNullTheIAE() {
        user.setZoneId(null);
    }

    @Test
    public void whenZoneIdNullGetterReturnNull() {
        assertThat(user.getZoneId()).isNull();
    }

    @Test
    public void setAndGetLocale() {
        Locale locale = Locale.US;
        user.setLocale(locale);
        assertThat(user.getLocale()).isEqualTo(locale);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetLocaleWithNullTheIAE() {
        user.setLocale(null);
    }

    @Test
    public void whenLocaleNullGetterReturnNull() {
        assertThat(user.getLocale()).isNull();
    }

    @Test
    public void setAndGetDetails() {
        Details details = new Details("NAME", "SURNAME", "FIRM NAME", "POSITION");
        user.setDetails(details);
        assertThat(user.getDetails()).isEqualTo(details);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetDetailsWithNullTheIAE() {
        user.setDetails(null);
    }

    @Test
    public void whenDetailsNullGetterReturnEmptyDetails() {
        assertThat(user.getDetails()).isEqualTo(new Details());
    }

    @Test
    public void checkEqualsAndHashCode() throws NoSuchAlgorithmException {
        EqualsVerifier.forClass(User.class)
                .withIgnoredFields("NICKNAME", "LOGIN", "PASSWORD", "MAIL")
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test(expected = NullPointerException.class)
    public void whenUserPatternsValueOfNullThenNPE() {
        User.Patterns.valueOf(null);
    }

    @Test
    public void getUserPatternsFromValueOf() {
        User.Patterns patterns = User.Patterns.valueOf("NICKNAME");
        assertThat(patterns).isSameAs(User.Patterns.NICKNAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenErrorMsgWithNullPatterns() {
        user.errorMsgWithPatterns(null, "text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenErrorMsgWithNullText() {
        user.errorMsgWithPatterns(User.Patterns.NICKNAME, null);
    }

    @Test
    public void getMessageDigesterOfMD5() {
        assertThat(user.getMessageDigester("MD5")).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetMessageDigesterOfNullThenIAE() {
        user.getMessageDigester(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetMessageDigesterWithUnknownThenIAE() {
        user.getMessageDigester("SuperMd5");
    }

    @Test
    public void whenChangeDetailsWichSetDoNotChangeUserDetails() {
        Details details = new Details("NAME", "SURNAME", "FIRM NAME", "POSITION");
        user.setDetails(details);
        details.setName("SUPERNAME");
        assertThat(user.getDetails()).isNotEqualTo(details);
    }

    @Test
    public void createUserWithLoginPasswordNickName() {
        String nickname = "NICKNAME";
        String password = "PASSWORD";
        String login = "LOGIN6";
        String mail = "mail@mail.com";
        ZoneId zoneId = ZoneId.of("Europe/London");
        Locale locale = Locale.UK;
        User user = new User(login, password, nickname, mail, locale, zoneId);
        assertThat(user.getLogin()).isEqualTo(login);
        assertThat(user.getPassword()).isNotEqualTo(password);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getMail()).isEqualTo(mail);
        assertThat(user.getLocale()).isEqualTo(locale);
        assertThat(user.getZoneId()).isEqualTo(zoneId);
    }

    @Test
    public void compareUserPasswordWithAnotherPassword() {
        String password = "PASSWORD";
        user.setPassword(password);
        assertThat(user.comparePassword(password)).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCompareUserPasswordWithNullThenIAE() {
        user.comparePassword(null);
    }
    */
}