package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.util.Util;
import org.tastefuljava.ezguest.data.RoomType;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class RoomTypeTableModel extends AbstractTableModel {
    public static final int COLUMN_NAME  = 0;
    public static final int COLUMN_PRICE  = 1;
    public static final int COLUMN_DESCRIPTION = 2;

    private final EasyguestSession sess;
    private final List<RoomType> roomTypes = new ArrayList<>();

    public RoomTypeTableModel(EasyguestSession sess) {
        this.sess = sess;
        roomTypes.addAll(sess.getExtent(RoomType.class));
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return roomTypes.size()+1;
    }

    public RoomType getRoomType(int index) {
        return index < roomTypes.size() ? roomTypes.get(index) : null;
    }

    @Override
    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_NAME:
                return Util.getResource("roomtype.column.name");
            case COLUMN_PRICE:
                return Util.getResource("roomtype.column.price");
            case COLUMN_DESCRIPTION:
                return Util.getResource("roomtype.column.description");
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= roomTypes.size()) {
            return null;
        }
        RoomType roomType = getRoomType(rowIndex);
        switch (columnIndex) {
            case COLUMN_NAME:
                return roomType.getName();
            case COLUMN_PRICE:
                return roomType.getBasePrice();
            case COLUMN_DESCRIPTION:
                return roomType.getDescription();
        }
        return null;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_NAME:
                return String.class;
            case COLUMN_PRICE:
                return Double.class;
            case COLUMN_DESCRIPTION:
                return String.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        boolean isNew = rowIndex >= roomTypes.size();
        RoomType roomType;
        sess.begin();
        try {
            if (!isNew) {
                roomType = roomTypes.get(rowIndex);
            } else {
                roomType = new RoomType();
                roomType.setName(Util.getResource("roomtype.newname"));
                roomType.setBasePrice(0);
                sess.makePersistent(roomType);
            }
            switch (columnIndex) {
                case COLUMN_NAME:
                    roomType.setName((String)value);
                    break;
                case COLUMN_PRICE:
                    roomType.setBasePrice(Double.parseDouble(value.toString()));
                    break;
                case COLUMN_DESCRIPTION:
                    roomType.setDescription((String)value);
                    break;
            }
            sess.commit();
        } finally {
            sess.end();
        }
        if (isNew) {
            roomTypes.add(roomType);
            fireTableRowsInserted(rowIndex+1, rowIndex+1);
        } else {
            this.fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
}
