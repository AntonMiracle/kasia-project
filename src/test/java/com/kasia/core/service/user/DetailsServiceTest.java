package com.kasia.core.service.user;

import com.kasia.core.model.Details;
import com.kasia.core.service.user.impl.DetailsServiceImpl;
import org.junit.Before;

public class DetailsServiceTest {
    private DetailsService service;
    private Details details;

    @Before
    public void before() {
        service = new DetailsServiceImpl();
        details = new Details();
    }

}