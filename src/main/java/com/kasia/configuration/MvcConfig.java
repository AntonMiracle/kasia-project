package com.kasia.configuration;

import com.kasia.model.*;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.ElementService;
import com.kasia.model.service.PlaceService;
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
import java.time.ZoneId;
import java.util.Locale;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserService userS;
    @Autowired
    private BudgetService budgetS;
    @Autowired
    private PlaceService placeS;
    @Autowired
    private ElementService elementS;

    @PostConstruct
    public void init() {
        User user1 = new User("anton@gmail.com", "Password2", Role.USER, LocalDateTime.now(), true, ZoneId.systemDefault(), Locale.getDefault());
        User user2 = new User("anton2@gmail.com", "Password2", Role.USER, LocalDateTime.now(), true, ZoneId.systemDefault(), Locale.getDefault());
        user1.setZoneId(ZoneId.of("America/Atka"));
        user2.setZoneId(ZoneId.of("America/Atka"));
        user1.setLocale(new Locale("zh", "HK"));
        user2.setLocale(new Locale("zh", "HK"));
        userS.save(user1);
        userS.save(user2);
        Balance balance1 = new Balance(BigDecimal.TEN, Currencies.USD.name(), LocalDateTime.now());
        Balance balance2 = new Balance(BigDecimal.valueOf(-100.2), Currencies.USD.name(), LocalDateTime.now());
        Budget budget1 = new Budget("budget name 1", balance1, LocalDateTime.now());
        Budget budget2 = new Budget("budget name 2", balance2, LocalDateTime.now());
        budgetS.save(budget1);
        budgetS.save(budget2);
        budgetS.setOwner(budget1.getId(), user1.getId());
        budgetS.setOwner(budget2.getId(), user1.getId());

        Place place1 = new Place("Aname", "");
        Place place2 = new Place("Cname", "some description");
        Place place3 = new Place("Bname", "some description");
        Place place4 = new Place("Dname", "some description");
        placeS.save(place1);
        placeS.save(place2);
        placeS.save(place3);
        placeS.save(place4);
        budgetS.addPlace(budget1.getId(), place1.getId());
        budgetS.addPlace(budget1.getId(), place2.getId());
        budgetS.addPlace(budget1.getId(), place3.getId());
        budgetS.addPlace(budget1.getId(), place4.getId());
        place1.setId(0);
        place2.setId(0);
        place3.setId(0);
        place4.setId(0);
        placeS.save(place1);
        placeS.save(place2);
        placeS.save(place3);
        placeS.save(place4);
        budgetS.addPlace(budget2.getId(), place1.getId());
        budgetS.addPlace(budget2.getId(), place2.getId());
        budgetS.addPlace(budget2.getId(), place3.getId());
        budgetS.addPlace(budget2.getId(), place4.getId());

        Element element1 = new Element("Aelement", BigDecimal.valueOf(22));
        Element element2 = new Element("Belement", BigDecimal.valueOf(200));
        Element element3 = new Element("Celement", BigDecimal.valueOf(0));
        Element element4 = new Element("Aelement1", BigDecimal.valueOf(20));
        elementS.save(element1);
        elementS.save(element2);
        elementS.save(element3);
        elementS.save(element4);
        budgetS.addElement(budget1.getId(), element1.getId());
        budgetS.addElement(budget1.getId(), element2.getId());
        budgetS.addElement(budget1.getId(), element3.getId());
        budgetS.addElement(budget1.getId(), element4.getId());
        element1.setId(0);
        element2.setId(0);
        element3.setId(0);
        element4.setId(0);
        elementS.save(element1);
        elementS.save(element2);
        elementS.save(element3);
        elementS.save(element4);
        budgetS.addElement(budget2.getId(), element1.getId());
        budgetS.addElement(budget2.getId(), element2.getId());
        budgetS.addElement(budget2.getId(), element3.getId());
        budgetS.addElement(budget2.getId(), element4.getId());
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
