package org.tastefuljava.ezguest.components;

import java.awt.AWTEvent;
import java.awt.Point;
import java.util.Arrays;
import org.tastefuljava.ezguest.session.EasyguestSession;
import org.tastefuljava.ezguest.data.Reservation;
import org.tastefuljava.ezguest.data.Room;
import org.tastefuljava.ezguest.data.Customer;
import org.tastefuljava.ezguest.data.Invoice;
import org.tastefuljava.ezguest.gui.ReservationDialog;
import org.tastefuljava.ezguest.util.Util;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class CalendarView extends JComponent implements Scrollable,
                                                MouseListener, MouseMotionListener {
    private static final Log LOG = LogFactory.getLog(CalendarView.class);

    private int cellWidth = 22;
    private int cellHeight = 18;
    private int headerHeight = 36;
    private Color weekendColor = new Color(250, 236, 200);
    private Color todayColor = new Color(250, 150, 150);
    private Color filterColor = new Color(204, 255, 255);
    private Color linesColor = new Color(192, 192, 192);
    private Date firstDate;
    private EasyguestSession sess;
    private Room rooms[];
    private Reservation[] reservations;
    private CalendarColumnHeader columnHeader;
    private CalendarRowHeader rowHeader;
    private int xpos, oxpos, oypos;
    private MouseDragger mouseDragger = null;
    private boolean filterActive = false;
    private Date filterMinDate;
    private Date filterMaxDate;
    private boolean showRoomType;
    private boolean showRoomPrice;
    private final Color[] statusColor = new Color[Reservation.STATUS_COUNT];

    public CalendarView() {
        firstDate = Util.today();
        initialize();
    }

    private void initialize() {
        addMouseListener(this);
        addMouseMotionListener(this);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK|AWTEvent.MOUSE_MOTION_EVENT_MASK);
        setToolTipText("");
    }

    public int getRowCount() {
        return rooms == null ? 120 : rooms.length;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public Room getRoom(int index) {
        return rooms[index];
    }

    public int getRoomIndex(Room room) {
        if (rooms != null) {
            for (int i = 0; i < rooms.length; ++i) {
                if (rooms[i].getId() == room.getId()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public CalendarColumnHeader getColumnHeader() {
        if (columnHeader == null) {
            setSession(sess);
            columnHeader = new CalendarColumnHeader(this);
        }
        return columnHeader;
    }

    public CalendarRowHeader getRowHeader() {
        if (rowHeader == null) {
            rowHeader = new CalendarRowHeader(this);
        }
        return rowHeader;
    }

    public Date getFirstDate() {
        return (Date)firstDate.clone();
    }

    public Date getLastDate() {
        int days = (getWidth()+cellWidth)/(cellWidth+1);
        return Util.addDays(firstDate, days);
    }

    public void setDate(int year, int month, int day) {
        firstDate = Util.makeDate(year, month, day);
        clearReservations();
        System.out.println(firstDate.getTime());
        repaint();
        if (columnHeader != null) {
            columnHeader.invalidate();
        }
    }

    public void nextDay() {
        reservations = null;
        firstDate = Util.addDays(firstDate, 1);
        repaint();
        if (columnHeader != null) {
            columnHeader.invalidate();
        }
    }

    public void previousDay() {
        reservations = null;
        firstDate = Util.addDays(firstDate, -1);
        repaint();
        if (columnHeader != null) {
            columnHeader.invalidate();
        }
    }

    public void nextMonth() {
        reservations = null;
        firstDate = Util.addMonths(firstDate, 1);
        repaint();
        if (columnHeader != null) {
            columnHeader.invalidate();
        }
    }

    public void previousMonth() {
        reservations = null;
        firstDate = Util.addMonths(firstDate, -1);
        repaint();
        if (columnHeader != null) {
            columnHeader.invalidate();
        }
    }

    public void nextWeek() {
        reservations = null;
        firstDate = Util.addDays(firstDate, 7);
        repaint();
        if (columnHeader != null) {
            columnHeader.invalidate();
        }
    }

    public void previousWeek() {
        reservations = null;
        firstDate = Util.addDays(firstDate, -7);
        repaint();
        if (columnHeader != null) {
            columnHeader.invalidate();
        }
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int newValue) {
        cellWidth = newValue;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(int newValue) {
        cellHeight = newValue;
    }

    public int getHeaderHeight() {
        return headerHeight;
    }

    public void setHeaderHeight(int newValue) {
        headerHeight = newValue;
    }

    public Color getWeekendColor() {
        return weekendColor;
    }

    public void setWeekendColor(Color newValue) {
        weekendColor = newValue;
        repaint();
    }

    public Color getTodayColor() {
        return todayColor;
    }

    public void setTodayColor(Color newValue) {
        todayColor = newValue;
        repaint();
    }

    public Color getFilterColor() {
        return filterColor;
    }

    public void setFilterColor(Color newValue) {
        filterColor = newValue;
        if (filterActive) {
            repaint();
        }
    }

    public Color getLinesColor() {
        return linesColor;
    }

    public void setLinesColor(Color newValue) {
        linesColor = newValue;
        repaint();
    }

    public boolean getShowRoomType() {
        return showRoomType;
    }

    public void setShowRoomType(boolean newValue) {
        showRoomType = newValue;
        if (rowHeader != null) {
            rowHeader.repaint();
        }
    }

    public boolean getShowRoomPrice() {
        return showRoomPrice;
    }

    public void setShowRoomPrice(boolean newValue) {
        showRoomPrice = newValue;
        if (rowHeader != null) {
            rowHeader.repaint();
        }
    }

    public boolean isFilterActive() {
        return filterActive;
    }

    public Date getFilterMinDate() {
        return filterMinDate;
    }

    public Date getFilterMaxDate() {
        return filterMaxDate;
    }

    public EasyguestSession getSession() {
        return sess;
    }

    public void setSession(EasyguestSession newValue) {
        sess = newValue;
        sess.begin();
        try {
            Collection<Room> col = sess.getHotel().getRooms();//           
            rooms = col.toArray(new Room[col.size()]);
            Arrays.sort(rooms);
        } finally {
            sess.end();
        }
        updateSize();
    }

    public void clearReservations() {
        reservations = null;
    }

    @Override
    public void invalidate() {
        clearReservations();
    }

    public void updateSize() {
        revalidate();
        if (columnHeader != null) {
            columnHeader.invalidate();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int rowCount = getRowCount();
        Dimension dim = new Dimension(getWidth(), rowCount*(cellHeight+1));
        if (getParent() instanceof JViewport) {
            Component p = getParent().getParent();
            if (p instanceof JScrollPane) {
                dim.width = ((JScrollPane)p).getViewportBorderBounds().width;
            }
        }
        return dim;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width/2;
        } else {
            return (visibleRect.height-headerHeight-1)/2;
        }
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return cellWidth+1;
        } else {
            return cellHeight+1;
        }
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public final Date dateForIndex(int index) {
        return Util.addDays(firstDate, index);
    }

    public final Date dateForX(int x) {
        return dateForIndex(x/(cellWidth+1));
    }

    public final int indexForDate(Date date) {
        return Util.daysBetween(firstDate, date);
    }

    public int xForDate(Date date) {
        return indexForDate(date)*(cellWidth+1);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            e.consume();
            mouseDragger = null;
            int x = e.getX();
            int y = e.getY();
            requireReservations();
            for (int i = reservations.length-1; mouseDragger == null && i >= 0; --i) {
                Reservation res = reservations[i];
                Rectangle rc = reservationBounds(res);
                if (y >= rc.y && y < rc.y+rc.height) {
                    if (x < rc.x-4 || x >= rc.x+rc.width+4) {
                        /* nothing */
                    } else if (x < rc.x+4) {
                        mouseDragger = new MouseDragReservationBarLeft(this, res, x, y);
                    } else if (x >= rc.x+rc.width-4) {
                        mouseDragger = new MouseDragReservationBarRight(this, res, x, y);
                    } else {
                        mouseDragger = new MouseDragDropReservationBar(this, res, x, y);
                    }
                }
            }
            if (mouseDragger == null) {
                mouseDragger = new MouseDragReservationBar(this, x, y);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        e.consume();
        if (mouseDragger != null) {
            mouseDragger.mouseDragged(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        e.consume();
        if (mouseDragger != null) {
            mouseDragger.mouseReleased(e.getX(), e.getY());
            mouseDragger = null;
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        requireReservations();
        for (int i = reservations.length-1; i >= 0; --i) {
            Reservation res = reservations[i];
            Rectangle rc = reservationBounds(res); 
            if (y >= rc.y && y < rc.y+rc.height) {
                if (x < rc.x-4 || x >= rc.x+rc.width+4) {
                    /* nothing */
                } else if (x < rc.x+4) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                    return;
                } else if (x >= rc.x+rc.width-4) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    return;
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    return;
                }
            }
        }
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            e.consume();
            if (e.getClickCount() == 2) {
                Reservation reservation = getReservationAt(e.getX(), e.getY());
                if (reservation != null) {
                    ReservationDialog reservationDialog
                            = new ReservationDialog(getFrame(), sess,
                                    reservation);
                    reservationDialog.setVisible(true);
                }
            }
            repaint();
        }
    }

    public Reservation getReservationAt(int x, int y) {
        requireReservations();
        for (int i = 0; i < reservations.length; ++i) {
            Reservation reservation = reservations[i];
            Rectangle rect = reservationBounds(reservation);
            if (rect.contains(x, y)) {
                return reservation;
            }
        }
        return null;
    }

    public void setFilter(Date fromDate, Date toDate) {
       if (sess != null) {
           long minTime = firstDate.getTime();
           filterActive = true;
           filterMinDate = fromDate;
           filterMaxDate = toDate;
           sess.begin();
           try {
               rooms = sess.getFreeRooms(sess.getHotel(), fromDate, toDate);
           } finally {
               sess.end();
           }
           revalidate();
           repaint();
           if (rowHeader != null) {
               rowHeader.repaint();
           }
       }
    }

    public boolean isFree(Reservation cur, int roomIndex, int xl, int xr) {        
        if (roomIndex >= rooms.length) {
            return false;
        }
        Room room = rooms[roomIndex];
        requireReservations();
        Date fromDate = dateForIndex(xl);
        Date toDate = dateForIndex(xr);
        for (int i = 0; i < reservations.length; ++i) {
            Reservation res = reservations[i];
            if (res != cur
                    && res.getStatus() != Reservation.STATUS_CANCELED
                    && room == res.getRoom()
                    && fromDate.before(res.getToDate())
                    && toDate.after(res.getFromDate())) {
                return false;
            }
        }
        return true;
    }

    public void clearFilter() {
        filterActive = false;
        sess.begin();
        try {
            Collection<Room> col = sess.getHotel().getRooms();
            rooms = col.toArray(new Room[col.size()]);
            Arrays.sort(rooms);
        } finally {
            sess.end();
        }
        revalidate();
        repaint();
        if (rowHeader != null) {
            rowHeader.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Date today = Util.today();
        Date date = firstDate;

        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);

        for (int x = 0; x < width; x += cellWidth+1) {
            if (date.equals(today)) {
                g.setColor(todayColor);
                g.fillRect(x, 0, cellWidth+1, height);
            } else {
                int dw = Util.dayOfWeek(date);
                if (dw == Util.SATURDAY || dw == Util.SUNDAY) {
                    g.setColor(weekendColor);
                    g.fillRect(x, 0, cellWidth+1, height);
                }
            }
            date = Util.addDays(date, 1);
        }

        if (filterActive) {
            int x = Util.daysBetween(firstDate, filterMinDate)*(cellWidth+1)+cellWidth/2;
            int w = Util.daysBetween(filterMinDate, filterMaxDate)*(cellWidth+1);
            g.setColor(filterColor);
            g.fillRect(x, 0, w, height);
        }

        g.setColor(linesColor);
        for (int x = cellWidth; x < width; x += cellWidth+1) {
            g.drawLine(x, 0, x, height);
        }

        for (int y = cellHeight; y < height; y += cellHeight+1) {
            g.drawLine(0, y, width, y);
        }

        paintReservations(g);
        if (mouseDragger != null) {
            mouseDragger.drawFeedback(g);
        }
    }

    protected static void drawCentered(Graphics g, int x, int y, int w, int h,
            String text) {
        FontMetrics fm = g.getFontMetrics();
        int ascent = fm.getMaxAscent();
        int ht = ascent + fm.getMaxDescent();
        int wt = fm.stringWidth(text);
        if (h >= ht && w >= wt) {
            x += (w-wt)/2;
            y += (h-ht)/2;
            g.drawString(text, x, y+ascent);
        }
    }

    protected static void drawCenteredVertical(Graphics g, int x, int y, int h,
            String text) {
        FontMetrics fm = g.getFontMetrics();
        int ascent = fm.getMaxAscent();
        int ht = ascent + fm.getMaxDescent();
        if (h >= ht) {
            y += (h-ht)/2;
            g.drawString(text, x, y+ascent);
            y -= (h-ht)/2;
        }
    }

    private void requireReservations() {
        if (reservations == null) {
            int days = (getWidth()+cellWidth)/(cellWidth+1);
            Date maxTime = Util.addDays(firstDate, days);
            sess.begin();
            try {
                reservations = sess.getReservations(firstDate, maxTime);
            } finally {
                sess.end();
            }
        }
    }

    public void setStatusColor(int status, Color color) {        
        statusColor[status] = color; 
    }

    private void paintReservations(Graphics g) {
        if (sess != null) {
            requireReservations();
            for (int i = 0; i < reservations.length; ++i) {
                Reservation reservation = reservations[i];
                Rectangle rect = reservationBounds(reservation);
                int status = reservation.getStatus();
                Color barColor = statusColor[Reservation.STATUS_CANCELED];
                Color textColor = Color.blue;
                switch(status) {
                    case Reservation.STATUS_RESERVED:
                        barColor = statusColor[Reservation.STATUS_RESERVED];
                        break;
                    case Reservation.STATUS_OCCUPIED:
                        barColor = statusColor[Reservation.STATUS_OCCUPIED];
                        break;
                    case Reservation.STATUS_RELEASED:
                        barColor = statusColor[Reservation.STATUS_RELEASED];
                        break;
                }
                g.setColor(barColor);
                g.fillRect(rect.x, rect.y, rect.width, rect.height);
                g.setColor(Color.black);
                g.drawRect(rect.x, rect.y, rect.width-1, rect.height-1);
                g.setColor(textColor);
                drawCentered(g, rect.x+1, rect.y+1, rect.width-2, rect.height-2,
                        reservationText(reservation));
            }
        }
    }

    protected Rectangle reservationBounds(Reservation res) {
        int d1 = Util.daysBetween(firstDate, res.getFromDate());
        int d2 = Util.daysBetween(firstDate, res.getToDate());
        int x1 = d1*(cellWidth+1) + cellWidth/2;
        int x2 = d2*(cellWidth+1) + cellWidth/2;
        int row = getRoomIndex(res.getRoom());
        int yt = row*(cellHeight+1);
        return new Rectangle(x1, yt+2, x2-x1, cellHeight-4);
    }

    private String reservationText(Reservation res) {
        Invoice invoice = res.getInvoice();
        if (invoice == null) {
            return "";
        } else {
            Customer cust = invoice.getCustomer();
            StringBuilder buf = new StringBuilder();
            String s = cust.getFirstName();
            if (s != null) {
                buf.append(' ');
                buf.append(s);
            }
            s = cust.getLastName();
            if (s != null) {
                buf.append(' ');
                buf.append(s);
            }
            s = cust.getCompany();
            if (s != null) {
                buf.append(' ');
                buf.append(s);
            }
            return buf.substring(1);
        }
    }

    private String reservationTooltip(Reservation res) {
        StringBuilder buf = new StringBuilder();
        Invoice invoice = res.getInvoice();
        if (invoice != null) {
            Customer cust = invoice.getCustomer();
            String fn = cust.getFirstName();
            String ln = cust.getLastName();
            if (fn != null) {
                buf.append(fn);
                if (ln != null) {
                    buf.append(' ');
                }
            }
            if (ln != null) {
                buf.append(ln);
            }
            if (fn != null && ln != null) {
                buf.append('\n');
            }
            String cy = cust.getCompany();
            if (cy != null) {
                buf.append(cy);
                buf.append('\n');
            }
        }
        buf.append(Util.getResource("reservation.column.from-date"));
        buf.append(": ");
        buf.append(Util.date2str(res.getFromDate()));
        buf.append('\n');
        buf.append(Util.getResource("reservation.column.to-date"));
        buf.append(": ");
        buf.append(Util.date2str(res.getToDate()));
        return buf.toString();
    }

    public Frame getFrame() {
        for (Container c = getParent(); c != null; c = c.getParent()) {
            if (c instanceof Frame) {
                return (Frame)c;
            }
        }
        return null;
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
        Reservation res = getReservationAt(event.getX(), event.getY());
        if (res != null) {
            Rectangle rc = reservationBounds(res);
            return new Point(rc.x, rc.y+rc.height);
        }
        return null;
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        Reservation res = getReservationAt(event.getX(), event.getY());
        if (res != null) {
            sess.begin();
            try {
                res = sess.getObjectById(Reservation.class, res.getId());
                if (res != null) {
                    return reservationTooltip(res);
                }
            } finally {
                sess.end();
            }
        }
        return null;
    }
}
