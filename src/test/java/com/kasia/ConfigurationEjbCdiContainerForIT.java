package com.kasia;

import com.kasia.repository.*;
import com.kasia.service.imp.*;
import com.oneandone.ejbcdiunit.EjbUnitRunner;
import com.oneandone.ejbcdiunit.persistence.TestPersistenceFactory;
import org.jglue.cdiunit.AdditionalClasses;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EjbUnitRunner.class)
@AdditionalClasses({
        //Services
        ArticleServiceImp.class
        , BudgetServiceImp.class
        , UserServiceImp.class
        , OperationServiceImp.class
        , EmployerServiceImp.class
        , ValidationServiceImp.class
        //repositories
        , ArticleRepository.class
        , BudgetRepository.class
        , UserRepository.class
        , EmployerRepository.class
        , OperationRepository.class
        //configuration JPA with persist unit name = test
        , TestPersistenceFactory.class})
public class ConfigurationEjbCdiContainerForIT {
    @Test
    public void mockTestMethod() {
    }
}
