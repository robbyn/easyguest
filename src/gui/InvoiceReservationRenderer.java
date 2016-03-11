/*
 * InvoiceReservationRenderer.java
 *
 * Created on 02 February 2003, 12:48
 */

package gui;

import java.util.Date;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;
import util.Util;

/**
 *
 * @author  Maurice Perry
 */
public class InvoiceReservationRenderer implements TableCellRenderer {
    public static final int COLUMN_FROMDATE = InvoiceReservationTableModel.COLUMN_FROMDATE;
    public static final int COLUMN_TODATE = InvoiceReservationTableModel.COLUMN_TODATE;
    public static final int COLUMN_ROOMNUMBER = InvoiceReservationTableModel.COLUMN_ROOMNUMBER;
    public static final int COLUMN_AMOUNT = InvoiceReservationTableModel.COLUMN_AMOUNT;

    private JLabel label = new JLabel();

    public InvoiceReservationRenderer() {
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
        label.setHorizontalAlignment(JLabel.RIGHT);
        if (value == null) {
            label.setText("");
        } else {
            switch (column) {
                case COLUMN_FROMDATE:
                case COLUMN_TODATE:
                    label.setText(Util.date2str((Date)value));
                    break;
                default:
                    label.setText(value.toString());
                    break;
            }
        }
        return label;
    }
}
