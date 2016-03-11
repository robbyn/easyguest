/*
 * BufferedImageIcon.java
 *
 * Created on September 18, 2007, 10:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gui.config;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

/**
 *
 * @author maurice
 */
public class BufferedImageIcon implements Icon {
    private int width;
    private int height;
    private BufferedImage image;

    public BufferedImageIcon(BufferedImage image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

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

    public int getIconWidth() {
        return width;
    }

    public int getIconHeight() {
        return height;
    }   
}
