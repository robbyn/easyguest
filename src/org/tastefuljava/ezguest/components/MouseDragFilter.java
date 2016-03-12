package org.tastefuljava.ezguest.components;
import java.awt.Graphics;

public class MouseDragFilter implements MouseDragger {
    private final CalendarColumnHeader cch;
    private final CalendarView kv;
    private final int xo;
    private int xp;

    public MouseDragFilter(CalendarColumnHeader cch, int x) {
        this.cch = cch;
        kv = cch.getCalendarView();
        xo = x/(kv.getCellWidth()+1);
        xp = xo;
    }

    @Override
    public void mouseDragged(int x, int y) {
        xp = x/(kv.getCellWidth()+1);
        cch.repaint();
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (xp == xo) {
            kv.clearFilter();
        } else {
            int xl = xo < xp ? xo : xp;
            int xr = xo > xp ? xo : xp;
            kv.setFilter(kv.dateForIndex(xl), kv.dateForIndex(xr));
        }
    }

    @Override
    public void drawFeedback(Graphics g) {
        int cellWidth = kv.getCellWidth();
        int xl = (xo < xp ? xo : xp)*(cellWidth+1) + cellWidth/2;
        int w = (xo < xp ? xp-xo : xo-xp)*(cellWidth+1);
        cch.paintCursorFilter(g, xl, w);
    }
}
