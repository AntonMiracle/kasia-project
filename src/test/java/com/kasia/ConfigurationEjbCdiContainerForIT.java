package com.kasia;

import com.kasia.model.User;
import com.kasia.repository.imp.*;
import com.kasia.service.model.imp.*;
import com.kasia.service.validation.ValidationService;
import com.oneandone.ejbcdiunit.EjbUnitRunner;
import com.oneandone.ejbcdiunit.persistence.TestPersistenceFactory;
import org.jglue.cdiunit.AdditionalClasses;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@RunWith(EjbUnitRunner.class)
@AdditionalClasses({
        //Services model
        ArticleServiceImp.class
        , BudgetServiceImp.class
        , UserServiceImp.class
        , OperationServiceImp.class
        , EmployerServiceImp.class
        //validation
        , ValidationService.class
        //repositories
        , ArticleRepositoryImp.class
        , BudgetRepositoryImp.class
        , UserRepositoryImp.class
        , EmployerRepositoryImp.class
        , OperationRepositoryImp.class
        //configuration JPA with persist unit name = test
        , TestPersistenceFactory.class})
public class ConfigurationEjbCdiContainerForIT {

    public User createUser(String email, String nick) {
        String password = "Password2";
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);

        User user = new User(email, password, nick, roles
                , new HashSet<>(), new HashSet<>(), new HashSet<>()
                , LocalDateTime.now().withNano(0), ZoneId.systemDefault());
        return user;
    }

    @Test
    public void mockTestMethod() {
    }
}
