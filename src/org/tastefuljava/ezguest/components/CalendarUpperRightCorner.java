package org.tastefuljava.ezguest.components;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class CalendarUpperRightCorner extends JComponent {
    private final CalendarView kV;
    private final int cellHeight;
    public static final int SIZE = 0;


    public CalendarUpperRightCorner(CalendarView calendarView) {
        this.kV = calendarView;
        this.cellHeight = kV.getCellHeight();
    }

    @Override
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int left = 0;
        int top = 0;
        int bottom = top + height;
        int x = left;
        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        g.drawLine(x, top+cellHeight, x, bottom);
    }
}

