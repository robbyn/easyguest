package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.components.ColorIcon;
import org.tastefuljava.ezguest.data.Tariff;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class TariffComboBoxRenderer implements ListCellRenderer {   
    private final JLabel label = new JLabel();

    public TariffComboBoxRenderer() {
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
        Tariff tariff = (Tariff)value;
        if (tariff == null) {
            label.setIcon(null);
            label.setText("");
        } else {
            label.setText(tariff.getName() + " (" + tariff.getFactor() + ")");
            label.setIcon(new ColorIcon(tariff.getColor()));
        }
        return label;
    }        
}
