package com.kasia.configuration;

import com.kasia.controller.dto.OperationDTO;
import com.kasia.controller.dto.OperationsHistoryPages;
import com.kasia.model.*;
import com.kasia.model.service.*;
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
    @Autowired
    private OperationService operationS;

    @PostConstruct
    public void init() {
        User user1 = new User("anton@gmail.com", "Password2", Role.USER, LocalDateTime.now(), true, ZoneId.systemDefault(), Locale.getDefault());
        User user2 = new User("anton2@gmail.com", "Password2", Role.USER, LocalDateTime.now(), true, ZoneId.systemDefault(), Locale.getDefault());
        user1.setZoneId(ZoneId.systemDefault());
        user2.setZoneId(ZoneId.of("America/Atka"));
        user1.setLocale(new Locale("pl", "PL"));
        user2.setLocale(new Locale("zh", "HK"));
        userS.save(user1);
        userS.save(user2);
        Balance balance1 = new Balance(BigDecimal.TEN, Currencies.PLN.name(), LocalDateTime.now());
        Balance balance2 = new Balance(BigDecimal.valueOf(-100.2), Currencies.EUR.name(), LocalDateTime.now());
        Budget budget1 = new Budget("Budżet rodziny", balance1, LocalDateTime.now());
        Budget budget2 = new Budget("Mój budżet", balance2, LocalDateTime.now());
        budgetS.save(budget1);
        budgetS.save(budget2);
        budgetS.setOwner(budget1.getId(), user1.getId());
        budgetS.setOwner(budget2.getId(), user1.getId());

        Place place1 = new Place("Auchan M1", "");
        Place place2 = new Place("Auchan Bora", "Bora komorowskiego");
        Place place3 = new Place("dom", "");
        Place place4 = new Place("MPK", "");
        Place place5 = new Place("Praca", "");
        Place place6 = new Place("Rybitwy", "");
        Place place7 = new Place("Szkoła Varia", "kursy języka Polskiego");
        Place place8 = new Place("OBI", "");
        Place place9 = new Place("Galleria Krakowska", "");
        placeS.save(place1);
        placeS.save(place2);
        placeS.save(place3);
        placeS.save(place4);
        placeS.save(place5);
        placeS.save(place6);
        placeS.save(place7);
        placeS.save(place8);
        placeS.save(place9);
        budgetS.addPlace(budget1.getId(), place1.getId());
        budgetS.addPlace(budget1.getId(), place2.getId());
        budgetS.addPlace(budget1.getId(), place3.getId());
        budgetS.addPlace(budget1.getId(), place4.getId());
        budgetS.addPlace(budget1.getId(), place5.getId());
        budgetS.addPlace(budget1.getId(), place6.getId());
        budgetS.addPlace(budget1.getId(), place7.getId());
        budgetS.addPlace(budget1.getId(), place8.getId());
        budgetS.addPlace(budget1.getId(), place9.getId());

        Element element1 = new Element("Pensia", BigDecimal.valueOf(22));
        Element element2 = new Element("Prezent", BigDecimal.valueOf(200));
        Element element3 = new Element("Transport", BigDecimal.valueOf(0));
        Element element4 = new Element("Jedzenie", BigDecimal.valueOf(20));
        Element element5 = new Element("Dług", BigDecimal.valueOf(20));
        Element element6 = new Element("Odzież", BigDecimal.valueOf(20));
        Element element7 = new Element("Chemia", BigDecimal.valueOf(20));
        Element element8 = new Element("Media", BigDecimal.valueOf(20));
        Element element9 = new Element("Play", BigDecimal.valueOf(20));
        Element element10 = new Element("Mamusia Kasia", BigDecimal.valueOf(20));
        Element element11 = new Element("Syn Staś", BigDecimal.valueOf(20));
        Element element12 = new Element("Ojciec Anton", BigDecimal.valueOf(20));
        elementS.save(element1);
        elementS.save(element2);
        elementS.save(element3);
        elementS.save(element4);
        elementS.save(element5);
        elementS.save(element6);
        elementS.save(element7);
        elementS.save(element8);
        elementS.save(element9);
        elementS.save(element10);
        elementS.save(element11);
        elementS.save(element12);
        budgetS.addElement(budget1.getId(), element1.getId());
        budgetS.addElement(budget1.getId(), element2.getId());
        budgetS.addElement(budget1.getId(), element3.getId());
        budgetS.addElement(budget1.getId(), element4.getId());
        budgetS.addElement(budget1.getId(), element5.getId());
        budgetS.addElement(budget1.getId(), element6.getId());
        budgetS.addElement(budget1.getId(), element7.getId());
        budgetS.addElement(budget1.getId(), element8.getId());
        budgetS.addElement(budget1.getId(), element9.getId());
        budgetS.addElement(budget1.getId(), element10.getId());
        budgetS.addElement(budget1.getId(), element11.getId());
        budgetS.addElement(budget1.getId(), element12.getId());

        Operation operation1 = new Operation(user1, element1, place5, BigDecimal.valueOf(5000), LocalDateTime.now(), "", OperationType.INCOME);
        Operation operation2 = new Operation(user1, element2, place1, BigDecimal.valueOf(100), LocalDateTime.now().minusDays(1), "prezent na urodziny babci", OperationType.CONSUMPTION);
        Operation operation3 = new Operation(user1, element3, place4, BigDecimal.valueOf(72), LocalDateTime.now().minusDays(8), "", OperationType.CONSUMPTION);
        Operation operation4 = new Operation(user1, element4, place2, BigDecimal.valueOf(120), LocalDateTime.now().minusYears(2), "", OperationType.CONSUMPTION);
        Operation operation5 = new Operation(user1, element4, place2, BigDecimal.valueOf(99), LocalDateTime.now().minusHours(10), "", OperationType.CONSUMPTION);
        Operation operation6 = new Operation(user1, element4, place6, BigDecimal.valueOf(32), LocalDateTime.now().minusMonths(2), "", OperationType.CONSUMPTION);
        Operation operation7 = new Operation(user1, element8, place3, BigDecimal.valueOf(452.3), LocalDateTime.now().minusWeeks(1), " +internet", OperationType.CONSUMPTION);
        Operation operation8 = new Operation(user1, element5, place3, BigDecimal.valueOf(150), LocalDateTime.now().minusMinutes(20), " brat wrócił dług", OperationType.INCOME);
        Operation operation9 = new Operation(user1, element9, place3, BigDecimal.valueOf(20), LocalDateTime.now().minusSeconds(20), "", OperationType.CONSUMPTION);
        Operation operation10 = new Operation(user1, element11, place1, BigDecimal.valueOf(35), LocalDateTime.now().minusDays(1), "zabawka", OperationType.CONSUMPTION);
        Operation operation11 = new Operation(user1, element10, place9, BigDecimal.valueOf(35), LocalDateTime.now().minusDays(2), "", OperationType.CONSUMPTION);
        Operation operation12 = new Operation(user1, element12, place5, BigDecimal.valueOf(3500), LocalDateTime.now().minusDays(2), "", OperationType.INCOME);
        Operation operation13 = new Operation(user1, element7, place2, BigDecimal.valueOf(120), LocalDateTime.now().minusMinutes(2), "", OperationType.CONSUMPTION);
        Operation operation14 = new Operation(user1, element11, place7, BigDecimal.valueOf(120), LocalDateTime.now().minusDays(1), "zajęcia indywidualne", OperationType.CONSUMPTION);
        Operation operation15 = new Operation(user1, element6, place1, BigDecimal.valueOf(120), LocalDateTime.now().minusWeeks(1), "buty", OperationType.CONSUMPTION);
        operationS.save(operation1);
        operationS.save(operation2);
        operationS.save(operation3);
        operationS.save(operation4);
        operationS.save(operation5);
        operationS.save(operation6);
        operationS.save(operation7);
        operationS.save(operation8);
        operationS.save(operation9);
        operationS.save(operation10);
        operationS.save(operation11);
        operationS.save(operation12);
        operationS.save(operation13);
        operationS.save(operation14);
        operationS.save(operation15);
        budgetS.addOperation(budget1.getId(), operation1.getId());
        budgetS.addOperation(budget1.getId(), operation2.getId());
        budgetS.addOperation(budget1.getId(), operation3.getId());
        budgetS.addOperation(budget1.getId(), operation4.getId());
        budgetS.addOperation(budget1.getId(), operation5.getId());
        budgetS.addOperation(budget1.getId(), operation6.getId());
        budgetS.addOperation(budget1.getId(), operation7.getId());
        budgetS.addOperation(budget1.getId(), operation8.getId());
        budgetS.addOperation(budget1.getId(), operation9.getId());
        budgetS.addOperation(budget1.getId(), operation10.getId());
        budgetS.addOperation(budget1.getId(), operation11.getId());
        budgetS.addOperation(budget1.getId(), operation12.getId());
        budgetS.addOperation(budget1.getId(), operation13.getId());
        budgetS.addOperation(budget1.getId(), operation14.getId());
        budgetS.addOperation(budget1.getId(), operation15.getId());
        place1.setId(0);
        place2.setId(0);
        place3.setId(0);
        place4.setId(0);
        place5.setId(0);
        place6.setId(0);
        place7.setId(0);
        place8.setId(0);
        place9.setId(0);
        placeS.save(place1);
        placeS.save(place2);
        placeS.save(place3);
        placeS.save(place4);
        placeS.save(place5);
        placeS.save(place6);
        placeS.save(place7);
        placeS.save(place8);
        placeS.save(place9);
        budgetS.addPlace(budget2.getId(), place1.getId());
        budgetS.addPlace(budget2.getId(), place2.getId());
        budgetS.addPlace(budget2.getId(), place3.getId());
        budgetS.addPlace(budget2.getId(), place4.getId());
        budgetS.addPlace(budget2.getId(), place5.getId());
        budgetS.addPlace(budget2.getId(), place6.getId());
        budgetS.addPlace(budget2.getId(), place7.getId());
        budgetS.addPlace(budget2.getId(), place8.getId());
        budgetS.addPlace(budget2.getId(), place9.getId());
        element1.setId(0);
        element2.setId(0);
        element3.setId(0);
        element4.setId(0);
        element5.setId(0);
        element6.setId(0);
        element7.setId(0);
        element8.setId(0);
        element9.setId(0);
        element10.setId(0);
        element11.setId(0);
        element12.setId(0);
        elementS.save(element1);
        elementS.save(element2);
        elementS.save(element3);
        elementS.save(element4);
        elementS.save(element5);
        elementS.save(element6);
        elementS.save(element7);
        elementS.save(element8);
        elementS.save(element9);
        elementS.save(element10);
        elementS.save(element11);
        elementS.save(element12);
        budgetS.addElement(budget2.getId(), element1.getId());
        budgetS.addElement(budget2.getId(), element2.getId());
        budgetS.addElement(budget2.getId(), element3.getId());
        budgetS.addElement(budget2.getId(), element4.getId());
        budgetS.addElement(budget2.getId(), element5.getId());
        budgetS.addElement(budget2.getId(), element6.getId());
        budgetS.addElement(budget2.getId(), element7.getId());
        budgetS.addElement(budget2.getId(), element8.getId());
        budgetS.addElement(budget2.getId(), element9.getId());
        budgetS.addElement(budget2.getId(), element10.getId());
        budgetS.addElement(budget2.getId(), element11.getId());
        budgetS.addElement(budget2.getId(), element12.getId());
        operation1.setId(0);
        operation2.setId(0);
        operation3.setId(0);
        operation4.setId(0);
        operation5.setId(0);
        operation6.setId(0);
        operation7.setId(0);
        operation8.setId(0);
        operation9.setId(0);
        operation10.setId(0);
        operation11.setId(0);
        operation12.setId(0);
        operation13.setId(0);
        operation14.setId(0);
        operation15.setId(0);
        operationS.save(operation1);
        operationS.save(operation2);
        operationS.save(operation3);
        operationS.save(operation4);
        operationS.save(operation5);
        operationS.save(operation6);
        operationS.save(operation7);
        operationS.save(operation8);
        operationS.save(operation9);
        operationS.save(operation10);
        operationS.save(operation11);
        operationS.save(operation12);
        operationS.save(operation13);
        operationS.save(operation14);
        operationS.save(operation15);
        budgetS.addOperation(budget2.getId(), operation1.getId());
        budgetS.addOperation(budget2.getId(), operation2.getId());
        budgetS.addOperation(budget2.getId(), operation3.getId());
        budgetS.addOperation(budget2.getId(), operation4.getId());
        budgetS.addOperation(budget2.getId(), operation5.getId());
        budgetS.addOperation(budget2.getId(), operation6.getId());
        budgetS.addOperation(budget2.getId(), operation7.getId());
        budgetS.addOperation(budget2.getId(), operation8.getId());
        budgetS.addOperation(budget2.getId(), operation9.getId());
        budgetS.addOperation(budget2.getId(), operation10.getId());
        budgetS.addOperation(budget2.getId(), operation11.getId());
        budgetS.addOperation(budget2.getId(), operation12.getId());
        budgetS.addOperation(budget2.getId(), operation13.getId());
        budgetS.addOperation(budget2.getId(), operation14.getId());
        budgetS.addOperation(budget2.getId(), operation15.getId());
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

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OperationsHistoryPages sessionWeekOperationHistory() {
        return new OperationsHistoryPages();
    }

}
