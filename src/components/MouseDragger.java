/*
 * MouseDragger.java
 *
 * Created on 24 January 2003, 14:55
 */

package components;
import java.awt.Graphics;

/**
 *
 * @author  Maurice Perry
 */
public interface MouseDragger {
    public void mouseDragged(int x, int y);
    public void mouseReleased(int x, int y);
    public void drawFeedback(Graphics g);
}
