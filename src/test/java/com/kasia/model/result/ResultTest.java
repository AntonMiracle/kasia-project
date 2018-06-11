package com.kasia.model.result;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ResultTest {
    private TestObject testObject;
    private Result<TestObject> result;

    @Before
    public void before() {
        result = new Result<>();
        testObject = new TestObject();
        testObject.setName("TetName");
    }

    @Test
    public void setAndGetResult() {
        result.setResult(testObject);
        assertThat(result.getResult()).isEqualTo(testObject);
    }

    @Test
    public void SetAndIsExist() {
        result.setExist(true);
        assertThat(result.isExist()).isTrue();
    }

    @Test
    public void setAndIsValid() {
        result.setValid(true);
        assertThat(result.isValid()).isTrue();
    }

}

class TestObject {
    private String name;

    protected TestObject() {

    }

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

        TestObject that = (TestObject) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}