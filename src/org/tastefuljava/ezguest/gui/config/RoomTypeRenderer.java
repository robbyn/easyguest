package org.tastefuljava.ezguest.gui.config;

import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RoomTypeRenderer implements TableCellRenderer {
    public static final int COLUMN_NAME        = 0;
    public static final int COLUMN_PRICE       = 1;
    public static final int COLUMN_DESCRIPTION = 2;

    private final JLabel label = new JLabel();
    private final JTextArea textArea = new JTextArea();

    public RoomTypeRenderer() {
        label.setOpaque(true);
        textArea.setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }
        label.setHorizontalAlignment(SwingConstants.LEFT);
        switch (col) {
            case COLUMN_NAME:
            case COLUMN_PRICE:
            case COLUMN_DESCRIPTION:
                if (value == null) {
                    label.setText("");
                } else {
                    label.setText(value.toString());
                }
                return label;
        }
        return null;
    }
}
