/*
 * Corner.java
 *
 * Created on 8 janvier 2003, 02:43
 */

package components;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class CalendarUpperRightCorner extends JComponent {
    private CalendarView kV;
    private int cellWidth;
    private int cellHeight;
    private int rowWidth;
    public static final int SIZE = 0;


    public CalendarUpperRightCorner(CalendarView calendarView) {
        this.kV = calendarView;
        this.cellWidth = kV.getCellWidth();
        this.cellHeight = kV.getCellHeight();
    }

    public void paintComponent(Graphics g) {
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
        g.drawLine(x, top+cellHeight, x, bottom);
    }
}


