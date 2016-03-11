/*
 * MouseDragReservationBarLeft.java
 *
 * Created on 29 janvier 2003, 00:31
 */

package components;

import data.Reservation;
import util.Util;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Date;
import javax.swing.JOptionPane;
import session.EasyguestSession;

/**
 *
 * @author  denis
 */
public class MouseDragReservationBarLeft implements MouseDragger {
    private CalendarView kV;
    private Reservation res;
    private Rectangle rc;
    private int xr;
    private int xl;
    private int xp;
    private int roomIndex;


    public MouseDragReservationBarLeft(CalendarView calendarView, Reservation res, 
                                        int xo, int yo) {
        this.kV = calendarView;
        this.res = res;
        this.roomIndex = kV.getRoomIndex(res.getRoom());
        this.rc = kV.reservationBounds(res);
        this.xr = (rc.x+rc.width)/(kV.getCellWidth()+1);
        this.xl = rc.x/(kV.getCellWidth()+1);
        this.xp = xl;        
    }

    public void mouseDragged(int x, int y) {
        int xi = x/(kV.getCellWidth()+1);
        if (xi >= xr) {
            xi = xr-1;            
        }
        if (xi != xp && kV.isFree(res, roomIndex, xi, xr)) {
            xp = xi;
            kV.repaint();
        }
    }

    public void mouseReleased(int x, int y) {
        EasyguestSession sess = kV.getSession(); 
        Date arrival = Util.addDays(kV.getFirstDate(), xp);
        Date departure = Util.addDays(kV.getFirstDate(), xr);
        if (xp != xl) {
//            int n = JOptionPane.showConfirmDialog(kV.getFrame(),
//                    Util.getResource("question.reservation.move_arrival"),
//                    Util.getResource("question.reservation.title.move-date"),
//                    JOptionPane.YES_NO_OPTION,
//                    JOptionPane.QUESTION_MESSAGE);
//            if (n == JOptionPane.YES_OPTION) {
                sess.begin();
                try {
                    sess.reassociate(res);
                    res.setFromDate(arrival);
                    res.setToDate(departure);      
                    sess.commit();
                } finally {
                    sess.end();
                }                                
                kV.clearReservations();
//            } else {
//                return;
//            }
        } else {
            return;
        }
    }

    public void drawFeedback(Graphics g) {
        int cellWidth = kV.getCellWidth();
        int w = (xr-xp)*(cellWidth+1);
        int status = res.getStatus();
        g.setColor(Color.BLUE);
        g.drawRect(rc.x+rc.width-w, rc.y, w-1, rc.height-1);
    }    
}
