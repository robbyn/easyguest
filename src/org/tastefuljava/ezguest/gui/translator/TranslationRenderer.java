/*
 * TranslationRenderer.java
 *
 * Created on 26 novembre 2002, 01:39
 */

package org.tastefuljava.ezguest.gui.translator;

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
public class TranslationRenderer implements TableCellRenderer {
    public static final int COLUMN_KEY_LANG       = 0;
    public static final int COLUMN_DEFAULT_LANG   = 1;
    public static final int COLUMN_TRANSLATE_LANG = 2;

    private JLabel label = new JLabel();
    private TranslationTableModel translationTable;

    public TranslationRenderer(TranslationTableModel translationTable) {
        this.translationTable = translationTable;
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

        if (translationTable.isAdded(rowIndex)) {
            label.setForeground(Color.GREEN);
        } else if (translationTable.isDeleted(rowIndex)) {
            label.setForeground(Color.RED);
        }

        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setText((String)value);
        return label;
    }
}
