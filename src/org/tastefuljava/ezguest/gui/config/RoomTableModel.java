/*
 * RoomTableModel.java
 *
 * Created on 01 december 2002, 17:39
 */

package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.data.Room;
import org.tastefuljava.ezguest.data.RoomType;
import org.tastefuljava.ezguest.util.Util;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.tastefuljava.ezguest.session.EasyguestSession;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class RoomTableModel extends AbstractTableModel {
    public static final int COLUMN_NUMBER  = 0;
    public static final int COLUMN_TYPE  = 1;

    private EasyguestSession sess;
    private RoomType roomType;
    private List<Room> rooms = new ArrayList<Room>();

    public RoomTableModel(EasyguestSession sess) {
        this.sess = sess;
        rooms.addAll(sess.getExtent(Room.class));
    }

    private void setType(RoomType value) {
        roomType = value;
        rooms.clear();
        if (roomType != null) {
            rooms.addAll(roomType.getRooms());
        }
        fireTableDataChanged();
    }

    public int getColumnCount() {
        return 2;
    }

    public int getRowCount() {
        return rooms.size()+1;
    }
    
    public Room getRoom(int index) {
        return index < rooms.size() ? rooms.get(index) : null;
    }
    
    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_NUMBER:
                return Util.getResource("room.column.number");
            case COLUMN_TYPE:
                return Util.getResource("room.column.type");
        }
        return null;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= rooms.size()) {
            return null;
        }
        Room room = rooms.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_NUMBER:
                return new Integer(room.getNumber());
            case COLUMN_TYPE:
                return room.getType();
        }
        return null;
    }

    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_NUMBER:
                return Integer.class;
            case COLUMN_TYPE:
                return RoomType.class;
        }
        return null;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        boolean isNew = rowIndex >= rooms.size();
        Room room;
        sess.begin();
        try {
            if (!isNew) {
                room = rooms.get(rowIndex);
            } else {
                room = new Room();
                room.setNumber(001);
                room.setType(roomType);
            }
            switch (columnIndex) {
                case COLUMN_NUMBER:
                   room.setNumber(Integer.parseInt(value.toString()));
                   break;
                case COLUMN_TYPE:
                   room.setType((RoomType)value);
                   break;
            }
            sess.update(room);
            sess.commit();
        } finally {
            sess.end();
        }
        if (isNew) {
            rooms.add(room);
            fireTableRowsInserted(rowIndex+1, rowIndex+1);
        }
    }
}
