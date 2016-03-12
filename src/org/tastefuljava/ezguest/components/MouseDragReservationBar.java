/*
 * MousDrag.java
 *
 * Created on 28 janvier 2003, 12:03
 */

package org.tastefuljava.ezguest.components;
import org.tastefuljava.ezguest.gui.ReservationDialog;
import java.util.Date;
import java.awt.Graphics;
import java.awt.Color;
import org.tastefuljava.ezguest.util.Util;

/**
 *
 * @author  denis
 */
public class MouseDragReservationBar implements MouseDragger {
    private CalendarView kV;
    private int xo;
    private int xp;
    private int roomIndex;

    public MouseDragReservationBar(CalendarView calendarView, int xo, int yo) {
        this.kV = calendarView;
        this.xo = xo/(kV.getCellWidth()+1);
        this.xp = this.xo;
        this.roomIndex = yo/(kV.getCellHeight()+1);
    }

    public void mouseDragged(int x, int y) {
        int xi = x/(kV.getCellWidth()+1);
        if (xi != xp) {
            int xl = xo < xi ? xo : xi;
            int xr = xo > xi ? xo : xi;
            if (kV.isFree(null, roomIndex, xl, xr)) {
                xp = xi;
                kV.repaint();
            }
        }
    }

    public void mouseReleased(int x, int y) {       
        if (xp != xo) {
            int xl = xo < xp ? xo : xp;
            int xr = xo > xp ? xo : xp;
            Date arrival = Util.addDays(kV.getFirstDate(), xl);
            Date departure = Util.addDays(kV.getFirstDate(), xr);
            ReservationDialog reservationDialog = new ReservationDialog(
                    kV.getFrame(), kV.getSession(), arrival, departure,
                    kV.getRoom(roomIndex));
            reservationDialog.setVisible(true);
            kV.clearReservations();
        }
    }

    public void drawFeedback(Graphics g) {
        int left = xo < xp ? xo : xp;
        int right = xo > xp ? xo : xp;
        int cellWidth = kV.getCellWidth();
        int cellHeight = kV.getCellHeight();
        int x1 = left*(cellWidth+1)+cellWidth/2;
        int x2 = right*(cellWidth+1)+cellWidth/2;
        int y = roomIndex*(cellHeight+1);
        g.setColor(Color.BLUE);
        g.fillRect(x1, y+2, x2-x1, cellHeight-4);
    }
}
