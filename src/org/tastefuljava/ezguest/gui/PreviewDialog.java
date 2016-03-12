package org.tastefuljava.ezguest.gui;

import java.awt.Frame;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;

@SuppressWarnings("serial")
public class PreviewDialog extends javax.swing.JDialog {
    private final JRViewer viewer;

    public PreviewDialog(java.awt.Frame parent, JasperPrint print)
            throws JRException {
        super(parent, true);
        viewer = new JRViewer(print);
        initComponents();
        initialize(parent);
    }

    private void initialize(Frame parent) {
        getContentPane().add(viewer, "Center");
        setLocation(parent.getX()+(parent.getWidth()-getWidth())/2,
                parent.getY()+(int)(parent.getWidth()*(0.618/1.618))-getHeight()/2);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources"); // NOI18N
        jButton1.setText(bundle.getString("button.close")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        closeDialog(null);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}
