package com.kasia.service.validation.imp;

import com.kasia.exception.FieldNotExistRuntimeException;
import com.kasia.model.Article;
import com.kasia.service.validation.ArticleValidationService;
import com.kasia.service.validation.field.AField;
import com.kasia.service.validation.message.AMessageLink;
import com.kasia.service.validation.regex.ARegex;

import javax.validation.ConstraintValidatorContext;

public class ArticleValidationServiceImp implements ArticleValidationService{

    @Override
    public boolean isValid(Article article, ConstraintValidatorContext constraintValidatorContext) {
        if (article == null) return true;
        int errors = 0;
        if (article.getId() < 0) {
            addConstraintViolation(AField.ID, AMessageLink.ID_NEGATIVE, constraintValidatorContext);
            errors++;
        }
        if (!isTypeValid(article.getType(), constraintValidatorContext)) {
            errors++;
        }
        if (!isDescriptionValid(article.getDescription(), constraintValidatorContext)) {
            errors++;
        }
        if (!isNameValid(article.getName(), constraintValidatorContext)) {
            errors++;
        }
        return errors == 0;
    }

    private boolean isTypeValid(Article.Type type, ConstraintValidatorContext constraintValidatorContext) {
        if (type == null) {
            addConstraintViolation(AField.TYPE, AMessageLink.TYPE_NULL, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isDescriptionValid(String description, ConstraintValidatorContext constraintValidatorContext) {
        if (description == null) {
            addConstraintViolation(AField.DESCRIPTION, AMessageLink.DESCRIPTION_NULL, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isNameValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) {
            addConstraintViolation(AField.NAME, AMessageLink.NAME_NULL, constraintValidatorContext);
            return false;
        }
        if (!isMatch(name, ARegex.NAME)) {
            addConstraintViolation(AField.NAME, AMessageLink.NAME_REGEX_ERROR, constraintValidatorContext);
            return false;
        }
        return true;
    }

    @Override
    public Article createModelWithFieldAndValue(AField field, Object value) throws FieldNotExistRuntimeException {
        if (value == null || field == null) throw new NullPointerException("Field or value is null");
        Article article = new Article();
        switch (field) {
            case ID:
                article.setId((Long) value);
                break;
            case NAME:
                article.setName((String) value);
                break;
            case DESCRIPTION:
                article.setDescription((String) value);
                break;
            case TYPE:
                article.setType((Article.Type) value);
                break;
            default:
                throw new FieldNotExistRuntimeException();
        }
        return article;
    }
}
