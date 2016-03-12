/*
 * InvoiceReservationRenderer.java
 *
 * Created on 02 February 2003, 12:48
 */

package org.tastefuljava.ezguest.gui;

import org.tastefuljava.ezguest.data.Article;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author  Maurice Perry
 */
public class InvoiceItemRenderer implements TableCellRenderer {
    public static final int COLUMN_ARTICLE = InvoiceItemTableModel.COLUMN_ARTICLE;
    public static final int COLUMN_PRICE = InvoiceItemTableModel.COLUMN_PRICE;
    public static final int COLUMN_QUANTITY = InvoiceItemTableModel.COLUMN_QUANTITY;
    public static final int COLUMN_AMOUNT = InvoiceItemTableModel.COLUMN_AMOUNT;

    private JLabel label = new JLabel();

    public InvoiceItemRenderer() {
        label.setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }
        label.setFont(table.getFont());
        if (value == null) {
            label.setText("");
        } else {
            switch (column) {
                case COLUMN_ARTICLE:
                    Article article = (Article)value;
                    label.setText(article.getLabel());
                    label.setHorizontalAlignment(JLabel.LEFT);
                    break;
                default:
                    label.setText(value.toString());
                    label.setHorizontalAlignment(JLabel.RIGHT);
                    break;
            }
        }
        return label;
    }
}
