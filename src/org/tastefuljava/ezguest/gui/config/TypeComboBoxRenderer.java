package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.data.RoomType;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class TypeComboBoxRenderer implements ListCellRenderer {
    private final JLabel label = new JLabel();

    public TypeComboBoxRenderer() {
       label.setOpaque(true);
    } 
    
    @Override
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
