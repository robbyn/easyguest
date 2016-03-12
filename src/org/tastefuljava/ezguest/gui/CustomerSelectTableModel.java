/*
 * ReservationSearchTableModel.java
 *
 * Created on 12 december 2002, 23:46
 */

package org.tastefuljava.ezguest.gui;

import org.tastefuljava.ezguest.data.Customer;
import javax.swing.table.AbstractTableModel;
import org.tastefuljava.ezguest.util.Util;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class CustomerSelectTableModel extends AbstractTableModel {
    public static final int COLUMN_NAME  = 0;
    public static final int COLUMN_FIRSTNAME  = 1;
    public static final int COLUMN_ZIP = 2;
    public static final int COLUMN_CITY  = 3;        

    private Customer customers[] = new Customer[0];

    public CustomerSelectTableModel() {
    }

    public void setCustomers(Customer cust[]) {
        customers = cust == null ? new Customer[0] : cust;
        fireTableDataChanged();
    }

    public int getColumnCount() {
        return 4;
    }

    public int getRowCount() {
        return customers.length;
    }

    public Customer getCustomer(int row) {
        return customers[row];
    }

    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_NAME:
                return Util.getResource("dialog.reservation.name");            
            case COLUMN_FIRSTNAME:
                return Util.getResource("dialog.reservation.firstname");
            case COLUMN_ZIP:
                return Util.getResource("dialog.reservation.zip");
            case  COLUMN_CITY :
                return Util.getResource("dialog.reservation.city");
        }
        return null;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {        
        Customer customer = customers[rowIndex];
        switch (columnIndex) {
            case COLUMN_NAME:
                return customer.getLastName();            
            case COLUMN_FIRSTNAME:
                return customer.getFirstName();
            case COLUMN_ZIP:
                return customer.getZip();
            case COLUMN_CITY:
                return customer.getCity();                
        }
        return null;
    }

    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_NAME:
                return String.class;
            case COLUMN_FIRSTNAME:
                return String.class;
            case COLUMN_ZIP:
                return String.class;
            case COLUMN_CITY:
                return String.class;                
        }
        return null;
    }            
}
