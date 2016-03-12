/*
 * PeriodTableModel.java
 *
 * Created on 03 November 2002, 15:19
 */

package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.util.Util;
import org.tastefuljava.ezguest.data.Tariff;
import org.tastefuljava.ezguest.data.Period;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class PeriodTableModel extends AbstractTableModel
        implements TableModelListener {
    public static final int COLUMN_FROMDATE  = 0;
    public static final int COLUMN_TODATE  = 1;
    public static final int COLUMN_TARIFF = 2;

    private static final Comparator<Period> FROMDATE_ORDER
            = new FromDateComparator();

    private EasyguestSession sess;
    private TariffTableModel tariffModel;
    private Tariff tariff;
    private List<Period> periods = new ArrayList<Period>();

    public PeriodTableModel(EasyguestSession sess,
            TariffTableModel tariffModel) {
        this.sess = sess;
        this.tariffModel = tariffModel;
        tariffModel.addTableModelListener(this);
        periods.addAll(sess.getExtent(Period.class));
        Collections.sort(periods, FROMDATE_ORDER);
    }

    private void setTariff(Tariff value) {
        tariff = value;
        periods.clear();
        if (tariff != null) {
            periods.addAll(tariff.getPeriods());
        }
        fireTableDataChanged();
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return periods.size()+1;
    }
    
    public Period getPeriod(int index) {
        return index < periods.size() ? periods.get(index) : null;
    }    

    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_FROMDATE:
                return Util.getResource("period.column.fromdate");
            case COLUMN_TODATE:
                return Util.getResource("period.column.todate");
            case COLUMN_TARIFF:
                return Util.getResource("period.column.tariff");
        }
        return null;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= periods.size()) {
            return null;
        }
        Period period = periods.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_FROMDATE:
                return period.getFromDate();
            case COLUMN_TODATE:
                return period.getToDate();
            case COLUMN_TARIFF:
                return period.getTariff();
        }
        return null;
    }

    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_FROMDATE:
                return Date.class;
            case COLUMN_TODATE:
                return Date.class;
            case COLUMN_TARIFF:
                return Tariff.class;
        }
        return null;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        boolean isNew = rowIndex >= periods.size();
        Period period;
        sess.begin();
        try {
            if (!isNew) {
                period = periods.get(rowIndex);
                period = sess.getObjectById(Period.class, period.getId());
                if (period == null) {
                    periods.remove(rowIndex);
                    fireTableRowsDeleted(rowIndex, rowIndex);
                    return;
                }
                periods.set(rowIndex, period);
            } else {
                period = new Period();
                period.setFromDate(Util.today());
                period.setToDate(Util.today());
                period.setTariff(tariff);
            }
            switch (columnIndex) {
                case COLUMN_FROMDATE:
                    period.setFromDate((Date)value);
                    break;

                case COLUMN_TODATE:
                    period.setToDate((Date)value);
                    break;

                case COLUMN_TARIFF:
                    period.setTariff((Tariff)value);
                    System.out.println("Tariff : " + (Tariff)value);
                    break;
            }
            if (isNew) {
                sess.makePersistent(period);
            }
            sess.commit();
        } finally {
            sess.end();
        }
        if (isNew) {
            periods.add(period);
            this.fireTableRowsInserted(rowIndex, rowIndex);
        }
    }

    public void tableChanged(TableModelEvent e) {
        switch (e.getType()) {
            case TableModelEvent.DELETE:
                sess.begin();
                try {
                    for (int j = 0; j < periods.size(); ++j) {
                        Period period = periods.get(j);
                        boolean found = false;
                        for (int i = e.getFirstRow(); i <= e.getLastRow(); ++i) {
                            Tariff tariff = tariffModel.getTariff(i);
                            if (tariff == period.getTariff()) {
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            period.setTariff(null);
                            fireTableCellUpdated(j,  COLUMN_TARIFF);
                        }
                    }
                    sess.commit();
                } finally {
                    sess.end();
                }
                break;
            case TableModelEvent.INSERT:
                for (int j = 0; j < periods.size(); ++j) {
                    Period period = periods.get(j);
                    boolean found = false;
                    for (int i = e.getFirstRow(); i <= e.getLastRow(); ++i) {
                        Tariff tariff = tariffModel.getTariff(i);
                        if (tariff == period.getTariff()) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        fireTableCellUpdated(j,  COLUMN_TARIFF);
                    }
                }
                break;
        }
    }

    private static class FromDateComparator implements Comparator<Period> {
        public int compare(Period a, Period b) {
            Date d = a.getFromDate();
            Date e = b.getFromDate();
            if (d == null) {
                return e == null ? 0 : -1;
            }
            return d.compareTo(e);
        }
    }
}
