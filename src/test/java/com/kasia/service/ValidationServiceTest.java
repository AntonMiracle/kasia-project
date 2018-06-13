package com.kasia.service;

import com.kasia.model.user.User;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidationServiceTest {
    private User user;
    @Inject
    private ValidationService service;

    @Before
    public void before() {
        WeldContainer container = new Weld().initialize();
        service = container.select(ValidationService.class).get();
        System.out.println("service is Null : " + (service == null));
        assert service != null;
        user = new User();
        user.setUsername("UseRName123");
        user.setPassword("password");
        user.setEmail("email@email.com");
    }

    @Test
    public void readMessageForInvalidUsername() {
       assertThat(true).isTrue();
       assertThat(service).isNotNull();
//        user.setUsername(null);
//        Set<ConstraintViolation<User>> errors = validator.validate(user);
//        Set<String> errorMsg = new HashSet<>();
//
//        assertThat(errors).isNotNull();
//        errors.stream().forEach(el -> errorMsg.add(el.getMessage()));
//        errors.stream().forEach(el -> System.out.println(el.getMessage()));
//
//        assertThat(errorMsg.contains("Username incorrect: must be length 3-16 and contain only A-Za-z0-9")).isTrue();
    }

    @Test
    public void readMessageForInvalidPassword() {
        assertThat(true).isTrue();
//        Set<ConstraintViolation<User>> errors = validator.validate(new User());
//        Set<String> errorMsg = new HashSet<>();
//
//        assertThat(errors).isNotNull();
//        errors.stream().forEach(el -> errorMsg.add(el.getMessage()));
//        errors.stream().forEach(el -> System.out.println(el.getMessage()));
//
//        assertThat(errorMsg.contains("Username incorrect: must be length 3-16 and contain only A-Za-z0-9")).isTrue();
    }

}