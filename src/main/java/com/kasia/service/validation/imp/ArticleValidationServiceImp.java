package com.kasia.service.validation.imp;

import com.kasia.model.Article;
import com.kasia.service.validation.ValidationService;
import com.kasia.service.validation.constraint.ArticleConstraint;
import com.kasia.service.validation.field.AField;
import com.kasia.service.validation.message.AMessageLink;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArticleValidationServiceImp implements ValidationService<Article, AField, AMessageLink>, ConstraintValidator<ArticleConstraint, Article> {

    @Override
    public boolean isValid(Article article, ConstraintValidatorContext constraintValidatorContext) {
        if (article == null) return true;
        int errors = 0;
        if (article.getId() < 0) {
            addConstraintViolation(AField.ID, AMessageLink.ID_NEGATIVE, constraintValidatorContext);
           errors++;
        }
        if (article.getType() == null) {
            addConstraintViolation(AField.TYPE, AMessageLink.TYPE_NULL, constraintValidatorContext);
            errors++;
        }
        if (article.getName() == null || article.getName().length() <= 0) {
            addConstraintViolation(AField.NAME,AMessageLink.NAME_NULL, constraintValidatorContext);
            errors++;
        }
        return errors == 0;
    }

    @Override
    public boolean isValueValid(AField field, Object value) {
        return false;
    }

    @Override
    public String getErrorMsgByValue(AField field, Object value) {
        return null;
    }
}
