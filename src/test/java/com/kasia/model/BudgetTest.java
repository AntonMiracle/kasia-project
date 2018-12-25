package com.kasia.model;

import org.junit.Test;

import java.math.BigDecimal;

public class BudgetTest {
    @Test
    public void test() {
        BigDecimal num1 = BigDecimal.ZERO;
        num1 = num1.subtract(BigDecimal.ONE);
        System.out.println(num1);
        System.out.println(num1.add(BigDecimal.TEN));
    }
}