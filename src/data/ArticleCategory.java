/*
 * ArticleCategory.java
 *
 * Created on 23 June 2002, 17:18
 */

package data;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author  maurice
 */
public class ArticleCategory {
    private int id;
    private String name;
    private ArticleCategory parent;
    private Set<ArticleCategory> subCategories = new HashSet<ArticleCategory>();
    private Set<Article> articles = new HashSet<Article>();

    public String toString() {
        return name;
    }

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
}
