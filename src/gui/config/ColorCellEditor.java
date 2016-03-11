/*
 * ColorCellEditor.java
 *
 * Created on 28 novembre 2002, 17:56
 */

package gui.config;

import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import components.ColorButton;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class ColorCellEditor extends AbstractCellEditor
        implements TableCellEditor {
    private ColorButton colorButton;

    public ColorCellEditor() {
        colorButton = new ColorButton();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
       colorButton.setColor((Color)value);
       return colorButton;
    }

    public Object getCellEditorValue() {
        return colorButton.getColor();
    }
}
