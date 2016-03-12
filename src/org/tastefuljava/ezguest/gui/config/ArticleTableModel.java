/*
 * ArticleTableModel.java
 *
 * Created on 03 November 2002, 15:19
 */

package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.util.Util;
import org.tastefuljava.ezguest.data.Article;
import org.tastefuljava.ezguest.data.ArticleCategory;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.util.List;
import java.util.ArrayList;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class ArticleTableModel extends AbstractTableModel {
    public static final int COLUMN_CODE   = 0;
    public static final int COLUMN_LABEL  = 1;
    public static final int COLUMN_PRICE  = 2;
    public static final int COLUMN_KEYB   = 3;

    private static final int COLUMN_COUNT = 4;

    private EasyguestSession sess;
    private int categoryId;
    private List<Article> articles = new ArrayList<Article>();

    public ArticleTableModel(EasyguestSession sess) {
        this.sess = sess;
    }

    public void setCategoryId(int newValue) {
        articles.clear();
        if (newValue >= 0) {
            sess.begin();
            try {
                ArticleCategory category = sess.getObjectById(ArticleCategory.class, newValue);
                if (category != null) {
                    articles.addAll(category.getArticles());
                }
            } finally {
                sess.end();
            }
        }
        categoryId = newValue;
        fireTableDataChanged();
    }

    public Article getArticle(int index) {
        while (index >= 0 && index < articles.size()) {
            Article article = articles.get(index);
            article = sess.getObjectById(Article.class, article.getId());
            if (article != null) {
                articles.set(index, article);
                fireTableRowsUpdated(index, index);
                return article;
            }
            articles.remove(index);
            fireTableRowsDeleted(index, index);
        }
        return null;
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    public int getRowCount() {
        return articles.size();
    }

    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_CODE:
                return Util.getResource("article.column.code");
            case COLUMN_LABEL:
                return Util.getResource("article.column.label");
            case COLUMN_PRICE:
                return Util.getResource("article.column.price");
            case COLUMN_KEYB:
                return Util.getResource("article.column.keys");
        }
        return null;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= articles.size()) {
            return null;
        }
        Article article = articles.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_CODE:
                return article.getCode();
            case COLUMN_LABEL:
                return article.getLabel();
            case COLUMN_PRICE:
                return new Double(article.getPrice());
            case COLUMN_KEYB:
                return article.getKeyStroke();
        }
        return null;
    }

    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_CODE:
                return String.class;
            case COLUMN_LABEL:
                return String.class;
            case COLUMN_PRICE:
                return Double.class;
            case COLUMN_KEYB:
                return KeyStroke.class;
        }
        return null;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        boolean isNew = rowIndex >= articles.size();
        Article article;
        sess.begin();
        try {
            if (!isNew) {
                article = articles.get(rowIndex);
            } else {
                ArticleCategory category = categoryId < 0 ? null
                        : sess.getObjectById(ArticleCategory.class, categoryId);
                article = new Article();
                article.setCode("");
                article.setLabel("");
                article.setPrice(0.0);
                article.setKeyStroke(null);
                article.setCategory(category);
                sess.makePersistent(article);
            }
            switch (columnIndex) {
                case COLUMN_CODE:
                    article.setCode((String)value);
                    break;

                case COLUMN_LABEL:
                    article.setLabel((String)value);
                    break;

                case COLUMN_PRICE:
                    article.setPrice(Double.parseDouble(value.toString()));
                    break;

                case COLUMN_KEYB:
                    article.setKeyStroke((KeyStroke)value);
                    break;

            }
            sess.commit();
        } finally {
            sess.end();
        }
        if (isNew) {
            articles.add(article);
            fireTableRowsInserted(rowIndex+1, rowIndex+1);
        }
    }
}
