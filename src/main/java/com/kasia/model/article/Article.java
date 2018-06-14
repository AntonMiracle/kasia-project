package com.kasia.model.article;

import com.kasia.model.Model;
import com.kasia.model.group.Group;
import com.kasia.model.item.Item;
import com.kasia.model.price.Price;

import java.io.Serializable;
import java.time.Instant;

public class Article extends Model implements Serializable {
    private String description;
    private Price price;
    private int quantity;
    private Item item;
    private Group group;
    private Instant create;

    public Article(String description, Instant create, Group group, Item item, int quantity, Price price) {
        this.description = description;
        this.create = create;
        this.group = group;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public Article() {

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreate(Instant create) {
        this.create = create;
    }

    public Instant getCreate() {
        return create;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Article article = (Article) o;

        if (quantity != article.quantity) return false;
        if (description != null ? !description.equals(article.description) : article.description != null) return false;
        if (price != null ? !price.equals(article.price) : article.price != null) return false;
        if (item != null ? !item.equals(article.item) : article.item != null) return false;
        if (group != null ? !group.equals(article.group) : article.group != null) return false;
        return create != null ? create.equals(article.create) : article.create == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (create != null ? create.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", item=" + item +
                ", group=" + group +
                ", create=" + create +
                '}';
    }
}
