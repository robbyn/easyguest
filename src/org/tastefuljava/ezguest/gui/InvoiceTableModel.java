/*
 * InvoiceTableModel.java
 *
 * Created on 27 January 2003, 10:34
 */

package org.tastefuljava.ezguest.gui;

import org.tastefuljava.ezguest.data.Customer;
import org.tastefuljava.ezguest.data.Invoice;
import java.util.List;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.tastefuljava.ezguest.util.Util;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class InvoiceTableModel extends AbstractTableModel {
    public static final int COLUMN_CUSTOMER    = 0;
    public static final int COLUMN_ID          = 1;
    public static final int COLUMN_DATECREATED = 2;
    public static final int COLUMN_AMOUNT      = 3;
    private static final int COLUMN_COUNT      = 4;

    private EasyguestSession sess;
    private List<Invoice> list = new ArrayList<Invoice>();
    private List<Double> amounts = new ArrayList<Double>();

    public InvoiceTableModel(EasyguestSession sess) {
        this.sess = sess;
    }

    public void query(int id) {
        sess.begin();
        try {
            Invoice invoice = sess.getInvoice(id);
            fill(new Invoice[] {invoice});
        } finally {
            sess.end();
        }
    }

    public void queryRoom(int roomNumber) {        
        sess.begin();
        try {
            fill(sess.getInvoices(roomNumber));
        } finally {
            sess.end();
        }
    }

    public void query(Date fromDate, Date toDate) {
        sess.begin();
        try {
            fill(sess.getInvoices(fromDate, toDate));
        } finally {
            sess.end();
        }
    }

    public void query(String lastName, String firstName, String company) {
        sess.begin();
        try {
            fill(sess.getInvoices(lastName, firstName, company));
        } finally {
            sess.end();
        }
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    public int getRowCount() {
        return list.size();
    }

    public Invoice getInvoice(int row) {
        return list.get(row);
    }

    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_CUSTOMER:
                return Util.getResource("invoice.column.customer");
            case COLUMN_ID:
                return Util.getResource("invoice.column.id");
            case COLUMN_DATECREATED:
                return Util.getResource("invoice.column.datecreated");
            case COLUMN_AMOUNT:
                return Util.getResource("invoice.column.amount");
        }
        return null;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice inv = getInvoice(rowIndex);
        switch (columnIndex) {
            case COLUMN_CUSTOMER:
                return customerText(inv.getCustomer());
            case COLUMN_ID:
                return new Integer(inv.getId());
            case COLUMN_DATECREATED:
                return Util.date2str(inv.getDateCreated());
            case COLUMN_AMOUNT:
                return amounts.get(rowIndex);
            default:
                return "";
        }
    }

    private String customerText(Customer cust) {
        if (cust == null) {
            return "";
        } else {
            StringBuffer buf = new StringBuffer();
            if (!Util.isBlank(cust.getLastName())) {
                buf.append(' ');
                buf.append(cust.getLastName());
            }
            if (!Util.isBlank(cust.getFirstName())) {
                buf.append(' ');
                buf.append(cust.getFirstName());
            }
            if (!Util.isBlank(cust.getCompany())) {
                buf.append(' ');
                buf.append(cust.getCompany());
            }
            return buf.length() > 0 ? buf.substring(1) : "";
        }
    }

    private void fill(Invoice invoices[]) {
        list.clear();
        amounts.clear();
        for (Invoice invoice: invoices) {
            list.add(invoice);
            amounts.add(sess.getAmount(invoice));
        }
        fireTableDataChanged();
    }
}
