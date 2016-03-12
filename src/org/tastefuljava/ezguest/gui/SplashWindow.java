/*
 * SplashWindow.java
 *
 * Created on 21 January 2004, 13:48
 */

package org.tastefuljava.ezguest.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JWindow;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class SplashWindow extends JWindow {
    
    /** Creates new form SplashWindow */
    public SplashWindow() {
        initComponents();
        Dimension fs = getSize();
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((ss.width-fs.width)/2, (ss.height-fs.height)/2 - 10);
        setVisible(true);
    }

    public void update(int val, String txt) {
        progress.setValue(val);
        progress.setString(txt);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        progress = new javax.swing.JProgressBar();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/splashEasyguest.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        getContentPane().add(jLabel1, gridBagConstraints);

        progress.setMaximum(4);
        progress.setString("-");
        progress.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(progress, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new SplashWindow().setVisible(true);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar progress;
    // End of variables declaration//GEN-END:variables
    
}