package org.tastefuljava.ezguest.gui;

import org.tastefuljava.ezguest.data.InvoiceItem;
import org.tastefuljava.ezguest.data.Invoice;
import org.tastefuljava.ezguest.data.Article;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.tastefuljava.ezguest.util.Util;

@SuppressWarnings("serial")
public class InvoiceItemTableModel extends AbstractTableModel {
    public static final int COLUMN_ARTICLE  = 0;
    public static final int COLUMN_PRICE    = 1;
    public static final int COLUMN_QUANTITY = 2;
    public static final int COLUMN_AMOUNT   = 3;

    public static final int COLUMN_COUNT = 4;

    private final EasyguestSession sess;
    private final List<InvoiceItem> items = new ArrayList<>();
    private Invoice invoice;

    public InvoiceItemTableModel(EasyguestSession sess) {
        this.sess = sess;
    }

    public void setInvoice(Invoice newValue) {
        invoice = newValue;
        items.clear();
        if (invoice != null) {
            items.addAll(invoice.getItems());
        }
        fireTableDataChanged();
    }

    public void addArticle(Article article) {
        if (invoice == null) {
            return;
        }
        for (int i = 0; i < items.size(); ++i) {
            InvoiceItem item = items.get(i);
            if (item.getArticle() == article) {
                sess.begin();
                try {
                    item.setQuantity(item.getQuantity()+1);
                    sess.commit();
                } finally {
                    sess.end();
                }
                fireTableRowsUpdated(i, i);
                return;
            }
        }
        InvoiceItem item;
        sess.begin();
        try {
            item = new InvoiceItem();
            item.setInvoice(invoice);
            item.setArticle(article);
            item.setPrice(article.getPrice());
            item.setQuantity(1);
            sess.makePersistent(item);
            sess.commit();
        } finally {
            sess.end();
        }
        int row = items.size();
        items.add(item);
        fireTableRowsInserted(row, row);
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public int getRowCount() {
        return items.size()+1;
    }

    @Override
    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_ARTICLE:
                return Util.getResource("invoice-item.column.article");
            case COLUMN_PRICE:
                return Util.getResource("invoice-item.column.price");
            case COLUMN_QUANTITY:
                return Util.getResource("invoice-item.column.quantity");
            case COLUMN_AMOUNT:
                return Util.getResource("invoice-item.column.amount");
        }
        return null;
    }

    @Override
    public Class getColumnClass(int index) {
        switch (index) {
            case COLUMN_ARTICLE:
                return Article.class;
            case COLUMN_PRICE:
            case COLUMN_QUANTITY:
            case COLUMN_AMOUNT:
                return Double.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != COLUMN_AMOUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= items.size()) {
            return null;
        }
        InvoiceItem item = items.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_ARTICLE:
                return item.getArticle();
            case COLUMN_PRICE:
                return item.getPrice();
            case COLUMN_QUANTITY:
                return item.getQuantity();
            case COLUMN_AMOUNT:
                return item.getAmount();
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        boolean isNew = rowIndex >= items.size();
        InvoiceItem item;
        sess.begin();
        try {
            if (!isNew) {
                item = items.get(rowIndex);
            } else {
                item = new InvoiceItem();
                item.setInvoice(invoice);
                item.setArticle(null);
                item.setPrice(0);
                item.setQuantity(1);
                sess.makePersistent(item);
            }
            switch (columnIndex) {
                case COLUMN_ARTICLE:
                    Article article = (Article)value;
                    item.setArticle((Article)value);
                    if (article != null) {
                        item.setPrice(article.getPrice());
                        fireTableCellUpdated(rowIndex, COLUMN_PRICE);
                        fireTableCellUpdated(rowIndex, COLUMN_AMOUNT);
                    }
                    break;
                case COLUMN_PRICE:
                    item.setPrice((Double)value);
                    fireTableCellUpdated(rowIndex, COLUMN_AMOUNT);
                    break;
                case COLUMN_QUANTITY:
                    item.setQuantity((Double)value);
                    fireTableCellUpdated(rowIndex, COLUMN_AMOUNT);
                    break;
            }
            sess.commit();
        } finally {
            sess.end();
        }
        if (isNew) {
            items.add(item);
            fireTableRowsInserted(rowIndex+1, rowIndex+1);
        }
    }
}
