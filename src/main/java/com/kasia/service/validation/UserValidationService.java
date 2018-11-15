package com.kasia.service.validation;

import com.kasia.model.User;
import com.kasia.service.validation.constraint.UserConstraint;
import com.kasia.service.validation.field.UField;
import com.kasia.service.validation.message.UMessageLink;
import com.kasia.service.validation.regex.URegex;

import javax.validation.ConstraintValidator;

public interface UserValidationService extends ValidationService<User, UField, UMessageLink, URegex>, ConstraintValidator<UserConstraint, User> {
}
