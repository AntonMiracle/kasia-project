package com.kasia;

import com.kasia.repository.imp.*;
import com.kasia.service.model.imp.*;
import com.kasia.service.validation.imp.*;
import com.oneandone.ejbcdiunit.EjbUnitRunner;
import com.oneandone.ejbcdiunit.persistence.TestPersistenceFactory;
import org.jglue.cdiunit.AdditionalClasses;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EjbUnitRunner.class)
@AdditionalClasses({
        //Services model
        ArticleServiceImp.class
        , BudgetServiceImp.class
        , UserServiceImp.class
        , OperationServiceImp.class
        , EmployerServiceImp.class
        // Services validate
        , UserValidationServiceImp.class
        , ArticleValidationServiceImp.class
        , BudgetValidationServiceImp.class
        , EmployerValidationServiceImp.class
        , OperationValidationServiceImp.class
        //repositories
        , ArticleRepositoryImp.class
        , BudgetRepositoryImp.class
        , UserRepositoryImp.class
        , EmployerRepositoryImp.class
        , OperationRepositoryImp.class
        //configuration JPA with persist unit name = test
        , TestPersistenceFactory.class})
public class ConfigurationEjbCdiContainerForIT {
    @Test
    public void mockTestMethod() {
    }
}
