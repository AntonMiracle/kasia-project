package com.kasia.core.service;

import com.kasia.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ExceptionServiceTest {
    @Autowired
    private ExceptionService exception;

    @Before
    public void before() {
        assert exception != null;
    }

    @Test(expected = NullPointerException.class)
    public void whenNPEWithNullThenNPE() {
        exception.NPE(null);
    }
}