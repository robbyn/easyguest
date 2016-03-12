package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.components.ColorIcon;
import org.tastefuljava.ezguest.data.Tariff;
import org.tastefuljava.ezguest.util.Util;
import java.awt.Component;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author  denis
 */
public class PeriodRenderer implements TableCellRenderer {
    public static final int COLUMN_FROMDATE  = 0;
    public static final int COLUMN_TODATE  = 1;
    public static final int COLUMN_TARIFF = 2;
    private JLabel label = new JLabel();

    public PeriodRenderer() {
       label.setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
           boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }
        label.setHorizontalAlignment(SwingConstants.LEFT);
        switch (columnIndex) {
            case COLUMN_FROMDATE:
                label.setText(Util.date2str((Date)value));
                label.setIcon(null);
                return label;
            case COLUMN_TODATE:
                label.setText(Util.date2str((Date)value));
                label.setIcon(null);
                return label;
            case COLUMN_TARIFF:
                Tariff tariff = (Tariff)value;
                if (tariff == null) {
                    label.setText("");
                    label.setIcon(null);
                } else {
                    label.setText(tariff.getName() + " (" + tariff.getFactor() + ")");
                    label.setIcon(new ColorIcon(tariff.getColor()));
                }
                return label;
        }
        return null;
    }
}
