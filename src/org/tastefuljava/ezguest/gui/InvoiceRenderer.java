/*
 * InvoiceRenderer.java
 *
 * Created on 27 January 2003, 22:23
 */

package org.tastefuljava.ezguest.gui;

import java.awt.Font;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;
import org.tastefuljava.ezguest.util.Util;

/**
 *
 * @author  Maurice Perry
 */
public class InvoiceRenderer implements TableCellRenderer {
    public static final int COLUMN_CUSTOMER = InvoiceTableModel.COLUMN_CUSTOMER;
    public static final int COLUMN_ID = InvoiceTableModel.COLUMN_ID;
    public static final int COLUMN_DATECREATED = InvoiceTableModel.COLUMN_DATECREATED;
    public static final int COLUMN_AMOUNT = InvoiceTableModel.COLUMN_AMOUNT;

    private JLabel label = new JLabel();

    public InvoiceRenderer() {
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
        Font font = table.getFont();
        switch (column) {
            case COLUMN_CUSTOMER:
                font = font.deriveFont(font.getStyle(), font.getSize()+2);
                label.setForeground(table.getForeground());
                label.setFont(font);
                label.setText(value == null ? "" : value.toString());
                label.setHorizontalAlignment(JLabel.LEFT);
                break;
            case COLUMN_ID:
                label.setFont(font);
                label.setText(value == null ? "" : value.toString());
                label.setHorizontalAlignment(JLabel.RIGHT);
                break;
            case COLUMN_DATECREATED:
                label.setFont(font);
                label.setText(value == null ? "" : value.toString());
                label.setHorizontalAlignment(JLabel.RIGHT);
                break;
            case COLUMN_AMOUNT:
                label.setFont(font);
                label.setText(value == null ? "" : Util.dbl2str(((Double)value).doubleValue()));
                label.setHorizontalAlignment(JLabel.RIGHT);
                break;
        }
        return label;
    }
}
