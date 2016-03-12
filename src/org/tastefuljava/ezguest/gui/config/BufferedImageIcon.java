package org.tastefuljava.ezguest.gui.config;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

public class BufferedImageIcon implements Icon {
    private final int width;
    private final int height;
    private final BufferedImage image;

    public BufferedImageIcon(BufferedImage image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void paintIcon(Component comp, Graphics g, int x, int y) {
        if (image != null) {
            int w = width;
            int h = height;
            if (image.getWidth()*height < image.getHeight()*width) {
                w = image.getWidth()*height/image.getHeight();
                x = (width-w)/2;
            } else {
                h = image.getHeight()*width/image.getWidth();
                y = (height-h)/2;
            }
            g.drawImage(image, x, y, w, h, comp);
        }
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }   
}
