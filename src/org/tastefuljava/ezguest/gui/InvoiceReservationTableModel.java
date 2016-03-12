package org.tastefuljava.ezguest.gui;

import org.tastefuljava.ezguest.data.Reservation;
import org.tastefuljava.ezguest.data.Invoice;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.tastefuljava.ezguest.util.Util;

@SuppressWarnings("serial")
public class InvoiceReservationTableModel extends AbstractTableModel {
    public static final int COLUMN_FROMDATE   = 0;
    public static final int COLUMN_TODATE     = 1;
    public static final int COLUMN_ROOMNUMBER = 2;
    public static final int COLUMN_AMOUNT     = 3;

    public static final int COLUMN_COUNT = 4;

    private final EasyguestSession sess;
    private final List<Reservation> reservations = new ArrayList<>();
    private final List<Double> amounts = new ArrayList<>();
    private Invoice invoice;

    public InvoiceReservationTableModel(EasyguestSession sess) {
        this.sess = sess;
    }

    public void setInvoiceId(int id) {
        sess.begin();
        try {
            invoice = sess.getObjectById(Invoice.class, id);
            reservations.clear();
            amounts.clear();
            if (invoice != null) {
                for (Reservation res: invoice.getReservations()) {
                    reservations.add(res);
                    amounts.add(
                            res.getRoom() == null ? 0.0 : sess.getAmount(res));
                }
            }
        } finally {
            sess.end();
        }
        fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public int getRowCount() {
        return reservations.size()+1;
    }

    @Override
    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_FROMDATE:
                return Util.getResource("reservation.column.from-date");
            case COLUMN_TODATE:
                return Util.getResource("reservation.column.to-date");
            case COLUMN_ROOMNUMBER:
                return Util.getResource("reservation.column.room-number");
            case COLUMN_AMOUNT:
                return Util.getResource("reservation.column.amount");
        }
        return null;
    }

    @Override
    public Class getColumnClass(int index) {
        switch (index) {
            case COLUMN_FROMDATE:
            case COLUMN_TODATE:
                return Date.class;
            case COLUMN_ROOMNUMBER:
                return Integer.class;
            case COLUMN_AMOUNT:
                return Double.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != COLUMN_AMOUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= reservations.size()) {
            return null;
        }
        Reservation res = reservations.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_FROMDATE:
                return res.getFromDate();
            case COLUMN_TODATE:
                return res.getToDate();
            case COLUMN_ROOMNUMBER:
                return res.getRoom() == null ? null : new Integer(res.getRoom().getNumber());
            case COLUMN_AMOUNT:
                return amounts.get(rowIndex);
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        boolean isNew = rowIndex >= reservations.size();
        Reservation res = null;
        sess.begin();
        try {
            if (!isNew) {
                res = sess.getObjectById(Reservation.class,
                        reservations.get(rowIndex).getId());
            }
            if (res == null) {
                res = new Reservation();
                res.setInvoice(invoice);
                res.setRoom(null);
                res.setFromDate(Util.today());
                res.setToDate(Util.today());
                sess.makePersistent(res);
            }
            switch (columnIndex) {
                case COLUMN_FROMDATE:
                    res.setFromDate((Date)value);
                    break;
                case COLUMN_TODATE:
                    res.setToDate((Date)value);
                    break;
                case COLUMN_ROOMNUMBER:
                    Integer ival = (Integer)value;
                    res.setRoom(sess.getRoom(sess.getHotel(), ival));
                    break;
            }
            sess.commit();
        } finally {
            sess.end();
        }
        if (isNew) {
            reservations.add(res);
            fireTableRowsInserted(rowIndex+1, rowIndex+1);
        }
    }
}
