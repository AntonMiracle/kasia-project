package com.kasia.configuration;

import com.kasia.controller.dto.OperationDTO;
import com.kasia.model.*;
import com.kasia.model.service.BalanceService;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.OperationService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserService uService;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private OperationService oService;
    @Autowired
    private BalanceService balanceService;

//    @PostConstruct
    public void init() {
        User user = uService.createUser("anton@gmail.com", "Anton", "Password2",
                "America/Atka", "pl", "PL");
        uService.saveUser(user);
        Balance ba1 = new Balance(BigDecimal.valueOf(12), Currencies.PLN, LocalDateTime.now());
        Balance ba2 = new Balance(BigDecimal.valueOf(-1200.99), Currencies.EUR, LocalDateTime.now());
        Budget bu1 = new Budget("Семейный Бюджет", ba1, LocalDateTime.now());
        Budget bu2 = new Budget("Anton Bond", ba2, LocalDateTime.now());

        budgetService.saveBudget(bu1);
        budgetService.saveBudget(bu2);
        uService.addBudget(user.getId(), bu1.getId());
        uService.addBudget(user.getId(), bu2.getId());

        // budget 1
        Provider provider1 = new Provider("8minut", "some shop");
        Provider provider2 = new Provider("M111111", "supermarket");
        Element element1 = new Element("food", new Price(BigDecimal.ZERO, bu1.getBalance().getCurrencies()), ElementType.CONSUMPTION);
        Element element2 = new Element("food", new Price(BigDecimal.ZERO, bu1.getBalance().getCurrencies()), ElementType.INCOME);
        Element element3 = new Element("salary", new Price(BigDecimal.TEN, bu1.getBalance().getCurrencies()), ElementType.INCOME);

        budgetService.addProvider(bu1.getId(), provider1);
        budgetService.addProvider(bu1.getId(), provider2);
        budgetService.addElement(bu1.getId(), element1);
        budgetService.addElement(bu1.getId(), element2);
        budgetService.addElement(bu1.getId(), element3);

        Price price1 = balanceService.createPrice(BigDecimal.valueOf(80.22), bu1.getBalance().getCurrencies());
        Price price2 = balanceService.createPrice(BigDecimal.valueOf(1900), bu1.getBalance().getCurrencies());
        Operation op1 = oService.createOperation(user, element1, provider1, price1);
        Operation op2 = oService.createOperation(user, element2, provider2, price2);
        Operation op3 = oService.createOperation(user, element1, provider1, price1);
        op3.setCreateOn(LocalDateTime.of(2018,03,12,12,22,00));
        Operation op4 = oService.createOperation(user, element2, provider2, price2);
        op4.setCreateOn(LocalDateTime.of(2019,01,10,22,59,00));
        oService.addOperation(bu1.getId(), op1);
        oService.addOperation(bu1.getId(), op2);
        oService.addOperation(bu1.getId(), op3);
        oService.addOperation(bu1.getId(), op4);

        // budget 2
        provider1.setId(0);
        provider2.setId(0);
        element1.setId(0);
        element2.setId(0);
        element3.setId(0);
        element1.setDefaultPrice(new Price(BigDecimal.ZERO, bu2.getBalance().getCurrencies()));
        element2.setDefaultPrice(new Price(BigDecimal.ZERO, bu2.getBalance().getCurrencies()));
        element3.setDefaultPrice(new Price(BigDecimal.ZERO, bu2.getBalance().getCurrencies()));

        budgetService.addProvider(bu2.getId(), provider1);
        budgetService.addProvider(bu2.getId(), provider2);
        budgetService.addElement(bu2.getId(), element1);
        budgetService.addElement(bu2.getId(), element2);
        budgetService.addElement(bu2.getId(), element3);

        price1 = balanceService.createPrice(BigDecimal.valueOf(80.22), bu2.getBalance().getCurrencies());
        price2 = balanceService.createPrice(BigDecimal.valueOf(1900), bu2.getBalance().getCurrencies());
        op1 = oService.createOperation(user, element1, provider1, price1);
        op2 = oService.createOperation(user, element2, provider2, price2);
        op3 = oService.createOperation(user, element1, provider1, price1);
        op3.setCreateOn(LocalDateTime.of(2018,03,12,12,22,00));
        op4 = oService.createOperation(user, element2, provider2, price2);
        op4.setCreateOn(LocalDateTime.of(2019,01,10,22,59,00));
        oService.addOperation(bu2.getId(), op1);
        oService.addOperation(bu2.getId(), op2);
        oService.addOperation(bu2.getId(), op3);
        oService.addOperation(bu2.getId(), op4);

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

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OperationDTO sessionOperationDTO() {
        return new OperationDTO();
    }
}
