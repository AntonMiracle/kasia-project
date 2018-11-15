package com.kasia.service.validation.field;

import com.kasia.model.Article;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AFieldTest extends ModelFieldTestHelper{
    @Test
    public void isValuesEqualsOfFieldsQuantityUser() {
        assertThat(Article.class.getDeclaredFields().length == AField.values().length).isTrue();
    }

    @Test
    public void isNamesChecked() {
        Set<String> clazz = Stream.of(Article.class.getDeclaredFields()).map(field -> field.getName()).collect(Collectors.toCollection(HashSet<String>::new));
        List<String> fields = Stream.of(AField.values()).map(field -> field.getName()).collect(Collectors.toList());

        int checked = countSameElement(fields,clazz);
        assertThat(checked == clazz.size()).isTrue();
    }

    @Test
    public void getAll() {
        assertThat(AField.ID.values().length == AField.values().length).isTrue();
    }


}