package com.kasia.core.service;

import com.kasia.config.AppConfig;
import com.kasia.core.model.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ResultServiceTest {
    @Autowired
    private ResultService<ForTest> result;
    private ForTest objForTest;

    @Before
    public void before() {
        assert result != null;
    }

    @Test
    public void whenFailedResultIsFailedTrue() {
        Result res = result.calculationFailed();
        assertThat(res.isCalculationFailed()).isTrue();
    }

    @Test
    public void whenSuccessResultIsFailedFalse() {
        Result res = result.calculationSuccess(new ForTest());
        assertThat(res.isCalculationFailed()).isFalse();
    }

    @Test
    public void whenFailedResultObjectNull() {
        Result res = result.calculationFailed();
        assertThat(res.isCalculationFailed()).isTrue();
        assertThat(res.getResult()).isNull();
    }

    @Test
    public void whenSuccessWithObjectResultObjectIsEquals() {
        objForTest = new ForTest();
        objForTest.setName("TestName");
        Result res = result.calculationSuccess(objForTest);
        assertThat(res.isCalculationFailed()).isFalse();
        assertThat(res.getResult()).isEqualTo(objForTest);
    }
}

class ForTest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForTest forTest = (ForTest) o;

        return name != null ? name.equals(forTest.name) : forTest.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}