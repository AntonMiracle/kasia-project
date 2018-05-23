package com.kasia.core.model;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ResultTest {
    private Result<ResultObjectForTest> result;

    @Before
    public void before() {
        result = new Result<>(new ResultObjectForTest());
        result = new Result<>();
        assert result instanceof Serializable;
    }

    @Test
    public void setAndGetFailed() {
        result.setFailed(true);
        assertThat(result.isFailed()).isTrue();
    }

    @Test
    public void setAndGetResult() {
        ResultObjectForTest obj = new ResultObjectForTest();
        obj.setName("Test");
        result.setResult(obj);
        assertThat(result.getResult()).isEqualTo(obj);
    }

}

class ResultObjectForTest {
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

        ResultObjectForTest that = (ResultObjectForTest) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}