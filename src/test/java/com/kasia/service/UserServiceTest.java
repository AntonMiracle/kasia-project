package com.kasia.service;

import com.kasia.model.result.Result;
import com.kasia.model.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserService service;
    private User user;

    private String email;

    @Before
    public void before() {
        email = "email@email.com";
        user = new User();
        user.setEmail(email);

        Result<User> expectedResultUser = new Result<>();
        expectedResultUser.setResult(user);
        expectedResultUser.setValid(true);
        expectedResultUser.setExist(true);

        Result<Boolean> expectedResultBoolean = new Result<>();
        expectedResultBoolean.setValid(true);
        expectedResultBoolean.setExist(true);
        expectedResultBoolean.setResult(Boolean.TRUE);

        service = mock(UserService.class);
        when(service.addNewUser(user)).thenReturn(expectedResultUser);
        when(service.getUserWithEmail(email)).thenReturn(expectedResultUser);
        when(service.removeUserWithEmail(email)).thenReturn(expectedResultBoolean);
        when(service.updateUser(user)).thenReturn(expectedResultUser);
    }

    @Test
    public void addNewUser() {
        Result<User> actual = service.addNewUser(user);
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult()).isEqualTo(user);
    }

    @Test
    public void getUserWithEmail() {
        Result<User> actual = service.getUserWithEmail(email);
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult().getEmail()).isEqualTo(email);
    }

    @Test
    public void updateUser() {
        Result<User> actual = service.updateUser(user);
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult()).isEqualTo(user);
        assertThat(actual.getResult().getEmail()).isEqualTo(email);
    }

    @Test
    public void removeUserWithEmail() {
        Result<Boolean> actual = service.removeUserWithEmail(email);
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult()).isEqualTo(Boolean.TRUE);
    }

}