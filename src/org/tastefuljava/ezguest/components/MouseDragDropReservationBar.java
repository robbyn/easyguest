package org.tastefuljava.ezguest.components;

import org.tastefuljava.ezguest.data.Reservation;
import org.tastefuljava.ezguest.data.Room;
import org.tastefuljava.ezguest.util.Util;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Date;
import org.tastefuljava.ezguest.session.EasyguestSession;

public class MouseDragDropReservationBar implements MouseDragger {

    private final CalendarView kV;
    private final Reservation res;
    private final int xo, yo, days;
    private final int xoffset;
    private final Rectangle rc;
    private int xp, yp;

    public MouseDragDropReservationBar(CalendarView calendarView,
            Reservation res, int x, int y) {
        kV = calendarView;
        this.res = res;
        xo = kV.indexForDate(res.getFromDate());
        days = kV.indexForDate(res.getToDate()) - xo;
        xp = xo;
        xoffset = x - (kV.getCellWidth() + 1) * xo;
        yo = y / (kV.getCellHeight() + 1);
        yp = yo;
        rc = kV.reservationBounds(res);
    }

    @Override
    public void mouseDragged(int x, int y) {
        int xi = (x - xoffset) / (kV.getCellWidth() + 1);
        int yi = y / (kV.getCellHeight() + 1);
        if ((xi != xp || yi != yp) && kV.isFree(res, yi, xi, xi + days)) {
            xp = xi;
            yp = yi;
            kV.repaint();
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        EasyguestSession sess = kV.getSession();
        int left = xp;
        int right = xp + (rc.width / (kV.getCellWidth() + 1));
        Date arrival = Util.addDays(kV.getFirstDate(), left);
        Date departure = Util.addDays(kV.getFirstDate(), right);

        if ((xp != xo || yp != yo)) {
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
        }
    }

    @Override
    public void drawFeedback(Graphics g) {
        int cellWidth = kV.getCellWidth();
        int x = xp * (cellWidth + 1) + cellWidth / 2;
        int y = yp * (kV.getCellHeight() + 1);
        g.setColor(Color.BLUE);
        g.drawRect(x, y + 2, rc.width - 1, rc.height - 1);
    }
}
