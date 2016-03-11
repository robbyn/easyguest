/*
 * TariffRenderer.java
 *
 * Created on 26 novembre 2002, 01:39
 */

package gui;

import data.Reservation;
import data.Room;
import data.Invoice;
import data.Customer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.ListCellRenderer;
import components.ColorButton;
import components.ColorIcon;
import util.Util;

/**
 * @author  denis
 */
public class ReservationRenderer implements ListCellRenderer {
    public static final int COLUMN_NAME   = 0;
    public static final int COLUMN_FACTOR = 1;
    public static final int COLUMN_COLOR  = 2;

    private JLabel label = new JLabel();

    public ReservationRenderer() {
        label.setOpaque(true);
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(SwingConstants.LEFT);
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        label.setText(toString((Reservation)value));
        return label;
    }

    private static String toString(Reservation res) {
        StringBuffer buf = new StringBuffer();
        Room room = res.getRoom();
        Invoice inv = res.getInvoice();
        if (room != null) {
            buf.append(room.getNumber());
            buf.append(":");
        }
        Customer cust = inv == null ? null : inv.getCustomer();
        if (cust != null) {
            if (!Util.isBlank(cust.getFirstName())) {
                buf.append(" ");
                buf.append(cust.getFirstName());
            }
            if (!Util.isBlank(cust.getLastName())) {
                buf.append(" ");
                buf.append(cust.getLastName());
            }
        }
        return buf.toString();
    }
}
