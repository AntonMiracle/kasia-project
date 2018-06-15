package com.kasia.model.budget;

import com.kasia.model.Model;
import com.kasia.model.article.Article;
import com.kasia.model.group.Group;
import com.kasia.model.price.Price;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

public class Budget extends Model implements Serializable {
    private Instant create;
    private String name;
    private Set<Article> articles;
    private Set<Group> groups;
    private Price balance;

    public Budget() {
    }

    public Budget(String name, Set<Article> articles, Set<Group> groups, Price balance, Instant create) {
        this.name = name;
        this.articles = articles;
        this.groups = groups;
        this.balance = balance;
        this.create = create;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setBalance(Price balance) {
        this.balance = balance;
    }

    public Price getBalance() {
        return balance;
    }

    public void setCreate(Instant create) {
        this.create = create;
    }

    public Instant getCreate() {
        return create;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "create=" + create +
                ", name='" + name + '\'' +
                ", articles=" + articles +
                ", groups=" + groups +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Budget budget = (Budget) o;

        if (create != null ? !create.equals(budget.create) : budget.create != null) return false;
        if (name != null ? !name.equals(budget.name) : budget.name != null) return false;
        if (articles != null ? !articles.equals(budget.articles) : budget.articles != null) return false;
        if (groups != null ? !groups.equals(budget.groups) : budget.groups != null) return false;
        return balance != null ? balance.equals(budget.balance) : budget.balance == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (create != null ? create.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (articles != null ? articles.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
