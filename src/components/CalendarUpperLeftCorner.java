/*
 * Corner.java
 *
 * Created on 8 janvier 2003, 02:43
 */

package components;

import util.Util;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class CalendarUpperLeftCorner extends JComponent {
    private CalendarView kV;

    public CalendarUpperLeftCorner(CalendarView calendarView) {
        this.kV = calendarView;
    }

    public void paintComponent(Graphics g) {
        int cellWidth = kV.getCellWidth();
        int cellHeight = kV.getCellHeight();
        int width = getWidth();
        int height = getHeight();
        int left = 0;
        int right = left + width;
        int top = 0;
        int bottom = top + height;
        int x = left;
        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        int y = top;
        CalendarView.drawCenteredVertical(g, x+7, y, cellHeight,
                Util.getResource("header.upper_left_corner.periods"));
        y += cellHeight;
        g.drawLine(left, y, right, y);
        ++y;
        CalendarView.drawCenteredVertical(g, x+7, y, cellHeight,
                Util.getResource("header.upper_left_corner.month_year"));
        y += cellHeight;
        g.drawLine(left, y, right, y);
        ++y;
        CalendarView.drawCenteredVertical(g, x+7, y, cellHeight,
                Util.getResource("header.upper_left_corner.daymonth"));
        y += cellHeight;
        g.drawLine(left, y, right, y);
        ++y;
        CalendarView.drawCenteredVertical(g, x+7, y, cellHeight,
                Util.getResource("header.upper_left_corner.dayweek"));
        y += cellHeight;
        g.drawLine(left, y, right, y);
        ++y;
        CalendarView.drawCenteredVertical(g, x+7, y, cellHeight+1,
                Util.getResource("header.upper_left_corner.filter"));
        y += cellHeight;
        g.drawLine(left, y, right, y);
        ++y;
        g.drawLine(right-1, top, right-1, bottom);
    }
}


