package org.tastefuljava.ezguest.gui;

import org.tastefuljava.ezguest.session.EasyguestSession;
import java.awt.Frame;
import java.awt.Container;
import java.awt.Cursor;
import org.tastefuljava.ezguest.data.Reservation;
import org.tastefuljava.ezguest.data.Hotel;
import java.util.Date;
import java.awt.Dimension;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tastefuljava.ezguest.util.Util;

@SuppressWarnings("serial")
public class SummaryPanel extends javax.swing.JPanel {
    private static final Log LOG = LogFactory.getLog(SummaryPanel.class);

    private final EasyguestSession sess;

    public SummaryPanel(EasyguestSession sess) {
        this.sess = sess;
        initComponents();
        initSummaryDataComponents();
        Dimension max = labelYesterday.getPreferredSize();
        Dimension dim = labelToday.getPreferredSize();
        if (dim.width > max.width) {
            max.width = dim.width;
        }
        dim = labelTomorrow.getPreferredSize();
        if (dim.width > max.width) {
            max.width = dim.width;
        }
        labelYesterday.setPreferredSize(max);
        labelToday.setPreferredSize(max);
        labelTomorrow.setPreferredSize(max);
        
        dateToday.setFormat(Util.getResource("summary.date-format"));
        arrivals.setCellRenderer(new ReservationRenderer());
        arrivals.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        departures.setCellRenderer(new ReservationRenderer());
        departures.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void initSummaryDataComponents() {
        sess.begin();
        try {
            Date yesterday = Util.yesterday();
            Date today = Util.today();
            Date tomorrow = Util.tomorrow();
            Hotel hotel = sess.getHotel();
            int total = hotel.getRooms().size();

            /* yesterday */
            int free = sess.getFreeRoomCount(hotel, yesterday);
            int occupied = sess.getOccupiedRoomCount(hotel, yesterday);
            int ratio = total == 0 ? 0 : (100*occupied)/total;
            labelYesterdayNumFR.setText(Integer.toString(free));
            labelYesterdayNumOR.setText(Integer.toString(occupied));
            labelYesterdayRO.setText(ratio + "%");

            /* today */
            free = sess.getFreeRoomCount(hotel, today);
            occupied = sess.getOccupiedRoomCount(hotel, today);
            ratio = total == 0 ? 0 : (100*occupied)/total;
            labelTodayNumFR.setText(Integer.toString(free));
            labelTodayNumOR.setText(Integer.toString(occupied));
            labelTodayRO.setText(ratio + "%");

            /* tomorrow */
            free = sess.getFreeRoomCount(hotel, tomorrow);
            occupied = sess.getOccupiedRoomCount(hotel, tomorrow);
            ratio = total == 0 ? 0 : (100*occupied)/total;
            labelTomorrowNumFR.setText(Integer.toString(free));
            labelTomorrowNumOR.setText(Integer.toString(occupied));
            labelTomorrowRO.setText(ratio + "%");

            Reservation reservations[] = sess.getArrivals(hotel, today);
            arrivals.setListData(reservations);
            reservations = sess.getDepartures(hotel, today);
            departures.setListData(reservations);
        } finally {
            sess.end();
        }
    }

    private void activateReservation(Reservation res) {
        ReservationDialog dlg = new ReservationDialog(getFrame(), sess, res);
        dlg.setVisible(true);
        initSummaryDataComponents();
    }

    private void activateReservation(String resid) {
        Reservation res = sess.getObjectById(Reservation.class, 
                Integer.valueOf(resid));
        activateReservation(res);
    }

    private Frame getFrame() {
        for (Container c = getParent(); c != null; c = c.getParent()) {
            if (c instanceof Frame) {
                return (Frame)c;
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dateToday = new org.tastefuljava.ezguest.components.DateToday();
        labelYesterday = new javax.swing.JLabel();
        labelToday = new javax.swing.JLabel();
        labelTomorrow = new javax.swing.JLabel();
        labelFreeRooms = new javax.swing.JLabel();
        labelYesterdayNumFR = new javax.swing.JLabel();
        labelTodayNumFR = new javax.swing.JLabel();
        labelTomorrowNumFR = new javax.swing.JLabel();
        labelOccupiedRooms = new javax.swing.JLabel();
        labelYesterdayNumOR = new javax.swing.JLabel();
        labelTodayNumOR = new javax.swing.JLabel();
        labelTomorrowNumOR = new javax.swing.JLabel();
        labelRateOccupation = new javax.swing.JLabel();
        labelYesterdayRO = new javax.swing.JLabel();
        labelTodayRO = new javax.swing.JLabel();
        labelTomorrowRO = new javax.swing.JLabel();
        labelArrivalToday = new javax.swing.JLabel();
        labelDepartureToday = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arrivals = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        departures = new javax.swing.JList();

        setLayout(new java.awt.GridBagLayout());

        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        setMinimumSize(new java.awt.Dimension(381, 147));
        setPreferredSize(new java.awt.Dimension(522, 147));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        dateToday.setBackground(new java.awt.Color(255, 255, 153));
        dateToday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateToday.setFont(new java.awt.Font("Dialog", 1, 18));
        dateToday.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        add(dateToday, gridBagConstraints);

        labelYesterday.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("summary.yesterday"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(11, 5, 0, 0);
        add(labelYesterday, gridBagConstraints);

        labelToday.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("summary.today"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(11, 5, 0, 0);
        add(labelToday, gridBagConstraints);

        labelTomorrow.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("summary.tomorrow"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(11, 5, 0, 11);
        add(labelTomorrow, gridBagConstraints);

        labelFreeRooms.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("summary.freerooms"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        add(labelFreeRooms, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(labelYesterdayNumFR, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(labelTodayNumFR, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 11);
        add(labelTomorrowNumFR, gridBagConstraints);

        labelOccupiedRooms.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("summary.occupied"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        add(labelOccupiedRooms, gridBagConstraints);

        labelYesterdayNumOR.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(labelYesterdayNumOR, gridBagConstraints);

        labelTodayNumOR.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(labelTodayNumOR, gridBagConstraints);

        labelTomorrowNumOR.setText("2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 11);
        add(labelTomorrowNumOR, gridBagConstraints);

        labelRateOccupation.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("summary.occupancyratio"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        add(labelRateOccupation, gridBagConstraints);

        labelYesterdayRO.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(labelYesterdayRO, gridBagConstraints);

        labelTodayRO.setText("14%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(labelTodayRO, gridBagConstraints);

        labelTomorrowRO.setText("28%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 11);
        add(labelTomorrowRO, gridBagConstraints);

        labelArrivalToday.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("summary.todayarrivals"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 11);
        add(labelArrivalToday, gridBagConstraints);

        labelDepartureToday.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("summary.todaydepartures"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 11);
        add(labelDepartureToday, gridBagConstraints);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(22, 120));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(3, 120));
        arrivals.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        arrivals.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        arrivals.setSelectionBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        arrivals.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                arrivalsValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(arrivals);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 11, 11);
        add(jScrollPane1, gridBagConstraints);

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setMinimumSize(new java.awt.Dimension(22, 120));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(3, 150));
        departures.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        departures.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        departures.setSelectionBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        departures.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                departuresValueChanged(evt);
            }
        });

        jScrollPane2.setViewportView(departures);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 11);
        add(jScrollPane2, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        initSummaryDataComponents();
    }//GEN-LAST:event_formComponentShown

    private void departuresValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_departuresValueChanged
        if (!evt.getValueIsAdjusting()) {
            Reservation res = (Reservation)departures.getSelectedValue();
            if (res != null) {
                activateReservation(res);
            }
        }
    }//GEN-LAST:event_departuresValueChanged

    private void arrivalsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_arrivalsValueChanged
        if (!evt.getValueIsAdjusting()) {
            Reservation res = (Reservation)arrivals.getSelectedValue();
            if (res != null) {
                activateReservation(res);
            }
        }
    }//GEN-LAST:event_arrivalsValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList arrivals;
    private org.tastefuljava.ezguest.components.DateToday dateToday;
    private javax.swing.JList departures;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelArrivalToday;
    private javax.swing.JLabel labelDepartureToday;
    private javax.swing.JLabel labelFreeRooms;
    private javax.swing.JLabel labelOccupiedRooms;
    private javax.swing.JLabel labelRateOccupation;
    private javax.swing.JLabel labelToday;
    private javax.swing.JLabel labelTodayNumFR;
    private javax.swing.JLabel labelTodayNumOR;
    private javax.swing.JLabel labelTodayRO;
    private javax.swing.JLabel labelTomorrow;
    private javax.swing.JLabel labelTomorrowNumFR;
    private javax.swing.JLabel labelTomorrowNumOR;
    private javax.swing.JLabel labelTomorrowRO;
    private javax.swing.JLabel labelYesterday;
    private javax.swing.JLabel labelYesterdayNumFR;
    private javax.swing.JLabel labelYesterdayNumOR;
    private javax.swing.JLabel labelYesterdayRO;
    // End of variables declaration//GEN-END:variables
}
