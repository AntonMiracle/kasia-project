package com.kasia.repository;

import com.kasia.repository.imp.*;
import com.kasia.service.imp.UserServiceImp;
import com.oneandone.ejbcdiunit.EjbUnitRunner;
import com.oneandone.ejbcdiunit.persistence.TestPersistenceFactory;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.AdditionalPackages;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(EjbUnitRunner.class)
@AdditionalPackages({
        //To insert all repositories implementation
        UserRepositoryImp.class
        //To insert all services implementation
        , UserServiceImp.class
})
@AdditionalClasses({
        //configuration JPA with persist unit name = test
        TestPersistenceFactory.class
})
public class RepositoryIT {
    @Inject
    private BudgetElementProviderRepository budgetElementProviderRepository;
    @Inject
    private BudgetElementRepository budgetElementRepository;
    @Inject
    private BudgetOperationRepository budgetOperationRepository;
    @Inject
    private BudgetRepository budgetRepository;
    @Inject
    private ElementRepository elementRepository;
    @Inject
    private ElementProviderRepository elementProviderRepository;
    @Inject
    private OperationRepository operationRepository;
    @Inject
    private UserConnectBudgetRepository userConnectBudgetRepository;
    @Inject
    private UserBudgetRepository userBudgetRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private EntityManager entityManager;

    @Test
    public void repositoriesInjectSuccess() {
        assertThat(budgetElementProviderRepository).isNotNull();
        assertThat(budgetElementRepository).isNotNull();
        assertThat(budgetOperationRepository).isNotNull();
        assertThat(budgetRepository).isNotNull();
        assertThat(elementProviderRepository).isNotNull();
        assertThat(elementRepository).isNotNull();
        assertThat(operationRepository).isNotNull();
        assertThat(userConnectBudgetRepository).isNotNull();
        assertThat(userBudgetRepository).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void entityManagerInjectSuccess() {
        assertThat(entityManager).isNotNull();
        assertThat(budgetElementProviderRepository).isNotNull();
        assertThat(((BudgetElementProviderRepositoryImp) budgetElementProviderRepository).getEm()).isNotNull();
        assertThat(budgetElementRepository).isNotNull();
        assertThat(((BudgetElementRepositoryImp) budgetElementRepository).getEm()).isNotNull();
        assertThat(budgetOperationRepository).isNotNull();
        assertThat(((BudgetOperationRepositoryImp) budgetOperationRepository).getEm()).isNotNull();
        assertThat(budgetRepository).isNotNull();
        assertThat(((BudgetRepositoryImp) budgetRepository).getEm()).isNotNull();
        assertThat(elementProviderRepository).isNotNull();
        assertThat(((ElementProviderRepositoryImp) elementProviderRepository).getEm()).isNotNull();
        assertThat(elementRepository).isNotNull();
        assertThat(((ElementRepositoryImp) elementRepository).getEm()).isNotNull();
        assertThat(operationRepository).isNotNull();
        assertThat(((OperationRepositoryImp) operationRepository).getEm()).isNotNull();
        assertThat(userConnectBudgetRepository).isNotNull();
        assertThat(((UserConnectBudgetRepositoryImp) userConnectBudgetRepository).getEm()).isNotNull();
        assertThat(userBudgetRepository).isNotNull();
        assertThat(((UserBudgetRepositoryImp) userBudgetRepository).getEm()).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(((UserRepositoryImp) userRepository).getEm()).isNotNull();
    }
}