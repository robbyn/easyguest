/*
 * TariffConfigDialog.java
 *
 * Created on 09 March 2003, 19:49
 */

package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.gui.DateCellEditor;
import org.tastefuljava.ezguest.data.Tariff;
import org.tastefuljava.ezguest.data.Period;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.util.Date;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tastefuljava.ezguest.components.ColorIcon;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import org.tastefuljava.ezguest.util.Util;
//import gui.config.TariffComboBoxModel;
//import gui.config.TariffComboBoxRenderer;

/**
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class TariffConfigDialog extends javax.swing.JDialog {
    private static final Log log = LogFactory.getLog(TariffConfigDialog.class);

    private EasyguestSession sess;
    private TariffTableModel tariffTableModel;
    private TariffRenderer tariffRenderer;
    private PeriodTableModel periodTableModel;
    private PeriodRenderer periodRenderer;
    private TariffComboBoxRenderer tariffComboBoxRenderer;
    private TariffComboBoxModel tariffComboBoxModel;    

    public TariffConfigDialog(JFrame parent, EasyguestSession sess) {
        super(parent, true);
        this.sess = sess;
        initComponents();
        sess.begin();
        try {
            tariffRenderer = new TariffRenderer();
            tariffTableModel = new TariffTableModel(sess);
            tariffTable.setModel(tariffTableModel);
            int tariffNumColumn = tariffTable.getColumnCount();
            for (int i = 0; i < tariffNumColumn; i++) {
                tariffTable.getColumnModel().getColumn(i).setCellRenderer(tariffRenderer);
            }
            tariffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);            
            tariffTable.setDefaultEditor(Color.class, new ColorCellEditor());
            tariffScrollPane.getViewport().setBackground(tariffTable.getBackground());
        
            periodRenderer = new PeriodRenderer();
            periodTableModel = new PeriodTableModel(sess, tariffTableModel);
            periodTable.setModel(periodTableModel);
            int periodNumColumn = periodTable.getColumnCount();
            for (int i = 0; i < periodNumColumn; i++) {
                periodTable.getColumnModel().getColumn(i).setCellRenderer(periodRenderer);
            }
            periodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);            
            periodTable.setDefaultEditor(Date.class, new DateCellEditor());
            periodTable.setDefaultEditor(Tariff.class, new TariffCellEditor(sess, tariffTableModel));
            periodScrollPane.getViewport().setBackground(periodTable.getBackground());            
            
            tariffComboBoxRenderer = new TariffComboBoxRenderer();
            tariffComboBoxModel = new TariffComboBoxModel(sess, tariffTableModel);
            tariffCombo.setModel(tariffComboBoxModel);
            tariffCombo.setRenderer(tariffComboBoxRenderer);
        } finally {
            sess.end();
        }
        setLocation(parent.getX()+(parent.getWidth()-getWidth())/2,
                parent.getY()+(int)(parent.getWidth()*(0.618/1.618))-getHeight()/2);        
        
        tariffTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                tariffChanged(tariffTable.getSelectedRow());
            }
        });        
        
        periodTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                periodChanged(periodTable.getSelectedRow());
            }
        });
        Util.clearWidthAll(this, JTextField.class);
        Util.clearWidthAll(this, JComboBox.class);
        pack();
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tariffperiodsTabbedPane = new javax.swing.JTabbedPane();
        tariffsPanel = new javax.swing.JPanel();
        tariffScrollPane = new javax.swing.JScrollPane();
        tariffTable = new javax.swing.JTable();
        formTariffPanel = new javax.swing.JPanel();
        formTariffsPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        factorLabel = new javax.swing.JLabel();
        factor = new javax.swing.JTextField();
        colorLabel = new javax.swing.JLabel();
        color = new org.tastefuljava.ezguest.components.ColorButton();
        buttonsTariffPanel = new javax.swing.JPanel();
        newTariffButton = new javax.swing.JButton();
        delTariffButton = new javax.swing.JButton();
        periodsPanel = new javax.swing.JPanel();
        periodScrollPane = new javax.swing.JScrollPane();
        periodTable = new javax.swing.JTable();
        formPeriodPanel = new javax.swing.JPanel();
        formPeriodsPanel = new javax.swing.JPanel();
        fromdateLabel = new javax.swing.JLabel();
        fromdate = new javax.swing.JTextField();
        todateLabel = new javax.swing.JLabel();
        todate = new javax.swing.JTextField();
        tariffLabel = new javax.swing.JLabel();
        tariffCombo = new javax.swing.JComboBox();
        buttonsPeriodPanel = new javax.swing.JPanel();
        newPeriodButton = new javax.swing.JButton();
        delPeriodButton = new javax.swing.JButton();
        tariffperiodsClosePanel = new javax.swing.JPanel();
        close = new javax.swing.JButton();

        setTitle(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("tariff.dialog.title"));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        tariffperiodsTabbedPane.setRequestFocusEnabled(false);
        tariffsPanel.setLayout(new java.awt.BorderLayout());

        tariffScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tariffTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        tariffScrollPane.setViewportView(tariffTable);

        tariffsPanel.add(tariffScrollPane, java.awt.BorderLayout.CENTER);

        formTariffPanel.setLayout(new java.awt.BorderLayout());

        formTariffsPanel.setLayout(new java.awt.GridBagLayout());

        formTariffsPanel.setMinimumSize(new java.awt.Dimension(291, 90));
        formTariffsPanel.setPreferredSize(new java.awt.Dimension(291, 90));
        nameLabel.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("tariffperiods.dialog.tariff.name"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        formTariffsPanel.add(nameLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 11);
        formTariffsPanel.add(name, gridBagConstraints);

        factorLabel.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("tariffperiods.dialog.tariff.factor"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        formTariffsPanel.add(factorLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        formTariffsPanel.add(factor, gridBagConstraints);

        colorLabel.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("tariffperiods.dialog.tariff.color"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 11, 11);
        formTariffsPanel.add(colorLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 11);
        formTariffsPanel.add(color, gridBagConstraints);

        formTariffPanel.add(formTariffsPanel, java.awt.BorderLayout.CENTER);

        buttonsTariffPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        newTariffButton.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("tariffperiods.dialog.tariffs.button.new"));
        newTariffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTariffButtonActionPerformed(evt);
            }
        });

        buttonsTariffPanel.add(newTariffButton);

        delTariffButton.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("tariffperiods.dialog.tariffs.button.delete"));
        buttonsTariffPanel.add(delTariffButton);

        formTariffPanel.add(buttonsTariffPanel, java.awt.BorderLayout.SOUTH);

        tariffsPanel.add(formTariffPanel, java.awt.BorderLayout.SOUTH);

        tariffperiodsTabbedPane.addTab(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("tariff.dialog.tab.tariffs"), tariffsPanel);

        periodsPanel.setLayout(new java.awt.BorderLayout());

        periodScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        periodTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        periodScrollPane.setViewportView(periodTable);

        periodsPanel.add(periodScrollPane, java.awt.BorderLayout.CENTER);

        formPeriodPanel.setLayout(new java.awt.BorderLayout());

        formPeriodsPanel.setLayout(new java.awt.GridBagLayout());

        formPeriodsPanel.setMinimumSize(new java.awt.Dimension(291, 90));
        formPeriodsPanel.setPreferredSize(new java.awt.Dimension(291, 90));
        fromdateLabel.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("periods.dialog.period.fromdate"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        formPeriodsPanel.add(fromdateLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 90;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 11);
        formPeriodsPanel.add(fromdate, gridBagConstraints);

        todateLabel.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("periods.dialog.period.todate"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        formPeriodsPanel.add(todateLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 90;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        formPeriodsPanel.add(todate, gridBagConstraints);

        tariffLabel.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("periods.dialog.period.tariff"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 11, 11);
        formPeriodsPanel.add(tariffLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 11);
        formPeriodsPanel.add(tariffCombo, gridBagConstraints);

        formPeriodPanel.add(formPeriodsPanel, java.awt.BorderLayout.CENTER);

        buttonsPeriodPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        newPeriodButton.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("periods.dialog.periods.button.new"));
        newPeriodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPeriodButtonActionPerformed(evt);
            }
        });

        buttonsPeriodPanel.add(newPeriodButton);

        delPeriodButton.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("periods.dialog.periods.button.delete"));
        delPeriodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delPeriodButtonActionPerformed(evt);
            }
        });

        buttonsPeriodPanel.add(delPeriodButton);

        formPeriodPanel.add(buttonsPeriodPanel, java.awt.BorderLayout.SOUTH);

        periodsPanel.add(formPeriodPanel, java.awt.BorderLayout.SOUTH);

        tariffperiodsTabbedPane.addTab(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("tariff.dialog.tab.periods"), periodsPanel);

        getContentPane().add(tariffperiodsTabbedPane, java.awt.BorderLayout.CENTER);

        tariffperiodsClosePanel.setLayout(new java.awt.GridBagLayout());

        tariffperiodsClosePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        close.setText(java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources").getString("dialog.close"));
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 11, 11);
        tariffperiodsClosePanel.add(close, gridBagConstraints);

        getContentPane().add(tariffperiodsClosePanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void delPeriodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delPeriodButtonActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_delPeriodButtonActionPerformed

    private void newPeriodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPeriodButtonActionPerformed
        setPeriod(new Period());
    }//GEN-LAST:event_newPeriodButtonActionPerformed

    private void newTariffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTariffButtonActionPerformed
        setTariff(new Tariff());
    }//GEN-LAST:event_newTariffButtonActionPerformed

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        closeDialog(null);
    }//GEN-LAST:event_closeActionPerformed

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPeriodPanel;
    private javax.swing.JPanel buttonsTariffPanel;
    private javax.swing.JButton close;
    private org.tastefuljava.ezguest.components.ColorButton color;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JButton delPeriodButton;
    private javax.swing.JButton delTariffButton;
    private javax.swing.JTextField factor;
    private javax.swing.JLabel factorLabel;
    private javax.swing.JPanel formPeriodPanel;
    private javax.swing.JPanel formPeriodsPanel;
    private javax.swing.JPanel formTariffPanel;
    private javax.swing.JPanel formTariffsPanel;
    private javax.swing.JTextField fromdate;
    private javax.swing.JLabel fromdateLabel;
    private javax.swing.JTextField name;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton newPeriodButton;
    private javax.swing.JButton newTariffButton;
    private javax.swing.JScrollPane periodScrollPane;
    private javax.swing.JTable periodTable;
    private javax.swing.JPanel periodsPanel;
    private javax.swing.JComboBox tariffCombo;
    private javax.swing.JLabel tariffLabel;
    private javax.swing.JScrollPane tariffScrollPane;
    private javax.swing.JTable tariffTable;
    private javax.swing.JPanel tariffperiodsClosePanel;
    private javax.swing.JTabbedPane tariffperiodsTabbedPane;
    private javax.swing.JPanel tariffsPanel;
    private javax.swing.JTextField todate;
    private javax.swing.JLabel todateLabel;
    // End of variables declaration//GEN-END:variables
    
    private void setTariff(Tariff tariff) {      
        if (tariff != null) {
            name.setText(tariff.getName());
            factor.setText(Util.dbl2str(tariff.getFactor()));
            color.setColor(tariff.getColor());                                    
        } else {
            name.setText("");
            factor.setText("");
            color.setColor(Color.BLACK);                                    
        }                    
    }
    
    private void setPeriod(Period period) {
        if (period != null) {
            fromdate.setText(Util.date2str(period.getFromDate()));
            todate.setText(Util.date2str(period.getToDate()));     
            tariffCombo.setSelectedItem(period.getTariff());
        }  else {
            fromdate.setText("");
            todate.setText("");     
            tariffCombo.setSelectedItem(null); 
        }
        /** don't know why but a repaint is necessary */
        tariffCombo.repaint();
    }
    
    private void tariffChanged(int index) {
        setTariff(tariffTableModel.getTariff(index));
    }

    private void periodChanged(int index) {
        setPeriod(periodTableModel.getPeriod(index));
    }
}


