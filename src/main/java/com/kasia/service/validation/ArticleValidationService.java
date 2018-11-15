package com.kasia.service.validation;

import com.kasia.model.Article;
import com.kasia.service.validation.constraint.ArticleConstraint;
import com.kasia.service.validation.field.AField;
import com.kasia.service.validation.message.AMessageLink;
import com.kasia.service.validation.regex.ARegex;

import javax.validation.ConstraintValidator;

public interface ArticleValidationService extends ValidationService<Article, AField, AMessageLink, ARegex>, ConstraintValidator<ArticleConstraint, Article> {
}
