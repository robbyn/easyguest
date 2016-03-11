/*
 * Article.java
 *
 * Created on 23 June 2002, 17:12
 */

package data;
import javax.swing.KeyStroke;

/**
 *
 * @author  maurice
 */
public class Article {
    private int id;
    private String code;
    private String label;
    private double price;
    private int keyCode;
    private int keyModifiers;
    private ArticleCategory category;

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String newValue) {
        code = newValue;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String newValue) {
        label = newValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double value) {
        price = value;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int newValue) {
        keyCode = newValue;
    }

    public int getKeyModifiers() {
        return keyModifiers;
    }

    public void setKeyModifiers(int newValue) {
        keyModifiers = newValue;
    }

    public KeyStroke getKeyStroke() {
        if (keyCode == 0) {
            return null;
        } else {
            return KeyStroke.getKeyStroke(keyCode, keyModifiers);
        }
    }

    public void setKeyStroke(KeyStroke newValue) {
        if (newValue == null) {
            keyCode = 0;
            keyModifiers = 0;
        } else {
            keyCode = newValue.getKeyCode();
            keyModifiers = newValue.getModifiers();
        }
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory newValue) {
        if (category != null) {
            category.getArticles().remove(this);
        }
        category = newValue;
        if (category != null) {
            category.getArticles().add(this);
        }
    }
}
