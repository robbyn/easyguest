/*
 * MapPanel.java
 *
 * Created on 03 November 2002, 12:42
 */

package gui;

import components.CalendarUpperLeftCorner;
import components.CalendarUpperRightCorner;
import data.Reservation;
import java.util.Date;
import java.awt.Color;
import session.EasyguestSession;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Util;

/**
 * @author  Maurice Perry
 * @author  Denis Trimaille
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel {
    private static Log log = LogFactory.getLog(MapPanel.class);

    private EasyguestSession sess;
    private Reservation reservation = null;
    private boolean initialized = false;   

    public MapPanel(EasyguestSession sess) {
        this.sess = sess;
        initComponents();
        calendarView.setSession(sess);
        jScrollPane2.setColumnHeaderView(calendarView.getColumnHeader());
        jScrollPane2.setRowHeaderView(calendarView.getRowHeader());
        jScrollPane2.setCorner(JScrollPane.UPPER_LEFT_CORNER,
                                new CalendarUpperLeftCorner(calendarView));
        jScrollPane2.setCorner(JScrollPane.UPPER_RIGHT_CORNER,
                                new CalendarUpperRightCorner(calendarView));
        for (int month = 1; month <= 12; ++month) {
            comboBoxMonth.addItem(Util.getResource("month." + month));
        }
        updateMonthYear();
        initialized = true;
                
        calendarView.setStatusColor(Reservation.STATUS_RESERVED, 
                labelReserved.getBackground());
        calendarView.setStatusColor(Reservation.STATUS_OCCUPIED, 
                labelOccupied.getBackground());
        calendarView.setStatusColor(Reservation.STATUS_CANCELED, 
                labelCanceled.getBackground());
        calendarView.setStatusColor(Reservation.STATUS_RELEASED, 
                labelReleased.getBackground());        
    }

    
    public void updateMonthYear() {
        Date firstDate = calendarView.getFirstDate();
        int month = Util.month(firstDate);
        int year = Util.year(firstDate);
        comboBoxMonth.setSelectedIndex(month-1);
        txtFieldZoneYear.setText(Integer.toString(year));
        txtFieldZoneYear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }

    public int getYear() throws InputException {
        String strYear = txtFieldZoneYear.getText().trim();
        if (Util.isBlank(strYear)) {            
            throw new InputException(txtFieldZoneYear, "error.calendar.nullyear");
        } else {
            try {
                return Integer.parseInt(strYear);
            } catch (NumberFormatException e) {
                throw new InputException(txtFieldZoneYear, "error.calendar.formatyear");
            }
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        reservationPopup = new javax.swing.JPopupMenu();
        openReservation = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        stateReserved = new javax.swing.JRadioButtonMenuItem();
        stateOccupied = new javax.swing.JRadioButtonMenuItem();
        stateReleased = new javax.swing.JRadioButtonMenuItem();
        stateCanceled = new javax.swing.JRadioButtonMenuItem();
        reservationStateGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panelButtonsCalendarView = new javax.swing.JPanel();
        buttonBackMonth = new javax.swing.JButton();
        buttonBackWeek = new javax.swing.JButton();
        buttonBackDay = new javax.swing.JButton();
        comboBoxMonth = new javax.swing.JComboBox();
        txtFieldZoneYear = new javax.swing.JTextField();
        buttonNextDay = new javax.swing.JButton();
        buttonNextWeek = new javax.swing.JButton();
        buttonNextMonth = new javax.swing.JButton();
        panelLegendReservation = new javax.swing.JPanel();
        labelReserved = new javax.swing.JLabel();
        labelOccupied = new javax.swing.JLabel();
        labelCanceled = new javax.swing.JLabel();
        labelReleased = new javax.swing.JLabel();
        panelUtilDate = new javax.swing.JPanel();
        dateToday = new components.DateToday();
        jScrollPane2 = new javax.swing.JScrollPane();
        calendarView = new components.CalendarView();

        reservationPopup.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                reservationPopupPopupMenuWillBecomeVisible(evt);
            }
        });

        openReservation.setText(java.util.ResourceBundle.getBundle("resources").getString("map.menu.open-reservation"));
        openReservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openReservationActionPerformed(evt);
            }
        });

        reservationPopup.add(openReservation);

        reservationPopup.add(jSeparator1);

        reservationStateGroup.add(stateReserved);
        stateReserved.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status.reserved"));
        stateReserved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateReservedActionPerformed(evt);
            }
        });

        reservationPopup.add(stateReserved);

        reservationStateGroup.add(stateOccupied);
        stateOccupied.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status.occupied"));
        stateOccupied.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateOccupiedActionPerformed(evt);
            }
        });

        reservationPopup.add(stateOccupied);

        reservationStateGroup.add(stateReleased);
        stateReleased.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status.released"));
        stateReleased.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateReleasedActionPerformed(evt);
            }
        });

        reservationPopup.add(stateReleased);

        reservationStateGroup.add(stateCanceled);
        stateCanceled.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status.canceled"));
        stateCanceled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateCanceledActionPerformed(evt);
            }
        });

        reservationPopup.add(stateCanceled);

        setLayout(new java.awt.BorderLayout());

        setMinimumSize(new java.awt.Dimension(18, 37));
        setPreferredSize(new java.awt.Dimension(18, 37));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelButtonsCalendarView.setLayout(new java.awt.GridBagLayout());

        buttonBackMonth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/backmonth.png")));
        buttonBackMonth.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonBackMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackMonthActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panelButtonsCalendarView.add(buttonBackMonth, gridBagConstraints);

        buttonBackWeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/backweek.png")));
        buttonBackWeek.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonBackWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackWeekActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panelButtonsCalendarView.add(buttonBackWeek, gridBagConstraints);

        buttonBackDay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/backday.png")));
        buttonBackDay.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonBackDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackDayActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panelButtonsCalendarView.add(buttonBackDay, gridBagConstraints);

        comboBoxMonth.setToolTipText(java.util.ResourceBundle.getBundle("resources").getString("map.combobox.calendar.month"));
        comboBoxMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxMonthItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panelButtonsCalendarView.add(comboBoxMonth, gridBagConstraints);

        txtFieldZoneYear.setToolTipText(java.util.ResourceBundle.getBundle("resources").getString("map.textfield.calendar.year"));
        txtFieldZoneYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldZoneYearActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panelButtonsCalendarView.add(txtFieldZoneYear, gridBagConstraints);

        buttonNextDay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nextday.png")));
        buttonNextDay.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonNextDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextDayActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panelButtonsCalendarView.add(buttonNextDay, gridBagConstraints);

        buttonNextWeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nextweek.png")));
        buttonNextWeek.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonNextWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextWeekActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panelButtonsCalendarView.add(buttonNextWeek, gridBagConstraints);

        buttonNextMonth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nextmonth.png")));
        buttonNextMonth.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonNextMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextMonthActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panelButtonsCalendarView.add(buttonNextMonth, gridBagConstraints);

        jPanel1.add(panelButtonsCalendarView, java.awt.BorderLayout.WEST);

        labelReserved.setBackground(new java.awt.Color(255, 204, 153));
        labelReserved.setFont(new java.awt.Font("Dialog", 0, 12));
        labelReserved.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelReserved.setText(java.util.ResourceBundle.getBundle("resources").getString("map.legend.reserved"));
        labelReserved.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelReserved.setMaximumSize(new java.awt.Dimension(77, 17));
        labelReserved.setMinimumSize(new java.awt.Dimension(77, 17));
        labelReserved.setOpaque(true);
        labelReserved.setPreferredSize(new java.awt.Dimension(77, 17));
        panelLegendReservation.add(labelReserved);

        labelOccupied.setBackground(new java.awt.Color(255, 102, 102));
        labelOccupied.setFont(new java.awt.Font("Dialog", 0, 12));
        labelOccupied.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelOccupied.setText(java.util.ResourceBundle.getBundle("resources").getString("map.legend.occupied"));
        labelOccupied.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelOccupied.setMaximumSize(new java.awt.Dimension(77, 17));
        labelOccupied.setMinimumSize(new java.awt.Dimension(77, 17));
        labelOccupied.setOpaque(true);
        labelOccupied.setPreferredSize(new java.awt.Dimension(77, 17));
        panelLegendReservation.add(labelOccupied);

        labelCanceled.setBackground(new java.awt.Color(255, 255, 255));
        labelCanceled.setFont(new java.awt.Font("Dialog", 0, 12));
        labelCanceled.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCanceled.setText(java.util.ResourceBundle.getBundle("resources").getString("map.legend.canceled"));
        labelCanceled.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelCanceled.setMaximumSize(new java.awt.Dimension(77, 17));
        labelCanceled.setMinimumSize(new java.awt.Dimension(77, 17));
        labelCanceled.setOpaque(true);
        labelCanceled.setPreferredSize(new java.awt.Dimension(77, 17));
        panelLegendReservation.add(labelCanceled);

        labelReleased.setBackground(new java.awt.Color(204, 255, 255));
        labelReleased.setFont(new java.awt.Font("Dialog", 0, 12));
        labelReleased.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelReleased.setText(java.util.ResourceBundle.getBundle("resources").getString("map.legend.released"));
        labelReleased.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelReleased.setMaximumSize(new java.awt.Dimension(77, 17));
        labelReleased.setMinimumSize(new java.awt.Dimension(77, 17));
        labelReleased.setOpaque(true);
        labelReleased.setPreferredSize(new java.awt.Dimension(77, 17));
        panelLegendReservation.add(labelReleased);

        jPanel1.add(panelLegendReservation, java.awt.BorderLayout.CENTER);

        panelUtilDate.setMinimumSize(new java.awt.Dimension(77, 27));
        panelUtilDate.setPreferredSize(new java.awt.Dimension(77, 27));
        panelUtilDate.add(dateToday);

        jPanel1.add(panelUtilDate, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jScrollPane2ComponentResized(evt);
            }
        });

        calendarView.setBackground(new java.awt.Color(255, 255, 255));
        calendarView.setFont(new java.awt.Font("Dialog", 0, 9));
        calendarView.setShowRoomType(true);
        calendarView.setTodayColor(new java.awt.Color(230, 235, 242));
        calendarView.setWeekendColor(new java.awt.Color(249, 241, 221));
        calendarView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calendarViewMouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(calendarView);

        add(jScrollPane2, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        calendarView.invalidate();
    }//GEN-LAST:event_formComponentShown

    private void stateCanceledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateCanceledActionPerformed
        if (reservation != null) {
            sess.begin();
            try {
                reservation = sess.getObjectById(Reservation.class, reservation.getId());
                if (reservation != null) {
                    reservation.setStatus(Reservation.STATUS_CANCELED);
                }
                sess.commit();
            } finally {
                sess.end();
            }
            calendarView.clearReservations();
            calendarView.repaint();
        }
    }//GEN-LAST:event_stateCanceledActionPerformed

    private void stateReleasedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateReleasedActionPerformed
        if (reservation != null) {
            sess.begin();
            try {
                reservation = sess.getObjectById(Reservation.class, reservation.getId());
                if (reservation != null) {
                    reservation.setStatus(Reservation.STATUS_RELEASED);
                }
                sess.commit();
            } finally {
                sess.end();
            }
            calendarView.clearReservations();
            calendarView.repaint();
        }
    }//GEN-LAST:event_stateReleasedActionPerformed

    private void stateOccupiedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateOccupiedActionPerformed
        if (reservation != null) {
            sess.begin();
            try {
                reservation = sess.getObjectById(Reservation.class, reservation.getId());
                if (reservation != null) {
                    reservation.setStatus(Reservation.STATUS_OCCUPIED);
                }
                sess.commit();
            } finally {
                sess.end();
            }
            calendarView.clearReservations();
            calendarView.repaint();
        }
    }//GEN-LAST:event_stateOccupiedActionPerformed

    private void stateReservedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateReservedActionPerformed
        if (reservation != null) {
            sess.begin();
            try {
                reservation = sess.getObjectById(Reservation.class, reservation.getId());
                if (reservation != null) {
                    reservation.setStatus(Reservation.STATUS_RESERVED);
                }
                sess.commit();
            } finally {
                sess.end();
            }
            calendarView.clearReservations();
            calendarView.repaint();
        }
    }//GEN-LAST:event_stateReservedActionPerformed

    private void reservationPopupPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_reservationPopupPopupMenuWillBecomeVisible
        if (reservation != null) {
            stateReserved.setSelected(reservation.getStatus() == Reservation.STATUS_RESERVED);
            stateOccupied.setSelected(reservation.getStatus() == Reservation.STATUS_OCCUPIED);            
            stateReleased.setSelected(reservation.getStatus() == Reservation.STATUS_RELEASED);
            stateCanceled.setSelected(reservation.getStatus() == Reservation.STATUS_CANCELED);
        }
    }//GEN-LAST:event_reservationPopupPopupMenuWillBecomeVisible

    private void openReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openReservationActionPerformed
        if (reservation != null) {
            ReservationDialog reservationDialog
                    = new ReservationDialog(calendarView.getFrame(), sess,
                            reservation);
            reservationDialog.setVisible(true);
        }
    }//GEN-LAST:event_openReservationActionPerformed

    private void calendarViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendarViewMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {
            reservation = calendarView.getReservationAt(evt.getX(), evt.getY());
            if (reservation != null) {
                reservationPopup.show(calendarView,  evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_calendarViewMouseClicked

    private void buttonNextWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextWeekActionPerformed
        calendarView.nextWeek();
        updateMonthYear();
    }//GEN-LAST:event_buttonNextWeekActionPerformed

    private void buttonBackWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackWeekActionPerformed
        calendarView.previousWeek();
        updateMonthYear();
    }//GEN-LAST:event_buttonBackWeekActionPerformed

    private void jScrollPane2ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jScrollPane2ComponentResized
        System.out.println("resized");
        calendarView.updateSize();
    }//GEN-LAST:event_jScrollPane2ComponentResized

    private void txtFieldZoneYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldZoneYearActionPerformed
        try {
            int m = comboBoxMonth.getSelectedIndex()+1;
            calendarView.setDate(getYear(), m,
                    Util.day(calendarView.getFirstDate()));
        } catch (InputException e) {
            e.showMessage(this);
        }        
    }//GEN-LAST:event_txtFieldZoneYearActionPerformed

    private void comboBoxMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxMonthItemStateChanged
        if (initialized) {
            int month = Util.month(calendarView.getFirstDate());
            int m = comboBoxMonth.getSelectedIndex()+1;
            if(m != month) {
                int year = Util.year(calendarView.getFirstDate());
                calendarView.setDate(year, m, 1);
                updateMonthYear();
            }
        }
    }//GEN-LAST:event_comboBoxMonthItemStateChanged

    private void buttonNextMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextMonthActionPerformed
        calendarView.nextMonth();
        updateMonthYear();
    }//GEN-LAST:event_buttonNextMonthActionPerformed

    private void buttonNextDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextDayActionPerformed
        calendarView.nextDay();
        updateMonthYear();
    }//GEN-LAST:event_buttonNextDayActionPerformed

    private void buttonBackDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackDayActionPerformed
        calendarView.previousDay();
        updateMonthYear();
    }//GEN-LAST:event_buttonBackDayActionPerformed

    private void buttonBackMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackMonthActionPerformed
        calendarView.previousMonth();
        updateMonthYear();
    }//GEN-LAST:event_buttonBackMonthActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBackDay;
    private javax.swing.JButton buttonBackMonth;
    private javax.swing.JButton buttonBackWeek;
    private javax.swing.JButton buttonNextDay;
    private javax.swing.JButton buttonNextMonth;
    private javax.swing.JButton buttonNextWeek;
    private components.CalendarView calendarView;
    private javax.swing.JComboBox comboBoxMonth;
    private components.DateToday dateToday;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelCanceled;
    private javax.swing.JLabel labelOccupied;
    private javax.swing.JLabel labelReleased;
    private javax.swing.JLabel labelReserved;
    private javax.swing.JMenuItem openReservation;
    private javax.swing.JPanel panelButtonsCalendarView;
    private javax.swing.JPanel panelLegendReservation;
    private javax.swing.JPanel panelUtilDate;
    private javax.swing.JPopupMenu reservationPopup;
    private javax.swing.ButtonGroup reservationStateGroup;
    private javax.swing.JRadioButtonMenuItem stateCanceled;
    private javax.swing.JRadioButtonMenuItem stateOccupied;
    private javax.swing.JRadioButtonMenuItem stateReleased;
    private javax.swing.JRadioButtonMenuItem stateReserved;
    private javax.swing.JTextField txtFieldZoneYear;
    // End of variables declaration//GEN-END:variables

}
