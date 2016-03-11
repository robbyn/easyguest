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
public class MouseDragFilterRight implements MouseDragger {
    private CalendarColumnHeader cch;
    private CalendarView kv;
    private int xl, xr;

    public MouseDragFilterRight(CalendarColumnHeader cch, int x) {
        this.cch = cch;
        kv = cch.getCalendarView();
        xr = x/(kv.getCellWidth()+1);
        xl = kv.indexForDate(kv.getFilterMinDate());
    }

    public void mouseDragged(int x, int y) {
        xr = x/(kv.getCellWidth()+1);
        if (xl > xr) {
            xr = xl;
        }
        cch.repaint();
    }

    public void mouseReleased(int x, int y) {
        if (xl == xr) {
            kv.clearFilter();
        } else {
            kv.setFilter(kv.dateForIndex(xl), kv.dateForIndex(xr));
        }
    }

    public void drawFeedback(Graphics g) {
        int cellWidth = kv.getCellWidth();
        int xxl = xl*(cellWidth+1) + cellWidth/2;
        int w = (xr-xl)*(cellWidth+1);
        cch.paintCursorFilter(g, xxl, w);
    }
}
