package org.tastefuljava.ezguest.components;

import org.tastefuljava.ezguest.data.Reservation;
import org.tastefuljava.ezguest.util.Util;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Date;
import org.tastefuljava.ezguest.session.EasyguestSession;

public class MouseDragReservationBarRight implements MouseDragger {

    private final CalendarView kV;
    private final Reservation res;
    private final Rectangle rc;
    private final int xr;
    private final int xl;
    private final int roomIndex;
    private int xp;

    public MouseDragReservationBarRight(CalendarView calendarView, Reservation res,
            int xo, int yo) {
        this.kV = calendarView;
        this.res = res;
        this.roomIndex = kV.getRoomIndex(res.getRoom());
        this.rc = kV.reservationBounds(this.res);
        this.xr = (rc.x + rc.width) / (kV.getCellWidth() + 1);
        this.xl = rc.x / (kV.getCellWidth() + 1);
        this.xp = xr;
    }

    @Override
    public void mouseDragged(int x, int y) {
        int xi = x / (kV.getCellWidth() + 1);
        if (xi <= xl) {
            xi = xl + 1;
        }
        if (xi != xp && kV.isFree(res, roomIndex, xl, xi)) {
            xp = xi;
            kV.repaint();
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        EasyguestSession sess = kV.getSession();
        Date arrival = Util.addDays(kV.getFirstDate(), xl);
        Date departure = Util.addDays(kV.getFirstDate(), xp);
        if (xp != xr) {
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
        }
    }

    @Override
    public void drawFeedback(Graphics g) {
        int cellWidth = kV.getCellWidth();
        int w = (xp - xl) * (cellWidth + 1);
        g.setColor(Color.BLUE);
        g.drawRect(rc.x, rc.y, w - 1, rc.height - 1);
    }
}
