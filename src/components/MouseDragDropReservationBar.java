/*
 * MouseDragDropReservationBar.java
 *
 * Created on 29 janvier 2003, 00:39
 */

package components;

import data.Reservation;
import data.Room;
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
public class MouseDragDropReservationBar implements MouseDragger {
    private CalendarView kV;
    private Reservation res;
    private int xo, xp, yo, yp, days;
    private int xoffset, yoffset;
    private Rectangle rc;

    public MouseDragDropReservationBar(CalendarView calendarView,
            Reservation res, int x, int y) {
        kV = calendarView;
        this.res = res;
        xo = kV.indexForDate(res.getFromDate());
        days = kV.indexForDate(res.getToDate())-xo;
        xp = xo;
        xoffset = x-(kV.getCellWidth()+1)*xo;
        yo = y/(kV.getCellHeight()+1);
        yp = yo;
        yoffset = y-(kV.getCellHeight()+1)*yp;
        rc = kV.reservationBounds(res);
    }

    public void mouseDragged(int x, int y) {
        int xi = (x-xoffset)/(kV.getCellWidth()+1);
        int yi = y/(kV.getCellHeight()+1);
        if ((xi != xp || yi != yp) && kV.isFree(res, yi, xi, xi+days)) {
            xp = xi;
            yp = yi;
            kV.repaint();
        }
    }

    public void mouseReleased(int x, int y) {
        EasyguestSession sess = kV.getSession(); 
        int left = xp;
        int right = xp+(rc.width/(kV.getCellWidth()+1));
        Date arrival = Util.addDays(kV.getFirstDate(), left);
        Date departure = Util.addDays(kV.getFirstDate(), right);

        if ((xp != xo || yp != yo)) {          
//            int n = JOptionPane.showConfirmDialog(kV.getFrame(),
//                    Util.getResource("question.reservation.move_bar"),
//                    Util.getResource("question.reservation.title.move"),
//                    JOptionPane.YES_NO_OPTION,
//                    JOptionPane.QUESTION_MESSAGE);        
//            if (n == JOptionPane.YES_OPTION) {        
                sess.begin();
                try {
                    Room room = kV.getRoom(yp);
                    sess.reassociate(room);
                    sess.reassociate(res);
                    res.setRoom(kV.getRoom(yp));
                    res.setFromDate(arrival);
                    res.setToDate(departure);      
                    sess.update(res);
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
        int x = xp*(cellWidth+1)+cellWidth/2;
        int y = yp*(kV.getCellHeight()+1);
        int status = res.getStatus();
        g.setColor(Color.BLUE);
        g.drawRect(x, y+2, rc.width-1, rc.height-1);
    }
}
