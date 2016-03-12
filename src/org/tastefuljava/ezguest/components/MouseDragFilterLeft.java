/*
 * MouseDragFilter.java
 *
 * Created on 14 March 2003, 23:42
 */

package org.tastefuljava.ezguest.components;
import java.awt.Graphics;

/**
 *
 * @author  Maurice Perry
 */
public class MouseDragFilterLeft implements MouseDragger {
    private CalendarColumnHeader cch;
    private CalendarView kv;
    private int xl, xr;

    public MouseDragFilterLeft(CalendarColumnHeader cch, int x) {
        this.cch = cch;
        kv = cch.getCalendarView();
        xl = x/(kv.getCellWidth()+1);
        xr = kv.indexForDate(kv.getFilterMaxDate());
    }

    public void mouseDragged(int x, int y) {
        xl = x/(kv.getCellWidth()+1);
        if (xl > xr) {
            xl = xr;
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
