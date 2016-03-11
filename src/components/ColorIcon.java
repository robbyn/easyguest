/*
 * ColorIcon.java
 *
 * Created on 04 January 2002, 17:29
 */

package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 *
 * @author  maurice
 */
public class ColorIcon implements Icon {
    private Color color;

    /** Creates a new instance of ColorIcon */
    public ColorIcon(Color color) {
        this.color = color;
    }


    public Color getColor() {
        return color;
    }


    public void setColor(Color newValue) {
        color = newValue;
    }


    public int getIconWidth() {
        return 12;
    }


    public int getIconHeight() {
        return 12;
    }


    public void paintIcon(Component c, Graphics g, int x, int y) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillRect(x, y, 12, 12);
        g.setColor(Color.black);
        g.drawRect(x, y, 12, 12);
        g.setColor(oldColor);
    }
}
