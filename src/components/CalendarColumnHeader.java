/*
 *CalendarColumnHeader.java
 *
 * Created on 7 janvier 2003, 17:28
 */

package components;

import data.Period;
import data.Tariff;
import util.Util;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import session.EasyguestSession;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class CalendarColumnHeader extends JComponent
        implements MouseListener, MouseMotionListener  {
    private static Log log = LogFactory.getLog(CalendarColumnHeader.class);

    private static String[] tabDays;
    private static String[] tabMonths;

    static {
        tabDays = new String[7];
        for (int i = 1; i <= 7; ++i) {
            tabDays[i-1] = Util.getResource("day-of-week." + i);
        }
        tabMonths = new String[12];
        for (int i = 1; i <= 12; ++i) {
            tabMonths[i-1] = Util.getResource("month." + i);
        }
    }

    private EasyguestSession sess;
    private CalendarView kV;
    private MouseDragger mouseDragger;
    private Period periods[];

    public CalendarColumnHeader(CalendarView calendarView) {
        this.kV = calendarView;
        this.sess = kV.getSession();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public CalendarView getCalendarView() {
        return kV;
    }

    public void invalidate() {
        periods = null;
        repaint();
    }

    public Dimension getPreferredSize() {
        Dimension dim = kV.getPreferredSize();
        dim.height = 5*kV.getCellHeight()+5;
        return dim;
    }

    public void mouseMoved(MouseEvent e) {
        if (kV.isFilterActive()) {
            int x = e.getX();
            int cellWidth = kV.getCellWidth();
            int x1 = kV.xForDate(kV.getFilterMinDate())+cellWidth/2;
            int xr = kV.xForDate(kV.getFilterMaxDate())+cellWidth/2;
            if (x >= x1-4 && x < x1+4) {
                setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
            } else if (x >= xr-4 && x < xr+4) {
                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            } else if (x >= x1 && x < xr) {
//                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                setCursor(Cursor.getDefaultCursor());
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            e.consume();
            int x = e.getX();
            if (kV.isFilterActive()) {
                int cellWidth = kV.getCellWidth();
                int x1 = kV.xForDate(kV.getFilterMinDate())+cellWidth/2;
                int xr = kV.xForDate(kV.getFilterMaxDate())+cellWidth/2;
                if (x >= x1-4 && x < x1+4) {
                    mouseDragger = new MouseDragFilterLeft(this, x);
                } else if (x >= xr-4 && x < xr+4) {
                    mouseDragger = new MouseDragFilterRight(this, x);
                } else if (x >= x1 && x < x1) {
                    // drag whole filter
                }
            }
            if (mouseDragger == null) {
                mouseDragger = new MouseDragFilter(this, x);
            }
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (mouseDragger != null) {
            e.consume();
            mouseDragger.mouseDragged(e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (mouseDragger != null) {
            e.consume();
            mouseDragger.mouseReleased(e.getX(), e.getY());
            mouseDragger = null;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void paintComponent(Graphics g) {
        sess.begin();
        try {
            int width = getWidth();
            int height = getHeight();
            int cellWidth = kV.getCellWidth();
            int cellHeight = kV.getCellHeight();
            Date firstDate = kV.getFirstDate();
            int left = 0;
            int right = left + width;
            int top = 0;
            int bottom = top + height;
            int x = left;
            Date today = Util.today();
            paintLegendPeriod(g, left, top, cellHeight);
            paintPeriods(g);
            Date date = firstDate;
            int month = Util.month(date);
            int year = Util.year(date);
            int lastMonthPos = x;
            while (x < right) {
                int d = Util.day(date);
                int dw = Util.dayOfWeek(date);
                int yt = top;
                yt += cellHeight+1;
                if (date.equals(today)) {
                    g.setColor(kV.getTodayColor());
                    g.fillRect(x, top+2*(cellHeight+1), cellWidth+1, 3*(cellHeight+1));
                } else if (dw == Util.SATURDAY || dw == Util.SUNDAY) {
                    // weekend colors
                    g.setColor(kV.getWeekendColor());
                    g.fillRect(x, top+3*(cellHeight+1), cellWidth+1, 2*(cellHeight+1));
                }

                g.setColor(Color.black);
                yt += cellHeight+1;
                CalendarView.drawCentered(g, x, yt, cellWidth, cellHeight, Integer.toString(d));
                yt += cellHeight+1;
                CalendarView.drawCentered(g, x, yt, cellWidth, cellHeight, tabDays[dw-1].substring(0,1));
                x += cellWidth;
                date = Util.addDays(date, 1);

                // month
                int m = Util.month(date);
                int y = Util.year(date);
                if (m == month) {
                    g.setColor(Color.gray);
                    g.drawLine(x, top+2*(cellHeight+1), x, bottom);
                    ++x;
                } else {
                    int ym = top+cellHeight+1;
                    g.setColor(Color.gray);
                    g.drawLine(x, ym, x, bottom);
                    ++x;
                    String s = tabMonths[month-1] + " " + year;
                    g.setColor(Color.black);
                    CalendarView.drawCentered(g, lastMonthPos, ym, x-lastMonthPos-1, cellHeight, s);
                    lastMonthPos = x;
                    month = m;
                    year = y;
                }
            }
            String s = tabMonths[month-1] + " " + year;
            g.setColor(Color.black);
            CalendarView.drawCentered(g, lastMonthPos, top+(cellHeight+1), right-lastMonthPos, cellHeight, s);
            if (mouseDragger != null) {
                mouseDragger.drawFeedback(g);
            } else if (kV.isFilterActive()) {
                Date dateMin = kV.getFilterMinDate();
                Date dateMax = kV.getFilterMaxDate();
                int x1 = Util.daysBetween(kV.getFirstDate(), dateMin)*(cellWidth+1)+cellWidth/2;
                int w = Util.daysBetween(dateMin, dateMax)*(cellWidth+1);
                paintCursorFilter(g, x1, w);
            }
            g.setColor(Color.black);
            int y = top;
            y += cellHeight;
            g.drawLine(left, y, right, y);
            y += cellHeight+1;
            g.drawLine(left, y, right, y);
            y += cellHeight+1;
            g.drawLine(left, y, right, y);
            y += cellHeight+1;
            g.drawLine(left, y, right, y);
             y += cellHeight+1;
            g.drawLine(left, y, right, y);
            ++y;
        } finally {
            sess.end();
        }
    }

    public void filter(int n1, int n2) {
        if (n1 == n2) {
            kV.clearFilter();
        } else {
            int cellWidth = kV.getCellWidth();
            int cellHeight = kV.getCellHeight();
            Date firstDate = kV.getFirstDate();
            int left = n1 > n2 ? n2 : n1;
            int right = n1 < n2 ? n2 : n1;
            int x1 = left;
            int x2 = (right-left);
            Date minTime = Util.addDays(firstDate, + x1);
            Date maxTime = Util.addDays(minTime, x2);
            kV.setFilter(minTime, maxTime);
        }
    }

    void paintCursorFilter(Graphics g, int x, int w) {
        int cellHeight = kV.getCellHeight();
        int y = 4*(cellHeight+1);
        Color color = Color.blue;
        g.setColor(color);
        g.fillRect(x-2, y+2, 4, cellHeight-4);
        g.fillRect(x+2, y+cellHeight/2-2,  w-4, 4);
        g.fillRect(x+w-2, y+2, 4, cellHeight-4);
    }

    public void paintLegendPeriod(Graphics g, int x, int y, int h) {
        int cellWidth = kV.getCellWidth();
        int cellHeight = kV.getCellHeight();
        Collection col = sess.getExtent(Tariff.class);
        for (Iterator it = col.iterator(); it.hasNext(); ) {
            Tariff tariff = (Tariff)it.next();
            String text = tariff.getName() + " (" + (int)(100*tariff.getFactor()) + "%)";
            FontMetrics fm = g.getFontMetrics();
            int wt = fm.stringWidth(text);
            CalendarView.drawCenteredVertical(g, x+cellWidth, y, h, text);
            Color oldColor = g.getColor();
            g.setColor(tariff.getColor());
            g.fillRect((x+cellWidth)+(wt+3), y+2, 12, 12);
            g.setColor(Color.black);
            g.drawRect((x+cellWidth)+(wt+3), y+2, 12, 12);
            g.setColor(oldColor);
            x += (wt+3) + 12 + cellWidth;
        } 
    }

    private void paintPeriods(Graphics g) {
        if (sess != null) {
            int cellWidth = kV.getCellWidth();
            int cellHeight = kV.getCellHeight();
            if (periods == null) {
                periods = sess.getPeriods(
                    kV.getFirstDate(), kV.getLastDate());
            }
            int yt = 2*(cellHeight+1);
            for (int i = 0; i < periods.length; ++i) {
                Period period = periods[i];
                if (log.isDebugEnabled()) {
                    log.debug(period.getFromDate() + " - " + period.getToDate());
                }
                if (period.getTariff() != null) {
                    int d1 = Util.daysBetween(kV.getFirstDate(), period.getFromDate());
                    int x1 = d1*(cellWidth+1);
                    int d2 = Util.daysBetween(kV.getFirstDate(), period.getToDate());
                    int x2 = (d2+1)*(cellWidth+1);
                    g.setColor(period.getTariff().getColor());
                    g.fillRect(x1, yt, x2-x1, cellHeight);
                }
            }
        }
    }
}
