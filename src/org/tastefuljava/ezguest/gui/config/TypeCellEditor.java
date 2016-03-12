/*
 * TypeCellEditor.java
 *
 * Created on 2 december 2002, 13:49
 */
package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.session.EasyguestSession;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JComboBox;
import org.tastefuljava.ezguest.data.RoomType;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class TypeCellEditor extends AbstractCellEditor
        implements TableCellEditor { 
    private JComboBox typeComboBox;
    private TypeComboBoxRenderer typeComboBoxRenderer;
    private TypeComboBoxModel typeComboBoxModel;  
    
    public TypeCellEditor(EasyguestSession sess, 
        RoomTypeTableModel roomTypeModel ) {
        typeComboBoxRenderer = new TypeComboBoxRenderer();         
        typeComboBoxModel = new TypeComboBoxModel(sess, roomTypeModel); 
        typeComboBox = new JComboBox();
        typeComboBox.setModel(typeComboBoxModel);
        typeComboBox.setRenderer(typeComboBoxRenderer);        
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof JComboBox) {               
            typeComboBox.setSelectedItem((RoomType)value);
        }    
        return typeComboBox;
    }

    public Object getCellEditorValue() {
        return typeComboBox.getSelectedItem();
    }            
    
}
