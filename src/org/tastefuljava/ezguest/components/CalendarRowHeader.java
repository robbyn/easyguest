package org.tastefuljava.ezguest.components;

import org.tastefuljava.ezguest.data.Room;
import org.tastefuljava.ezguest.util.Util;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class CalendarRowHeader extends JComponent {
    private CalendarView kV;

    public CalendarRowHeader(CalendarView calendarView) {
        this.kV = calendarView;
        initialize();
    }

    private void initialize() {
        setToolTipText("");
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        int row = event.getY()/(kV.getCellHeight()+1);
        Room rooms[] = kV.getRooms();
        if (rooms == null || row >= rooms.length) {
            return super.getToolTipText(event);
        }
        Room room = rooms[row];
        StringBuilder buf = new StringBuilder();
        buf.append(Util.format("room.tooltip.number", room.getNumber()));
        buf.append('\n');
        buf.append(Util.format("room.tooltip.type", room.getType().getName()));
        buf.append('\n');
        buf.append(Util.format("room.tooltip.price",
                room.getType().getBasePrice()));
        return buf.toString();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dim = kV.getPreferredSize();
        dim.width = 6*kV.getCellWidth();
        return dim;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int cellHeight = kV.getCellHeight();
        int rowCount = kV.getRowCount();
        Room rooms[] = kV.getRooms();
        int left = 0;
        int right = left + width;
        int top = 0;
        int bottom = top + height;
        int x = left;
        int y = top;

        for (int i = 0; i < rowCount; ++i) {
            String s = "";
            if (rooms != null && i < rooms.length) {
                s = roomToString(rooms[i]);
            }
            CalendarView.drawCenteredVertical(g, x+7, y, cellHeight, s);
            y += cellHeight;
            if (y >= bottom) {
                break;
            }
            g.drawLine(left, y, right, y);
            y += 1;
        }
        g.drawLine(right-1, top, right-1, bottom);
    }

    private String roomToString(Room room) {
        StringBuilder buf = new StringBuilder();
        buf.append(room.getNumber());
        if (kV.getShowRoomPrice() || kV.getShowRoomType()) {
            buf.append(" (");
            if (kV.getShowRoomType()) {
                buf.append(room.getType().getName());
                if (kV.getShowRoomPrice()) {
                    buf.append(": ");
                }
            }
            if (kV.getShowRoomPrice()) {
                buf.append(Util.dbl2str(room.getType().getBasePrice()));
            }
            buf.append(")");
        }
        return buf.toString();
    }
}
