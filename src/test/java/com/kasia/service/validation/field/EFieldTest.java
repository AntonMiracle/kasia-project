package com.kasia.service.validation.field;

import com.kasia.model.Employer;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EFieldTest extends ModelFieldTestHelper{
    @Test
    public void isValuesEqualsOfFieldsQuantityUser() {
        assertThat(Employer.class.getDeclaredFields().length == EField.values().length).isTrue();
    }

    @Test
    public void isNamesChecked() {
        Set<String> clazz = Stream.of(Employer.class.getDeclaredFields()).map(field -> field.getName()).collect(Collectors.toCollection(HashSet<String>::new));
        List<String> fields = Stream.of(EField.values()).map(field -> field.getName()).collect(Collectors.toList());

        int checked = countSameElement(fields,clazz);
        assertThat(checked == clazz.size()).isTrue();
    }

    @Test
    public void getAll() {
        assertThat(EField.ID.values().length == EField.values().length).isTrue();
    }

}