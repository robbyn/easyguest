/*
 * ColorButton.java
 *
 * Created on 04 January 2002, 17:31
 */

package org.tastefuljava.ezguest.components;

import org.tastefuljava.ezguest.util.Util;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JColorChooser;

/**
 *
 * @author  maurice
 */
@SuppressWarnings("serial")
public class ColorButton extends JButton {
    private static final Map<Color,String> colorKey
            = new HashMap<Color,String>();


    public ColorButton() {
        this(Color.black);
    }


    public ColorButton(Color color) {
        super(colorToString(color), new ColorIcon(color));
    }


    public Color getColor() {
        return ((ColorIcon)getIcon()).getColor();
    }


    public void setColor(Color newValue) {
        ((ColorIcon)getIcon()).setColor(newValue);
        setText(colorToString(newValue));
        repaint();
    }


    protected void fireActionPerformed(ActionEvent event) {
        JColorChooser chooser = new JColorChooser();
        Color color = chooser.showDialog(this, Util.getResource("tariffperiods.dialog.tariff.colorchooser.title"), getColor());
        if (color != null) {
            setColor(color);
            super.fireActionPerformed(event);
        }
    }


    public static final String colorToString(Color color) {
        if (color == null) {
            return "null";
        }
        String key = colorKey.get(color);
        if (key != null) {
            return Util.getResource(key);
        } else {
            return "[" + color.getRed() + "," + color.getGreen() + ","
                    + color.getBlue() + "]";
        }
    }

    static {
        colorKey.put(Color.white, "color.white");
        colorKey.put(Color.lightGray, "color.light_gray");
        colorKey.put(Color.gray, "color.gray");
        colorKey.put(Color.darkGray, "color.dark_gray");
        colorKey.put(Color.black, "color.black");
        colorKey.put(Color.red, "color.red");
        colorKey.put(Color.pink, "color.pink");
        colorKey.put(Color.orange, "color.orange");
        colorKey.put(Color.yellow, "color.yellow");
        colorKey.put(Color.green, "color.green");
        colorKey.put(Color.magenta, "color.magenta");
        colorKey.put(Color.cyan, "color.cyan");
        colorKey.put(Color.blue, "color.blue");
    }
}
