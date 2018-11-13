package com.kasia.service.validation.imp;

import com.kasia.model.Article;
import com.kasia.service.validation.ArticleValidationService;

import javax.validation.ConstraintValidatorContext;

public class ArticleValidationServiceImp implements ArticleValidationService {

    @Override
    public boolean isValid(Article article, ConstraintValidatorContext constraintValidatorContext) {
        if (article == null) return true;
        StringBuilder msg = new StringBuilder();
        if (article.getId() < 0) {
            msg.append("{validation.message.id}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (article.getType() == null) {
            msg.append("{validation.article.ArticleConstraint.message.type}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (article.getName() == null || article.getName().trim().length() <= 0) {
            msg.append("{validation.article.ArticleConstraint.message.name}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
