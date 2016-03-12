/*
 * ColorCellEditor.java
 *
 * Created on 28 novembre 2002, 17:56
 */

package org.tastefuljava.ezguest.gui;

import java.util.Date;
import java.text.ParseException;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import org.tastefuljava.ezguest.util.Util;

/**
 * @author  denis
 */
@SuppressWarnings("serial")
public class DateCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JTextField textField = new JTextField();

    public DateCellEditor() {
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
       if (value instanceof Date) {
           textField.setText(Util.date2str((Date)value));
           textField.setSelectionStart(0);
           textField.setSelectionEnd(textField.getText().length());
       }
       return textField;
    }

    public Object getCellEditorValue() {
        try {
            return Util.str2date(textField.getText());
        } catch (ParseException e) {
            return null;
        }
    }
}
