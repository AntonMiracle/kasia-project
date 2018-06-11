package com.kasia.model.result;

import com.kasia.service.ResultService;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResultServiceTest {
    private ResultService<TestObject> service;
    private TestObject testObject;
    private String name;

    private Result<TestObject> expectedSuccess;
    private Result<TestObject> expectedSuccessEmpty;
    private Result<TestObject> expectedInvalid;

    @Before
    public void before() {
        name = "name";
        testObject = new TestObject();
        testObject.setName(name);

        expectedSuccess = new Result<>();
        expectedSuccess.setResult(testObject);
        expectedSuccess.setValid(true);
        expectedSuccess.setExist(true);

        expectedSuccessEmpty = new Result<>();
        expectedSuccessEmpty.setValid(true);
        expectedSuccessEmpty.setExist(false);

        expectedInvalid = new Result<>();

        service = mock(ResultService.class);
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
