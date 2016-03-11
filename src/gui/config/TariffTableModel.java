/*
 * TariffTableModel.java
 *
 * Created on 03 November 2002, 15:19
 */

package gui.config;

import data.Period;
import data.Tariff;
import util.Util;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import session.EasyguestSession;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class TariffTableModel extends AbstractTableModel {
    public static final int COLUMN_NAME  = 0;
    public static final int COLUMN_FACTOR  = 1;
    public static final int COLUMN_COLOR = 2;

    private EasyguestSession sess;
    private Period period;
    private List<Tariff> tariffs = new ArrayList<Tariff>();

    public TariffTableModel(EasyguestSession sess) {
        this.sess = sess;
        tariffs.addAll(sess.getExtent(Tariff.class));
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return tariffs.size()+1;
    }

    public Tariff getTariff(int index) {
        return index < tariffs.size() ? tariffs.get(index) : null;
    }

    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_NAME:
                return Util.getResource("tariffperiod.column.name");
            case COLUMN_FACTOR:
                return Util.getResource("tariffperiod.column.factor");
            case COLUMN_COLOR:
                return Util.getResource("tariffperiod.column.color");
        }
        return null;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= tariffs.size()) {
            return null;
        }
        Tariff tariff = getTariff(rowIndex);
        switch (columnIndex) {
            case COLUMN_NAME:
                return tariff.getName();
            case COLUMN_FACTOR:
                return new Double(tariff.getFactor());
            case COLUMN_COLOR:
                return tariff.getColor();
        }
        return null;
    }

    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_NAME:
                return String.class;
            case COLUMN_FACTOR:
                return Double.class;
            case COLUMN_COLOR:
                return Color.class;
        }
        return null;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        boolean isNew = rowIndex >= tariffs.size();
        Tariff tariff;
        sess.begin();
        try {
            if (!isNew) {
                tariff = tariffs.get(rowIndex);
            } else {
                tariff = new Tariff();
                tariff.setName(Util.getResource("tariff.newname"));
                tariff.setFactor(1.0);
                tariff.setColor(Color.white);
                sess.makePersistent(tariff);
            }
            switch (columnIndex) {
                case COLUMN_NAME:
                    tariff.setName((String)value);
                    break;

                case COLUMN_FACTOR:
                    tariff.setFactor(Double.parseDouble(value.toString()));
                    break;

                case COLUMN_COLOR:
                    tariff.setColor((Color)value);
                    break;
            }
            sess.commit();
        } finally {
            sess.end();
        }
        if (isNew) {
            tariffs.add(tariff);
            fireTableRowsInserted(rowIndex+1, rowIndex+1);
        } else {
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
}
