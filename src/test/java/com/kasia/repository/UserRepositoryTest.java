package com.kasia.repository;

import com.kasia.util.TestHelper;
import com.kasia.model.result.Result;
import com.kasia.model.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRepositoryTest implements TestHelper<User> {
    private UserRepository repository;

    private User user;
    private Set<User> users;
    private long id;
    private String username;

    private Result<User> expectedUser;
    private Result<Set<User>> expectedAll;
    private Result<Boolean> expecteTrue;


    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        username = "username";
        id = 2L;

        user = new User();
        user.setUsername(username);
        setProtectedId(user, Long.valueOf(id));

        users = new HashSet<>();
        users.add(user);

        expectedUser = new Result<>();
        expectedUser.setValid(true);
        expectedUser.setExist(true);
        expectedUser.setResult(user);

        expectedAll = new Result<>();
        expectedAll.setValid(true);
        expectedAll.setExist(true);
        expectedAll.setResult(users);

        expecteTrue = new Result<>();
        expecteTrue.setValid(true);
        expecteTrue.setExist(true);
        expecteTrue.setResult(Boolean.TRUE);

        repository = mock(UserRepository.class);
        when(repository.add(user)).thenReturn(expectedUser);
        when(repository.getAll()).thenReturn(expectedAll);
        when(repository.getById(id)).thenReturn(expectedUser);
        when(repository.removeById(id)).thenReturn(expecteTrue);
        when(repository.update(user)).thenReturn(expectedUser);
    }

    @Test
    public void add() {
        Result<User> actual = repository.add(user);
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult()).isNotNull();
        assertThat(actual.getResult().getUsername()).isEqualTo(username);
    }

    @Test
    public void getAll() {
        Result<Set<User>> actual = repository.getAll();
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult()).isNotNull();
        assertThat(actual.getResult().size()).isEqualTo(1);
        assertThat(actual.getResult().contains(user)).isTrue();
    }

    @Test
    public void getById() {
        Result<User> actual = repository.getById(id);
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult()).isNotNull();
    }

    @Test
    public void removeById() {
        Result<Boolean> actual = repository.removeById(id);
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult()).isNotNull();
        assertThat(actual.getResult()).isTrue();
    }

    @Test
    public void update() {
        Result<User> actual = repository.update(user);
        assertThat(actual.isValid()).isTrue();
        assertThat(actual.isExist()).isTrue();
        assertThat(actual.getResult()).isNotNull();
    }
}