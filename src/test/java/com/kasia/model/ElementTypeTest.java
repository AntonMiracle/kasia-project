package com.kasia.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementTypeTest {
    @Test
    public void test() {
        Set<String> types = new HashSet<>();
        types.add("INCOME");
        types.add("CONSUMPTION");

        assertThat(types.size()).isEqualTo(ElementType.values().length);
        for (ElementType type : ElementType.values()) {
            assertThat(types.contains(type.toString())).isTrue();
        }
    }
}