/*
 * TariffRenderer.java
 *
 * Created on 26 novembre 2002, 01:39
 */

package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.components.ColorButton;
import org.tastefuljava.ezguest.components.ColorIcon;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author  denis
 */
public class TariffRenderer implements TableCellRenderer {
    public static final int COLUMN_NAME  = 0;
    public static final int COLUMN_FACTOR  = 1;
    public static final int COLUMN_COLOR = 2;

    private JLabel label = new JLabel();

    public TariffRenderer() {
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
            case COLUMN_NAME:
            case COLUMN_FACTOR:
                label.setText(value == null ? "" : value.toString());
                label.setIcon(null);
                return label;
            case COLUMN_COLOR:
                label.setText(value == null ? "" : ColorButton.colorToString((Color)value));
                label.setIcon(value == null ? null : new ColorIcon((Color)value));
                return label;
        }
        return null;
    }
}
