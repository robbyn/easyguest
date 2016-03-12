package org.tastefuljava.ezguest.components;

import org.tastefuljava.ezguest.util.Util;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JColorChooser;

@SuppressWarnings("serial")
public class ColorButton extends JButton {
    private static final String TITLE_KEY
            = "tariffperiods.dialog.tariff.colorchooser.title";
    private static final Map<Color,String> COLOR_KEY = new HashMap<>();


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


    @Override
    protected void fireActionPerformed(ActionEvent event) {
        JColorChooser chooser = new JColorChooser();
        Color color = chooser.showDialog(
                this, Util.getResource(TITLE_KEY), getColor());
        if (color != null) {
            setColor(color);
            super.fireActionPerformed(event);
        }
    }


    public static final String colorToString(Color color) {
        if (color == null) {
            return "null";
        }
        String key = COLOR_KEY.get(color);
        if (key != null) {
            return Util.getResource(key);
        } else {
            return "[" + color.getRed() + "," + color.getGreen() + ","
                    + color.getBlue() + "]";
        }
    }

    static {
        COLOR_KEY.put(Color.white, "color.white");
        COLOR_KEY.put(Color.lightGray, "color.light_gray");
        COLOR_KEY.put(Color.gray, "color.gray");
        COLOR_KEY.put(Color.darkGray, "color.dark_gray");
        COLOR_KEY.put(Color.black, "color.black");
        COLOR_KEY.put(Color.red, "color.red");
        COLOR_KEY.put(Color.pink, "color.pink");
        COLOR_KEY.put(Color.orange, "color.orange");
        COLOR_KEY.put(Color.yellow, "color.yellow");
        COLOR_KEY.put(Color.green, "color.green");
        COLOR_KEY.put(Color.magenta, "color.magenta");
        COLOR_KEY.put(Color.cyan, "color.cyan");
        COLOR_KEY.put(Color.blue, "color.blue");
    }
}
