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
    public void articleConsumptionTypeExist() {
        assertThat(Type.valueOf("ARTICLE_CATEGORY")).isEqualTo(Type.ARTICLE_CATEGORY);
    }

    @Test
    public void budgetTypeExist() {
        assertThat(Type.valueOf("BUDGET")).isEqualTo(Type.BUDGET);
    }

    @Test
    public void budgetAccessTypeExist() {
        assertThat(Type.valueOf("BUDGET_ACCESS")).isEqualTo(Type.BUDGET_ACCESS);
    }

    @Test
    public void itemTypeExist() {
        assertThat(Type.valueOf("ITEM")).isEqualTo(Type.ITEM);
    }

    @Test
    public void friendUserTypeExist() {
        assertThat(Type.valueOf("USER")).isEqualTo(Type.USER);
    }


    @Test
    public void synchronizedUserTypeExist() {
        assertThat(Type.valueOf("SYNCHRONIZED_USER")).isEqualTo(Type.SYNCHRONIZED_USER);
    }

}