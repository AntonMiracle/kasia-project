package com.kasia.configuration;

import com.kasia.model.*;
import com.kasia.model.service.BudgetService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserService uService;
    @Autowired
    private BudgetService bService;

    @PostConstruct
    public void init() {
        User user = uService.createUser("anton@gmail.com", "Anton", "Password2",
                "America/Atka", "pl", "PL");
        uService.saveUser(user);
        Balance ba1 = new Balance(BigDecimal.valueOf(12), Currencies.PLN, LocalDateTime.now());
        Balance ba2 = new Balance(BigDecimal.valueOf(-1200.99), Currencies.EUR, LocalDateTime.now());
        Budget bu1 = new Budget("Семейный Бюджет", ba1, LocalDateTime.now());
        Budget bu2 = new Budget("Anton Bond", ba2, LocalDateTime.now());

        bService.saveBudget(bu1);
        bService.saveBudget(bu2);
        uService.addBudget(user.getId(), bu1.getId());
        uService.addBudget(user.getId(), bu2.getId());

        ElementProvider provider1 = new ElementProvider("8minut", "some shop");
        ElementProvider provider2 = new ElementProvider("M111111", "supermarket");

        bService.addElementProvider(bu1.getId(), provider1);
        bService.addElementProvider(bu1.getId(), provider2);
        provider1.setId(0);
        provider2.setId(0);
        bService.addElementProvider(bu2.getId(), provider1);
        bService.addElementProvider(bu2.getId(), provider2);

        System.out.println("=============== MvcConfig#init");
        System.out.println(user);
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
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Budget sessionBudget() {
        return new Budget();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public User sessionUser() {
        return new User();
    }
}
