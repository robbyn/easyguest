package org.tastefuljava.ezguest.gui.config;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.TableModelEvent;

@SuppressWarnings("serial")
public class RoomTypeTable extends JTable {

    public RoomTypeTable() {
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        switch (e.getType()) {
            case TableModelEvent.INSERT:
            case TableModelEvent.UPDATE:
                for (int r = e.getFirstRow(); r <= e.getLastRow(); ++r) {
                    if (r >= 0) {
                        adjustRowHeight(r);
                    }
                }
                break;
        }
    }

    public void adjustRowHeight(int row) {
        int colCount = getColumnCount();
        int height = 0;
        for (int i = 0; i < colCount; ++i) {
            TableCellRenderer renderer = getCellRenderer(row, i);
            if (renderer != null) {
                Component comp = renderer.getTableCellRendererComponent(
                        this, getValueAt(row, i), false, false, row, i);
                Dimension size = comp.getPreferredSize();
                if (size.height > height) {
                    height = size.height;
                }
            }
        }
        if (height > 0) {
            setRowHeight(row, height);
        }
    }
}
