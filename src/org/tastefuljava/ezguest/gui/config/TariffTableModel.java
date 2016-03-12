package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.data.Period;
import org.tastefuljava.ezguest.data.Tariff;
import org.tastefuljava.ezguest.util.Util;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.tastefuljava.ezguest.session.EasyguestSession;

@SuppressWarnings("serial")
public class TariffTableModel extends AbstractTableModel {
    public static final int COLUMN_NAME  = 0;
    public static final int COLUMN_FACTOR  = 1;
    public static final int COLUMN_COLOR = 2;

    private final EasyguestSession sess;
    private final List<Tariff> tariffs = new ArrayList<>();

    public TariffTableModel(EasyguestSession sess) {
        this.sess = sess;
        tariffs.addAll(sess.getExtent(Tariff.class));
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return tariffs.size()+1;
    }

    public Tariff getTariff(int index) {
        return index < tariffs.size() ? tariffs.get(index) : null;
    }

    @Override
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= tariffs.size()) {
            return null;
        }
        Tariff tariff = getTariff(rowIndex);
        switch (columnIndex) {
            case COLUMN_NAME:
                return tariff.getName();
            case COLUMN_FACTOR:
                return tariff.getFactor();
            case COLUMN_COLOR:
                return tariff.getColor();
        }
        return null;
    }

    @Override
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
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
