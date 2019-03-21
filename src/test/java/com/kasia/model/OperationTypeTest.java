package com.kasia.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationTypeTest {
    @Test
    public void test() {
        assertThat(OperationType.CONSUMPTION.name()).isEqualTo("CONSUMPTION");
        assertThat(OperationType.INCOME.name()).isEqualTo("INCOME");
    }
}