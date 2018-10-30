package com.kasia.validation.article;

import com.kasia.model.Article;
import com.kasia.validation.ConstraintViolationManager;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ArticleValidation implements ConstraintValidator<ArticleConstraint, Article>, ConstraintViolationManager {

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
        if (article.getAmount() == null || article.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            msg.append("{validation.article.ArticleConstraint.message.amount}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if(article.getCreateOn() == null){
            msg.append("{validation.article.ArticleConstraint.message.date}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
