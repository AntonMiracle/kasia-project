package com.kasia.model;

import com.kasia.service.validation.constraint.ArticleConstraint;

import javax.inject.Named;
import javax.persistence.*;

@Named
@ArticleConstraint
@Entity
@Table(name = "ARTICLES")
public class Article implements Model {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TYPE", nullable = false)
    private Type type;

    public Article() {
    }

    public Article(String name, String description, Type type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (id != article.id) return false;
        if (name != null ? !name.equals(article.name) : article.name != null) return false;
        if (description != null ? !description.equals(article.description) : article.description != null) return false;
        return type == article.type;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        INCOME, CONSUMPTION

    }
}

