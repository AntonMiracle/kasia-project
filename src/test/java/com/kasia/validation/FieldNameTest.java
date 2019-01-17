package com.kasia.validation;

import com.kasia.model.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldNameTest {

    @Test
    public void userFieldNameCorrect() {
        Set<String> currentFieldsName = Arrays.stream(User.class.getDeclaredFields())
                .map(field -> field.getName())
                .collect(Collectors.toCollection(HashSet<String>::new));
        String name = FieldName.USER_NAME.getName();
        String email = FieldName.USER_EMAIL.getName();
        String password = FieldName.USER_PASSWORD.getName();

        assertThat(currentFieldsName.contains(name)).isTrue();
        assertThat(currentFieldsName.contains(password)).isTrue();
        assertThat(currentFieldsName.contains(email)).isTrue();
    }

}