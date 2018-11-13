package com.kasia.service.validation;

import com.kasia.model.Article;
import com.kasia.service.validation.constraint.ArticleConstraint;

import javax.validation.ConstraintValidator;

public interface ArticleValidationService extends ConstraintValidator<ArticleConstraint, Article>, ValidationService<Article> {
}
