/*
 * Invoice.java
 *
 * Created on 02 December 2002, 17:25
 */

package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author  Maurice Perry
 */
public class Invoice {
    public static final int STATUS_OPEN  = 0;
    public static final int STATUS_PAYED = 1;

    private int id;
    private int status;
    private Date dateCreated;
    private Hotel hotel;
    private Customer customer;
    private List<Reservation> reservations = new ArrayList<Reservation>();
    private List<InvoiceItem> items = new ArrayList<InvoiceItem>();


    public Invoice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int newValue) {
        status = newValue;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel newValue) {
        hotel = newValue;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date newValue) {
        dateCreated = newValue;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer newValue) {
        customer = newValue;
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public void addReservation(Reservation res) {
        reservations.add(res);
    }

    public void removeReservation(Reservation res) {
        reservations.remove(res);
    }

    public void removeAllReservations() {
        reservations.clear();
    }

    public List<InvoiceItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(InvoiceItem item) {
        items.add(item);
    }

    public void removeItem(InvoiceItem item) {
        items.remove(item);
    }

    public void removeAllItems() {
        items.clear();
    }
}
