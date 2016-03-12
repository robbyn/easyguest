

/*
 * Reservation.java
 *
 * Created on 02 December 2002, 17:18
 */

package org.tastefuljava.ezguest.data;

import java.util.Date;

/**
 * @author  Maurice Perry
 */
public class Reservation {
    public static final int STATUS_RESERVED = 0;
    public static final int STATUS_OCCUPIED = 1;
    public static final int STATUS_CANCELED = 2;
    public static final int STATUS_RELEASED = 3;

    public static final int STATUS_COUNT = 4;

    private int id;
    private Date fromDate;
    private Date toDate;
    private int status;
    private Room room;
    private Invoice invoice;

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date newValue) {
        fromDate = newValue;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date newValue) {
        toDate = newValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int newValue) {
        status = newValue;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room newValue) {
        room = newValue;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice newValue) {
        if (invoice != null) {
            invoice.removeReservation(this);
        }
        invoice = newValue;
        if (invoice != null) {
            invoice.addReservation(this);
        }
    }
}
