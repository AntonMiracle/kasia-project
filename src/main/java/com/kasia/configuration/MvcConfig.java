package com.kasia.configuration;

import com.kasia.model.Role;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserService userS;

    @PostConstruct
    public void init() {
        User user1 = new User("anton@gmail.com", "Password2", Role.USER, LocalDateTime.now(), true, ZoneId.systemDefault(), Locale.getDefault());
        User user2 = new User("anton2@gmail.com", "Password2", Role.USER, LocalDateTime.now(), true, ZoneId.systemDefault(), Locale.getDefault());
        userS.save(user1);
        userS.save(user2);
    }

    @Bean/* config i18n to get error msg from messages.properties*/
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean/* config i18n to get error msg from messages.properties*/
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean/*Dialect of Thymleaf to enable - xmlns:sec="http://www.w3.org/1999/xhtml"*/
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    /*==================================
    Session models
     ==================================*/
//    @Bean
//    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public Budget sessionBudget() {
//        return new Budget();
//    }
//
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public User sessionUser() {
        return new User();
    }
//
//    @Bean
//    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public OperationDTO sessionOperationDTO() {
//        return new OperationDTO();
//    }
//
//    @Bean
//    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public WeekOperationHistory sessionWeekOperationHistory() {
//        return new WeekOperationHistory();
//    }

}
