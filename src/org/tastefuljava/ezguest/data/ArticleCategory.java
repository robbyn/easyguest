package org.tastefuljava.ezguest.data;

import java.util.HashSet;
import java.util.Set;

public class ArticleCategory {
    private int id;
    private String name;
    private ArticleCategory parent;
    private final Set<ArticleCategory> subCategories = new HashSet<>();
    private final Set<Article> articles = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String newValue) {
        name = newValue;
    }

    public ArticleCategory getParent() {
        return parent;
    }

    public void setParent(ArticleCategory newValue) {
        if (parent != null) {
            parent.getSubCategories().remove(this);
        }
        parent = newValue;
        if (parent != null) {
            parent.getSubCategories().add(this);
        }
    }

    public Set<ArticleCategory> getSubCategories() {
        return subCategories;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    @Override
    public String toString() {
        return name;
    }
}
