package com.kasia;

import com.kasia.repository.*;
import com.kasia.service.imp.ArticleServiceImp;
import com.kasia.service.imp.BudgetServiceImp;
import com.kasia.service.imp.UserServiceImp;
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
        //repositories
        , ArticleRepository.class
        , BudgetRepository.class
        , UserRepository.class
        , EmployerRepository.class
        , OperationRepository.class
        //configuration JPA with persist unit name - test
        , TestPersistenceFactory.class})
public class ConfigurationEjbCdiContainerForIT {
    @Test
    public void mockTestMethod() {
    }
}
