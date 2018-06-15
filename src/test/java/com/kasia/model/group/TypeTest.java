package com.kasia.model.group;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TypeTest {
    @Test
    public void roleTypeExist() {
        assertThat(Type.valueOf("SYSTEM")).isEqualTo(Type.SYSTEM);
    }

    @Test
    public void articleTypeExist() {
        assertThat(Type.valueOf("ARTICLE")).isEqualTo(Type.ARTICLE);
    }

    @Test
    public void budgetTypeExist() {
        assertThat(Type.valueOf("BUDGET")).isEqualTo(Type.BUDGET);
    }

    @Test
    public void itemTypeExist() {
        assertThat(Type.valueOf("ITEM")).isEqualTo(Type.ITEM);
    }

    @Test
    public void friendUserTypeExist() {
        assertThat(Type.valueOf("USER")).isEqualTo(Type.USER);
    }

}