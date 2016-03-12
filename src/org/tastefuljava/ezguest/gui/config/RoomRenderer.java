/*
 * RoomRenderer.java
 *
 * Created on 1 december 2002, 17:39
 */

package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.data.RoomType;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author  denis
 */
public class RoomRenderer implements TableCellRenderer {
    public static final int COLUMN_NUMBER  = 0;
    public static final int COLUMN_TYPE  = 1;

    private JLabel label = new JLabel();

    public RoomRenderer() {
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
            case COLUMN_NUMBER:
                if (value == null) {
                    label.setText("");
                } else {
                    label.setText(value.toString());
                }
                return label;
            case COLUMN_TYPE:
                if (value == null) {
                    label.setText("");
                } else {
                    RoomType roomType = (RoomType)value;
                    label.setText(roomType.getName() + " (" + roomType.getBasePrice() + ")");
                }
                return label;
        }
        return null;
    }
}
