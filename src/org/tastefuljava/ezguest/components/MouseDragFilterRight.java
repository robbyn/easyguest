package org.tastefuljava.ezguest.components;
import java.awt.Graphics;

public class MouseDragFilterRight implements MouseDragger {
    private final CalendarColumnHeader cch;
    private final CalendarView kv;
    private final int xl;
    private int xr;

    public MouseDragFilterRight(CalendarColumnHeader cch, int x) {
        this.cch = cch;
        kv = cch.getCalendarView();
        xr = x/(kv.getCellWidth()+1);
        xl = kv.indexForDate(kv.getFilterMinDate());
    }

    @Override
    public void mouseDragged(int x, int y) {
        xr = x/(kv.getCellWidth()+1);
        if (xl > xr) {
            xr = xl;
        }
        cch.repaint();
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (xl == xr) {
            kv.clearFilter();
        } else {
            kv.setFilter(kv.dateForIndex(xl), kv.dateForIndex(xr));
        }
    }

    @Override
    public void drawFeedback(Graphics g) {
        int cellWidth = kv.getCellWidth();
        int xxl = xl*(cellWidth+1) + cellWidth/2;
        int w = (xr-xl)*(cellWidth+1);
        cch.paintCursorFilter(g, xxl, w);
    }
}
