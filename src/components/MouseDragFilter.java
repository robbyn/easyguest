/*
 * MouseDragFilter.java
 *
 * Created on 14 March 2003, 23:42
 */

package components;
import java.awt.Graphics;

/**
 *
 * @author  Maurice Perry
 */
public class MouseDragFilter implements MouseDragger {
    private CalendarColumnHeader cch;
    private CalendarView kv;
    private int xo, xp;

    public MouseDragFilter(CalendarColumnHeader cch, int x) {
        this.cch = cch;
        kv = cch.getCalendarView();
        xo = x/(kv.getCellWidth()+1);
        xp = xo;
    }

    public void mouseDragged(int x, int y) {
        xp = x/(kv.getCellWidth()+1);
        cch.repaint();
    }

    public void mouseReleased(int x, int y) {
        if (xp == xo) {
            kv.clearFilter();
        } else {
            int xl = xo < xp ? xo : xp;
            int xr = xo > xp ? xo : xp;
            kv.setFilter(kv.dateForIndex(xl), kv.dateForIndex(xr));
        }
    }

    public void drawFeedback(Graphics g) {
        int cellWidth = kv.getCellWidth();
        int xl = (xo < xp ? xo : xp)*(cellWidth+1) + cellWidth/2;
        int w = (xo < xp ? xp-xo : xo-xp)*(cellWidth+1);
        cch.paintCursorFilter(g, xl, w);
    }
}
