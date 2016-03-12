/*
 * EasyguestSession.java
 *
 * Created on 09 December 2002, 15:16
 */

package org.tastefuljava.ezguest.session;

import org.tastefuljava.ezguest.data.Period;
import org.tastefuljava.ezguest.data.Tariff;
import org.tastefuljava.ezguest.data.Hotel;
import org.tastefuljava.ezguest.data.Room;
import org.tastefuljava.ezguest.data.RoomType;
import org.tastefuljava.ezguest.data.Reservation;
import org.tastefuljava.ezguest.data.Customer;
import org.tastefuljava.ezguest.data.Invoice;
import org.tastefuljava.ezguest.data.InvoiceItem;
import org.tastefuljava.ezguest.data.Article;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Date;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.tastefuljava.ezguest.util.Util;

/**
 * @author  Maurice Perry
 */
public class EasyguestSession {
    private static EasyguestSession instance;

    private SessionFactory sf;
    private Session pm;
    private Transaction tx;

    public EasyguestSession() {
        try {
            Configuration cfg = new Configuration();
            cfg.configure(
                    EasyguestSession.class.getResource("hibernate.cfg.xml"));
            sf = cfg.buildSessionFactory();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (pm != null) {
                pm.close();
                pm = null;
            }
            if (sf != null) {
                sf.close();
                sf = null;
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public static EasyguestSession instance() {
        if (instance == null) {
            instance = new EasyguestSession();
        }
        return instance;
    }
    
    public static void createDb() {
        Configuration cfg = new Configuration();
        cfg.configure();
        new SchemaExport(cfg).create(false, true);        
    }

    public Session getPm() {
        return pm;
    }

    public Hotel getHotel() {
        Hotel hotel = null;
        for (Hotel h: getExtent(Hotel.class)) {
            hotel = h;
        }
        return hotel;
    }

    public final void begin() {
        if (tx != null || pm != null) {
            throw new RuntimeException("Transaction already started");
        }
        try {
            pm = sf.openSession();
            tx = pm.beginTransaction();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public final void end() {
        try {
            if (tx != null) {
                tx.rollback();
                tx = null;
            }
            if (pm != null) {
                pm.close();
                pm = null;
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public final void commit() {
        try {
            if (tx != null) {
                tx.commit();
                tx = null;
            }
            if (pm != null) {
                pm.flush();
                pm.close();
                pm = null;
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public final Serializable makePersistent(Object obj) {
        try {
            return pm.save(obj);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public final void reassociate(Object obj) {
        pm.lock(obj, LockMode.NONE);
    }

    public final void update(Object obj) {
        try {
            pm.saveOrUpdate(obj);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public final void deletePersistent(Object obj) {
        try {
            pm.delete(obj);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final <T> T merge(T obj) {
        return (T)pm.merge(obj);
    }

    @SuppressWarnings("unchecked")
    public final <T> List<T> getExtent(Class<T> clazz) {
        try {
            String s = "from " + clazz.getName();
            Query qry = pm.createQuery(s);
            return (List<T>)qry.list();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final <T> T getObjectById(Class<T> clazz, Serializable id) {
        try {
            return (T)pm.load(clazz, id);
        } catch (ObjectNotFoundException e) {
            return null;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public Period periodForDate(Date date) {
        try {
            Query qry = pm.createQuery("from Period as p"
                    + " where p.fromDate<=:d and p.toDate>=:d");
            qry.setDate("d", new java.sql.Date(date.getTime()));
            Iterator it = qry.list().iterator();
            if (it.hasNext()) {
                return (Period)it.next();
            } else {
                return null;
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Period[] getPeriods(Date fromDate, Date toDate) {
        try {
            Query qry = pm.createQuery("from Period as p"
                    + " where p.toDate>=:a and p.fromDate<=:b");
            qry.setDate("a", new java.sql.Date(fromDate.getTime()));
            qry.setDate("b", new java.sql.Date(toDate.getTime()));
            Collection<Period> col = (Collection<Period>)qry.list();
            return col.toArray(new Period[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Reservation[] getReservations(Date fromDate, Date toDate) {
        try {
            Query qry = pm.createQuery("from Reservation as r"
                    + " where r.toDate>=:a and r.fromDate<=:b");
            qry.setDate("a", new java.sql.Date(fromDate.getTime()));
            qry.setDate("b", new java.sql.Date(toDate.getTime()));
            Collection<Reservation> col = (Collection<Reservation>)qry.list();
            return col.toArray(new Reservation[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public Tariff tariffForDate(Date date) {
        Period period = periodForDate(date);
        return period == null ? null : period.getTariff();
    }

    public double getAmount(Invoice invoice) {
        double amount = 0;
        Period periods[] = getPeriods(invoice);
        for (Iterator it = invoice.getReservations().iterator(); it.hasNext(); ) {
            amount += getAmount((Reservation)it.next(), periods);
        }
        for (Iterator it = invoice.getItems().iterator(); it.hasNext(); ) {
            InvoiceItem item = (InvoiceItem)it.next();
            amount += item.getAmount();
        }
        return round(amount);
    }

    public double getAmount(Reservation res) {
        double value = getAmount(res, getPeriods(res.getFromDate(), res.getToDate()));
        return round(value);
    }

    public double getAmount(Date fromDate, Date toDate, RoomType roomType) {
        double value = getAmount(fromDate, toDate, roomType, getPeriods(fromDate, toDate));
        return round(value);
    }

    public static double round(double value) {
        return ((double)(long)(100.0*value+0.5))/100.0;
    }

    private double getPrice(RoomType roomType, Date date, Period periods[]) {
        double price = roomType.getBasePrice();
        for (int i = 0; i < periods.length; ++i) {
            Period period = periods[i];
            if (!date.before(period.getFromDate())
                    && !date.after(period.getToDate())) {
                price *= period.getTariff().getFactor();
                break;
            }
        }
        return price;
    }

    private double getAmount(Date fromDate, Date toDate, RoomType roomType,
            Period periods[]) {
        double price = 0;
        for (Date date = fromDate; date.before(toDate);
                date = Util.addDays(date, 1)) {
            price += getPrice(roomType, date, periods);
        }
        return price;
    }

    private double getAmount(Reservation res, Period periods[]) {
        Date fromDate = res.getFromDate();
        Date toDate = res.getToDate();
        RoomType roomType = res.getRoom().getType();
        return getAmount(fromDate, toDate, roomType, periods);
    }

    private Period[] getPeriods(Invoice invoice) {
        Date fromDate = null;
        Date toDate = null;
        for (Iterator it = invoice.getReservations().iterator(); it.hasNext(); ) {
            Reservation res = (Reservation)it.next();
            if (fromDate == null || fromDate.after(res.getFromDate())) {
                fromDate = res.getFromDate();
            }
            if (toDate == null || toDate.before(res.getToDate())) {
                toDate = res.getToDate();
            }
        }
        if (fromDate == null || toDate == null) {
            return new Period[0];
        }
        return getPeriods(fromDate, toDate);
    }

    @SuppressWarnings("unchecked")
    public Customer[] getCustomers(String str) {
        try {
            Query qry = pm.createQuery("from Customer as c"
                    + " where c.lastName like :s or c.company like :s");
            qry.setString("s", str + "%");
            Collection<Customer> col = (Collection<Customer>)qry.list();
            return col.toArray(new Customer[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Customer[] getCustomers(String firstName, String lastName,
            String company) {
        try {
            Query qry = pm.createQuery("from Customer as c"
                    + " where c.firstName like :f"
                    + " and c.lastName like :l"
                    + " and c.company like :c");
            qry.setString("f", firstName + "%");
            qry.setString("l", lastName + "%");
            qry.setString("c", company + "%");
            Collection<Customer> col = (Collection<Customer>)qry.list();
            return col.toArray(new Customer[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public Invoice getInvoice(int id) {
        try {
            return (Invoice)pm.load(Invoice.class, new Integer(id));
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Invoice[] getInvoices(int roomNumber) {
        try {
            Query qry = pm.createQuery(
                    "select distinct r.invoice from Reservation as r"
                    + " where r.room.number=:roomNumber"
                    + " order by r.invoice.dateCreated desc");
            qry.setInteger("roomNumber", roomNumber);
            Collection<Invoice> col = (Collection<Invoice>)qry.list();
            return col.toArray(new Invoice[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Invoice[] getInvoices(Date fromDate, Date toDate) {
        try {
            Query qry = pm.createQuery(
                    "from Invoice as i"
                    + " where (:f is null or i.dateCreated>=:f)"
                    + " and (:t is null or i.dateCreated<=:t) "
                    + " order by i.dateCreated desc");
            qry.setDate("f", new java.sql.Date(fromDate.getTime()));
            qry.setDate("t", new java.sql.Date(toDate.getTime()));
            Collection<Invoice> col = (Collection<Invoice>)qry.list();
            return col.toArray(new Invoice[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Invoice[] getInvoices(String firstName, String lastName,
            String company) {
        try {
            Query qry = pm.createQuery(
                    "from Invoice as i"
                    + " where i.customer.firstName like :f"
                    + " and i.customer.lastName like :l"
                    + " and i.customer.company like :c"
                    + " order by i.dateCreated desc");
            qry.setString("f", firstName + "%");
            qry.setString("l", lastName + "%");
            qry.setString("c", company + "%");
            Collection<Invoice> col = (Collection<Invoice>)qry.list();
            return col.toArray(new Invoice[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Room getRoom(Hotel hotel, int number) {
        try {
            Query qry = pm.createQuery("from Room as r"
                    + " where r.hotel=:h and r.number=:n");
            qry.setParameter("h", hotel);
            qry.setInteger("n", number);
            List<Room> col = (List<Room>)qry.list();
            if (col.isEmpty()) {
                return null;
            } else {
                return col.get(0);
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public Room[] getFreeRooms(Hotel hotel, Date fromDate, Date toDate) {
        try {
            List<Room> col = new ArrayList<Room>();
            col.addAll(hotel.getRooms());
            Query qry = pm.createQuery("select distinct r.room"
                    + " from Reservation as r"
                    + " where r.room.hotel=:h"
                    + " and r.toDate>:a"
                    + " and r.fromDate<:b"
                    + " and r.status!=2");
            qry.setParameter("h", hotel);
            qry.setDate("a", new java.sql.Date(fromDate.getTime()));
            qry.setDate("b", new java.sql.Date(toDate.getTime()));
            col.removeAll(qry.list());
            Room result[] = col.toArray(new Room[col.size()]);
            Arrays.sort(result);
            return result;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public int getFreeRoomCount(Hotel hotel, Date date) {
        return hotel.getRooms().size() - getOccupiedRoomCount(hotel, date);
    }

    public int getOccupiedRoomCount(Hotel hotel, Date date) {
        try {
            Query qry = pm.createQuery("select count(r) from Reservation as r"
                    + " where r.room.hotel=:h"
                    + " and r.fromDate<=:d"
                    + " and r.toDate>:d"
                    + " and r.status!=2");
            qry.setParameter("h", hotel);
            qry.setDate("d", new java.sql.Date(date.getTime()));
            Number val = (Number)qry.uniqueResult();
            return val.intValue();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Reservation[] getArrivals(Hotel hotel, Date atDate) { 
        try {
            Query qry = pm.createQuery("from Reservation as r"
                + " where r.room.hotel=:h"
                + " and r.fromDate<=:d"
                + " and r.status=0");
            qry.setParameter("h", hotel);
            qry.setDate("d", new java.sql.Date(atDate.getTime()));
            Collection<Reservation> col = (Collection<Reservation>)qry.list();
            return col.toArray(new Reservation[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Reservation[] getDepartures(Hotel hotel, Date atDate) { 
        try {
            Query qry = pm.createQuery("from Reservation as r"
                + " where r.room.hotel=:h"
                + " and r.toDate<=:d"
                + " and r.status=0");
            qry.setParameter("h", hotel);
            qry.setDate("d", new java.sql.Date(atDate.getTime()));
            Collection<Reservation> col = (Collection<Reservation>)qry.list();
            return col.toArray(new Reservation[col.size()]);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public Article getArticle(String code) {
        try {
            Query qry = pm.createQuery("from Article as a where a.code=:c");
            qry.setString("c", code);
            List col = qry.list();
            return col.isEmpty() ? null : (Article)col.get(0);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
