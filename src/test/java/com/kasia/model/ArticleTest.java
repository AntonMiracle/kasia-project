package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ArticleTest {

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Article.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}