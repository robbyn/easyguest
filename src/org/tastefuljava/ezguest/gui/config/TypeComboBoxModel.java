/*
 * TypeComboBoxModel.java
 *
 * Created on 2 december 2002, 14:01
 */
package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.data.RoomType;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.util.List;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class TypeComboBoxModel extends AbstractListModel
        implements ComboBoxModel, TableModelListener {
    private RoomType selection = null;
    private List<RoomType> roomTypes = new ArrayList<RoomType>();
    private RoomTypeTableModel roomTypeModel;

    public TypeComboBoxModel(EasyguestSession sess, RoomTypeTableModel roomTypeModel) {
        this.roomTypeModel = roomTypeModel;
        roomTypes.addAll(sess.getExtent(RoomType.class));        
        this.roomTypeModel = roomTypeModel;
        roomTypeModel.addTableModelListener(this);
    }
    
    public int getSize() {
        return roomTypes.size();
    }

    public Object getElementAt(int index) {
        return roomTypes.get(index);
    }

    public Object getSelectedItem() {
        return selection;
    }

    public void setSelectedItem(Object anItem) {
        selection = null;
        for (RoomType rt: roomTypes) {
            if (rt.equals(anItem)) {
                selection = rt;
                break;
            }
        }
    }

    public void add(RoomType roomType) {
        int pos = roomTypes.size();
        roomTypes.add(roomType);
        fireIntervalAdded(this, pos, pos);
    }

    public void remove(RoomType roomType) {
        int pos = roomTypes.indexOf(roomType);
        if (pos >= 0) {
            roomTypes.remove(pos);
            fireIntervalRemoved(this, pos, pos);
        }
    }

    public void change(RoomType roomType) {
        int pos = roomTypes.indexOf(roomType);
        if (pos >= 0) {
            fireContentsChanged(this, pos, pos);
        }
    }
    
    public void tableChanged(TableModelEvent e) {
        switch (e.getType()) {
            case TableModelEvent.DELETE:
                for (int i = e.getFirstRow(); i <= e.getLastRow(); ++i) {
                    remove(roomTypeModel.getRoomType(i));
                }
                break;
            case TableModelEvent.INSERT:
                for (int i = e.getFirstRow(); i <= e.getLastRow(); ++i) {
                    add(roomTypeModel.getRoomType(i-1));
                }
                break;
        }
    }    
}
