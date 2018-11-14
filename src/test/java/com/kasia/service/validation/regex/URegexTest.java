package com.kasia.service.validation.regex;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class URegexTest {
    @Test
    public void getREGEX() throws Exception {
        assertThat(URegex.values().length == 3).isTrue();

        assertThat(URegex.EMAIL.getREGEX()).isEqualTo("^[A-Za-z0-9+_.-]+@(.+)$");
        assertThat(URegex.NICK.getREGEX()).isEqualTo("^[A-Za-z0-9+_.-]{3,}$");
        assertThat(URegex.PASSWORD.getREGEX()).isEqualTo("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");
    }

}