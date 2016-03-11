/*
 * RoomTypeTableModel.java
 *
 * Created on 01 december 2002, 17:39
 */

package gui.config;

import util.Util;
import data.Room;
import data.RoomType;
import session.EasyguestSession;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class RoomTypeTableModel extends AbstractTableModel {
    public static final int COLUMN_NAME  = 0;
    public static final int COLUMN_PRICE  = 1;
    public static final int COLUMN_DESCRIPTION = 2;

    private EasyguestSession sess;
    private List<RoomType> roomTypes = new ArrayList<RoomType>();

    public RoomTypeTableModel(EasyguestSession sess) {
        this.sess = sess;
        roomTypes.addAll(sess.getExtent(RoomType.class));
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return roomTypes.size()+1;
    }

    public RoomType getRoomType(int index) {
        return index < roomTypes.size() ? roomTypes.get(index) : null;
    }

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

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= roomTypes.size()) {
            return null;
        }
        RoomType roomType = getRoomType(rowIndex);
        switch (columnIndex) {
            case COLUMN_NAME:
                return roomType.getName();
            case COLUMN_PRICE:
                return new Double(roomType.getBasePrice());
            case COLUMN_DESCRIPTION:
                return roomType.getDescription();
        }
        return null;
    }

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

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

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
