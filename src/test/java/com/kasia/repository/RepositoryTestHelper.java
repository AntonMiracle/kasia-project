package com.kasia.repository;

import com.kasia.service.RepositoryConnectionService;
import com.kasia.service.imp.RepositoryConnectionServiceImp;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class RepositoryTestHelper {
    private static final String PERSISTENCE_TEST_UNIT_NAME = "test-db-unit";
    protected static RepositoryConnectionService repositoryConnectionService;

    @BeforeClass
    public static void beforeClass() {
        repositoryConnectionService = new RepositoryConnectionServiceImp(PERSISTENCE_TEST_UNIT_NAME);
    }

    @AfterClass
    public static void afterClass() {
        if (repositoryConnectionService != null) repositoryConnectionService.closeEntityManagerFactory();
    }

}
