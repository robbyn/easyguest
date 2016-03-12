package org.tastefuljava.ezguest.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class ColorIcon implements Icon {
    private Color color;

    public ColorIcon(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newValue) {
        color = newValue;
    }

    @Override
    public int getIconWidth() {
        return 12;
    }

    @Override
    public int getIconHeight() {
        return 12;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillRect(x, y, 12, 12);
        g.setColor(Color.black);
        g.drawRect(x, y, 12, 12);
        g.setColor(oldColor);
    }
}
