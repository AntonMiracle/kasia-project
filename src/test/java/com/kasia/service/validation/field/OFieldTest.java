package com.kasia.service.validation.field;

import com.kasia.model.Operation;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OFieldTest extends ModelFieldTestHelper{
    @Test
    public void isValuesEqualsOfFieldsQuantityUser() {
        assertThat(Operation.class.getDeclaredFields().length == OField.values().length).isTrue();
    }

    @Test
    public void isNamesChecked() {
        Set<String> clazz = Stream.of(Operation.class.getDeclaredFields()).map(field -> field.getName()).collect(Collectors.toCollection(HashSet<String>::new));
        List<String> fields = Stream.of(OField.values()).map(field -> field.getName()).collect(Collectors.toList());

        int checked = countSameElement(fields,clazz);
        assertThat(checked == clazz.size()).isTrue();
    }

    @Test
    public void getAll() {
        assertThat(OField.ID.values().length == OField.values().length).isTrue();
    }
}