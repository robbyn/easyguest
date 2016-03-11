/*
 * RoomTypeRenderer.java
 *
 * Created on 14 January 2003, 10:38
 */

package gui.config;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author  Maurice Perry
 */
public class RoomTypeRenderer implements TableCellRenderer {
    public static final int COLUMN_NAME        = 0;
    public static final int COLUMN_PRICE       = 1;
    public static final int COLUMN_DESCRIPTION = 2;

    private JLabel label = new JLabel();
    private JTextArea textArea = new JTextArea();

    public RoomTypeRenderer() {
        label.setOpaque(true);
        textArea.setOpaque(true);
    }

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
