/*
 * RubricRenderer.java
 *
 * Created on 20 february 2003, 16:16
 */

package org.tastefuljava.ezguest.gui;

import java.awt.Component;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;
import org.tastefuljava.ezguest.util.Util;

/**
 * @author  denis
 */
@SuppressWarnings("serial")
public class RubricListCellRenderer extends DefaultListCellRenderer {
    private static Map<String,ImageIcon> iconCache
            = new HashMap<String,ImageIcon>();
    private JLabel label = new JLabel();

    public static ImageIcon getIcon(String name) {
        ImageIcon icon = iconCache.get(name);
        if (icon == null) {
            String descr = Util.getResource("main.tab." + name);
            URL url = RubricListCellRenderer.class.getResource("/org/tastefuljava/ezguest/images/" + name + ".png");
            icon = new ImageIcon(url, descr);
            iconCache.put(name, icon);
        }
        return icon;
    }

    public RubricListCellRenderer() {        
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(new EmptyBorder(4, 4, 4, 4));
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            label.setOpaque(true);
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            label.setOpaque(false);
            label.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }
        ImageIcon icon = value == null ? null : getIcon((String)value);
        label.setText(icon == null ? "" : icon.getDescription());
        label.setIcon(icon == null ? null : icon);
        return label;     
    }    
}
