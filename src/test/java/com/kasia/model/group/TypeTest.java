package com.kasia.model.group;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TypeTest {
    @Test
    public void userTypeExist() {
        assertThat(Type.valueOf("USER")).isEqualTo(Type.USER);
    }

    @Test
    public void budgetTypeExist() {
        assertThat(Type.valueOf("BUDGET")).isEqualTo(Type.BUDGET);
    }

    @Test
    public void articleTypeExist() {
        assertThat(Type.valueOf("ARTICLE")).isEqualTo(Type.ARTICLE);
    }

    @Test
    public void itemTypeExist() {
        assertThat(Type.valueOf("ITEM")).isEqualTo(Type.ITEM);
    }

    @Test
    public void consumptionTypeExist() {
        assertThat(Type.valueOf("ARTICLE_CONSUMPTION")).isEqualTo(Type.ARTICLE_CONSUMPTION);
    }
    @Test
    public void incomeTypeExist() {
        assertThat(Type.valueOf("ARTICLE_INCOME")).isEqualTo(Type.ARTICLE_INCOME);
    }

}