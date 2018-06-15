package com.kasia.model.article;

import com.kasia.model.Model;
import com.kasia.model.group.Group;
import com.kasia.model.group.Type;
import com.kasia.model.item.Item;
import com.kasia.model.price.Price;
import com.kasia.model.user.User;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArticleTest {
    private Article article;

    @Before
    public void before() {
        article = new Article();
    }

    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new Article()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = article.getClass().getDeclaredFields().length;

        String description = "description";
        User maker = new User();
        Instant create = Instant.now();
        Group group = new Group();
        Item item = new Item();
        int quantity = 0;
        Price price = new Price();
        int expectedSumClassFields = 7;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Article(maker, description, create, group, item, quantity, price)).isNotNull();
    }

    // IMPLEMENTS EXTENDS HASHCODE EQUALS TO_STRING ================================================
    @Test
    public void extendsModel() {
        assertThat(Model.class.isAssignableFrom(article.getClass())).isTrue();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(article.getClass())).isTrue();
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(article.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .withPrefabValues(Group.class, new Group(), new Group("name", Type.ARTICLE))
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(article.toString().contains("{")).isTrue();
        assertThat(article.toString().contains(article.getClass().getSimpleName())).isTrue();
    }

    // GETTERS SETTERS ================================================
    @Test
    public void setAndGetDescription() {
        article.setDescription("text");
        assertThat(article.getDescription()).isEqualTo("text");
    }

    @Test
    public void test() {
        User user = new User();
        article.setMaker(user);
        assertThat(article.getMaker()).isEqualTo(user);
    }

    @Test
    public void setAndGetCreate() {
        Instant create = Instant.now();
        article.setCreate(create);
        assertThat(article.getCreate()).isEqualTo(create);
    }

    @Test
    public void setAndGetGroup() {
        Group group = new Group();
        article.setGroup(group);
        assertThat(article.getGroup()).isEqualTo(group);
    }

    @Test
    public void setAndGetItem() {
        Item item = new Item();
        article.setItem(item);
        assertThat(article.getItem()).isEqualTo(item);
    }

    @Test
    public void setAndGetQuantity() {
        article.setQuantity(2);
        assertThat(article.getQuantity()).isEqualTo(2);
    }

}