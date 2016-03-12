package org.tastefuljava.ezguest.gui.config;

import java.awt.Component;
import java.awt.Color;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.tastefuljava.ezguest.components.ColorButton;

@SuppressWarnings("serial")
public class ColorCellEditor extends AbstractCellEditor
        implements TableCellEditor {
    private final ColorButton colorButton;

    public ColorCellEditor() {
        colorButton = new ColorButton();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
       colorButton.setColor((Color)value);
       return colorButton;
    }

    @Override
    public Object getCellEditorValue() {
        return colorButton.getColor();
    }
}
