package com.kasia.model.result;

import com.kasia.model.Model;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

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

    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new Result<TestObject>()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = new Result<TestObject>().getClass().getDeclaredFields().length;

        TestObject testObject = new TestObject();
        boolean exist = false;
        boolean valid = false;
        int expectedSumClassFields = 3;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Result<>(testObject, exist, valid)).isNotNull();
    }

    // ================================================
    @Test
    public void notExtendsModel() {
        assertThat(Model.class.isAssignableFrom(result.getClass())).isFalse();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(result.getClass())).isTrue();
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

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(result.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(result.toString().contains("{")).isTrue();
        assertThat(result.toString().contains(result.getClass().getSimpleName())).isTrue();
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