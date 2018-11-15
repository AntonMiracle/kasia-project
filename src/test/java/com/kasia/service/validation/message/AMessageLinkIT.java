package com.kasia.service.validation.message;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import com.kasia.service.validation.ValidationService;
import com.kasia.service.validation.field.AField;
import com.kasia.service.validation.field.ModelField;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AMessageLinkIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private ValidationService<Article, AField, AMessageLink> validationService;

    @Test
    public void allLinkReturnMessage() {
        Article article = new Article();
        article.setId(-10L);
        Map<ModelField, String> errors = validationService.getMessages(article, AField.values());

        for (AField field : AField.values()) {
            System.out.println(errors.get(field));
            assertThat(errors.get(field)).isNotNull();
            assertThat(errors.get(field).trim().startsWith("{")).isFalse();
            assertThat(errors.get(field).trim().length() > 0).isTrue();
        }
    }

}