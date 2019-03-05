package com.kasia.model.validation;

import com.kasia.controller.dto.UserDTO;
import com.kasia.model.Budget;
import com.kasia.model.Provider;
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

    @Test
    public void budgetFieldNameCorrect() {
        Set<String> currentFieldsName = Arrays.stream(Budget.class.getDeclaredFields())
                .map(field -> field.getName())
                .collect(Collectors.toCollection(HashSet<String>::new));
        String name = FieldName.BUDGET_NAME.getName();

        assertThat(currentFieldsName.contains(name)).isTrue();
    }

    @Test
    public void elementProviderFieldNameCorrect() {
        Set<String> currentFieldsName = Arrays.stream(Provider.class.getDeclaredFields())
                .map(field -> field.getName())
                .collect(Collectors.toCollection(HashSet<String>::new));
        String name = FieldName.ELEMENT_PROVIDER_NAME.getName();

        assertThat(currentFieldsName.contains(name)).isTrue();
    }

    @Test
    public void elementFieldNameCorrect() {
        Set<String> currentFieldsName = Arrays.stream(Provider.class.getDeclaredFields())
                .map(field -> field.getName())
                .collect(Collectors.toCollection(HashSet<String>::new));
        String name = FieldName.ELEMENT_NAME.getName();

        assertThat(currentFieldsName.contains(name)).isTrue();
    }

    @Test
    public void userDTOFieldNameCorrect() {
        Set<String> expectedFieldsName = Arrays.stream(UserDTO.class.getDeclaredFields())
                .map(field -> field.getName())
                .collect(Collectors.toCollection(HashSet<String>::new));
        Set<String> currentFieldName = new HashSet<>();
        currentFieldName.add(FieldName.USER_DTO_EMAIL.getName());
        currentFieldName.add(FieldName.USER_DTO_NAME.getName());
        currentFieldName.add(FieldName.USER_DTO_PASSWORD.getName());
        currentFieldName.add(FieldName.USER_DTO_CONFIRM.getName());
        currentFieldName.add(FieldName.USER_DTO_ZONEID.getName());
        currentFieldName.add(FieldName.USER_DTO_LANG.getName());
        currentFieldName.add(FieldName.USER_DTO_COUNTRY.getName());

        assertThat(currentFieldName.size()).isEqualTo(expectedFieldsName.size());
        for (String name : currentFieldName) {
            if (expectedFieldsName.contains(name)) {
                expectedFieldsName.remove(name);
            }
        }
    }

}