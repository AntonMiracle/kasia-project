package com.kasia.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleTest {
    @Test
    public void test() {
        Set<String> types = new HashSet<>();
        types.add("USER");
        types.add("ADMIN");

        assertThat(types.size()).isEqualTo(Role.values().length);
        for (Role role : Role.values()) {
            assertThat(types.contains(role.toString())).isTrue();
        }
    }
}