package com.kasia;

import com.kasia.repository.ArticleRepository;
import com.kasia.repository.BudgetRepository;
import com.kasia.repository.EconomyRepository;
import com.kasia.repository.UserRepository;
import com.kasia.service.imp.ArticleServiceImp;
import com.kasia.service.imp.BudgetServiceImp;
import com.kasia.service.imp.EconomyServiceImp;
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
        , EconomyServiceImp.class
        //repositories
        , ArticleRepository.class
        , BudgetRepository.class
        , EconomyRepository.class
        , UserRepository.class
        //configuration JPA with persist unit name - test
        , TestPersistenceFactory.class})
public class ConfigurationEjbCdiContainerForIT {
    @Test
    public void mockTestMethod() {
    }
}
