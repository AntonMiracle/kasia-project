package com.kasia.model.budget;

import com.kasia.model.Model;
import com.kasia.model.article.Article;
import com.kasia.model.group.Group;
import com.kasia.model.price.Price;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BudgetTest {
    private Budget budget;

    @Before
    public void before() {
        budget = new Budget();
    }

    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new Budget()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = budget.getClass().getDeclaredFields().length;

        String name = "name";
        Set<Article> articles = new HashSet<>();
        Set<Group> groups = new HashSet<>();
        Price balance = new Price();
        Instant create = Instant.now();
        int expectedSumClassFields = 5;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Budget(name, articles, groups, balance, create)).isNotNull();
    }

    // IMPLEMENTS EXTENDS HASHCODE EQUALS TO_STRING ================================================
    @Test
    public void extendsModel() {
        assertThat(Model.class.isAssignableFrom(budget.getClass())).isTrue();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(budget.getClass())).isTrue();
    }

    @Test
    public void checkEqualsAndHashCode() {
        Article article = new Article();
        article.setCreate(Instant.now());
        Group group = new Group();
        group.setName("Name");

        EqualsVerifier.forClass(budget.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .withPrefabValues(Group.class, new Group(), group)
                .withPrefabValues(Article.class, new Article(), article)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(budget.toString().contains("{")).isTrue();
        assertThat(budget.toString().contains(budget.getClass().getSimpleName())).isTrue();
    }

    // GETTERS SETTERS ================================================
    @Test
    public void setAndGetUsername() {
        budget.setName("name");
        assertThat(budget.getName()).isEqualTo("name");
    }

    @Test
    public void setAndGetArticles() {
        Set<Article> articles = new HashSet<>();
        budget.setArticles(articles);
        assertThat(budget.getArticles()).isEqualTo(articles);
    }

    @Test
    public void setAndGetGroups() {
        Set<Group> groups = new HashSet<>();
        budget.setGroups(groups);
        assertThat(budget.getGroups()).isEqualTo(groups);
    }

    @Test
    public void setAndGetBalance() {
        Price balance = new Price();
        budget.setBalance(balance);
        assertThat(budget.getBalance()).isEqualTo(balance);
    }

    @Test
    public void setAndGetCreate() {
        Instant create = Instant.now();
        budget.setCreate(create);
        assertThat(budget.getCreate()).isEqualTo(create);
    }


}