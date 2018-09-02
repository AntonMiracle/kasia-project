package com.kasia.validation.article;

import com.kasia.model.Article;
import com.kasia.validation.ValidationHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ArticleValidation implements ConstraintValidator<ArticleConstraint, Article> {
    private ValidationHelper helper = new ValidationHelper();

    @Override
    public void initialize(ArticleConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Article article, ConstraintValidatorContext constraintValidatorContext) {
        if (article == null) return true;
        StringBuilder msg = new StringBuilder();
        if (article.getType() == null) {
            msg.append("{validation.article.ArticleConstraint.message.type.null}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (article.getAmount() == null || article.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            msg.append("{validation.article.ArticleConstraint.message.amount.negative}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
