package com.kasia.validation;

import com.kasia.model.Model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {
    public static final String REGEX_USER_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String REGEX_USER_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";
    public static final String REGEX_USER_NICK = "^[A-Za-z0-9+_.-]+$";
    public static final String REGEX_ARTICLE_NAME = "^[A-Za-z0-9+_.-]{2,}$";
    public static final String REGEX_EMPLOYER_NAME = REGEX_ARTICLE_NAME;
    public static final String REGEX_BUDGET_NAME = REGEX_ARTICLE_NAME;

    public boolean isValid(Model model) {
        if (model == null) return true;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(model);
        return violations.size() == 0;
    }

    public enum FieldRegex{
        USER_EMAIL(REGEX_USER_EMAIL)
        , USER_PASSWORD(REGEX_USER_PASSWORD)
        , USER_NICK(REGEX_USER_NICK)
        , ARTICLE_NAME(REGEX_ARTICLE_NAME)
        , BUDGET_NAME(REGEX_BUDGET_NAME)
        , EMPLOYER_NAME(REGEX_EMPLOYER_NAME)
        ;

        private final String regex;

        FieldRegex(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }

        public Pattern pattern() {
            return Pattern.compile(regex);
        }

        public Matcher matcher(String text) {
            return pattern().matcher(text);
        }

        public boolean isMatch(String text) {
            return matcher(text).matches();
        }
    }

    public enum Currency {
        EUR(java.util.Currency.getInstance("EUR"))
        , USD(java.util.Currency.getInstance("USD"))
        , PLN(java.util.Currency.getInstance("PLN"))
        , RUB(java.util.Currency.getInstance("RUB"));
        private final java.util.Currency currency;

        Currency(java.util.Currency currency) {
            this.currency = currency;
        }
        public java.util.Currency get(){
            return currency;
        }
    }
}
