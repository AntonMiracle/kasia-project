package com.kasia.repository;

import com.kasia.service.RepositoryService;
import com.kasia.service.imp.RepositoryServiceImp;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class RepositoryTestHelper {
    private static final String PERSISTENCE_TEST_UNIT_NAME = "test-db-unit";
    protected static RepositoryService repositoryService;

    @BeforeClass
    public static void beforeClass() {
        repositoryService = new RepositoryServiceImp(PERSISTENCE_TEST_UNIT_NAME);
    }

    @AfterClass
    public static void afterClass() {
        if (repositoryService != null) repositoryService.closeFactory();
    }

}
