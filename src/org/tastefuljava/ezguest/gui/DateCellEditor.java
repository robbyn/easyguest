package org.tastefuljava.ezguest.gui;

import java.util.Date;
import java.text.ParseException;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import org.tastefuljava.ezguest.util.Util;

@SuppressWarnings("serial")
public class DateCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JTextField textField = new JTextField();

    public DateCellEditor() {
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
       if (value instanceof Date) {
           textField.setText(Util.date2str((Date)value));
           textField.setSelectionStart(0);
           textField.setSelectionEnd(textField.getText().length());
       }
       return textField;
    }

    @Override
    public Object getCellEditorValue() {
        try {
            return Util.str2date(textField.getText());
        } catch (ParseException e) {
            return null;
        }
    }
}
