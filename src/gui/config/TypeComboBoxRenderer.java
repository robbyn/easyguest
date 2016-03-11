/*
 * TypeComboBoxRenderer.java
 *
 * Created on 2 december 2002, 14:10
 */
package gui.config;

import data.RoomType;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 *
 * @author  denis
 */
public class TypeComboBoxRenderer implements ListCellRenderer {
       
    private JLabel label = new JLabel();

    public TypeComboBoxRenderer() {
       label.setOpaque(true);
    } 
    
    public Component getListCellRendererComponent(JList list, Object value,
           int index, boolean isSelected, boolean hasFocus) {       
        if (isSelected) {
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            label.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }
        if (value instanceof RoomType) {
            RoomType roomType = (RoomType)value;
            label.setText(roomType.getName() + " (" + roomType.getBasePrice() + ")");
        } else {
            label.setText("");
        }
        return label;
    }        
}
