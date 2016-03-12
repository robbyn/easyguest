package org.tastefuljava.ezguest.components;
import java.awt.Graphics;

public interface MouseDragger {
    public void mouseDragged(int x, int y);
    public void mouseReleased(int x, int y);
    public void drawFeedback(Graphics g);
}
