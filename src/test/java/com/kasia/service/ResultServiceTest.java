package com.kasia.service;

import com.kasia.model.result.Result;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResultServiceTest {
    private ResultService<TestObject> service;
    private TestObject testObject;

    @Before
    public void before() {
        testObject = new TestObject();

        Result<TestObject> expectedSuccess = new Result<>();
        expectedSuccess.setResult(testObject);
        expectedSuccess.setValid(true);
        expectedSuccess.setExist(true);

        Result<TestObject> expectedSuccessEmpty = new Result<>();
        expectedSuccessEmpty.setValid(true);
        expectedSuccessEmpty.setExist(false);

        Result<TestObject> expectedInvalid = new Result<>();

        service = (ResultService<TestObject>) mock(ResultService.class);
        when(service.success(testObject)).thenReturn(expectedSuccess);
        when(service.success()).thenReturn(expectedSuccessEmpty);
        when(service.failed()).thenReturn(expectedInvalid);
    }

    @Test
    public void successWithResultValue() {
        Result<TestObject> actual = service.success(testObject);
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.getResult().getName()).isEqualTo(testObject.getName());
    }

    @Test
    public void successWithEmptyValue() {
        Result<TestObject> actual = service.success();
        assertThat(actual.isExist()).isFalse();
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.getResult()).isNull();
    }

    @Test
    public void failed() {
        Result<TestObject> actual = service.failed();
        assertThat(actual.isExist()).isFalse();
        assertThat(actual.isValid()).isFalse();
        assertThat(actual.getResult()).isNull();
    }
}

class TestObject {
    private String name;

    TestObject() {
    }

    String getName() {
        return name;
    }

    void setName(String name) {
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
