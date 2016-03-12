/*
 * HotelDialog.java
 *
 * Created on 8. mars 2003, 09:15
 */

package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.data.Hotel;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.tastefuljava.ezguest.session.EasyguestSession;
import org.tastefuljava.ezguest.util.Util;
import org.tastefuljava.ezguest.gui.InputException;
import java.util.MissingResourceException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.lang.String;
import java.io.File;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class HotelConfigDialog extends javax.swing.JDialog {
    private static Log log = LogFactory.getLog(HotelConfigDialog.class);

    private static final int IMAGE_WIDTH = 300;
    private static final int IMAGE_HEIGHT = 300;

    private EasyguestSession sess;


    public HotelConfigDialog(JFrame parent, EasyguestSession sess) {
        super(parent, true);
        this.sess = sess;
        initComponents();
        initOtherDataComponents();
        getRootPane().setDefaultButton(ok);
        Dimension dim = rate.getPreferredSize();
        dim.setSize(120, dim.height);
        rate.setPreferredSize(dim);
        country.setPreferredSize(dim);
        pack();
        int x, y;
        int w, h;
        if (parent == null) {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            x = 0;
            y = 0;
            w = size.width;
            h = size.height;
        } else {
            x = parent.getX();
            y = parent.getY();
            w = parent.getWidth();
            h = parent.getHeight();
        }
        setLocation(x+(w-getWidth())/2, y+(int)(w*(0.618/1.618))-getHeight()/2);
    }

    public void initOtherDataComponents() {
        fileChooser.setIcon(new BufferedImageIcon(null, IMAGE_WIDTH, IMAGE_HEIGHT));
        fillCombo(rate, "hotel.rate.");
        fillCountryCombo();
        sess.begin();
        try {
            setHotel(sess.getHotel());
        } finally {
            sess.end();
        }
    }

    private void fillCombo(JComboBox combo, String prefix) {
        for(int i = 0; ; i++) {
            try {
                combo.addItem(Util.getResource(prefix + i));
            } catch (MissingResourceException e) {
                break;
            }
        }
    }
    
    private void fillCountryCombo() {
        List<String> countries = new ArrayList<String>();
        for (String iso: Locale.getISOCountries()) {
            try { 
                countries.add(Util.getResource("country." + iso.toLowerCase()));              
            } catch (MissingResourceException e) {
                System.out.println("Missing country " + iso);
            }            
        }
        Collections.sort(countries);
        for(String cntry: countries) {               
            country.addItem(cntry);
        }        
    }    

    private void setHotel(Hotel hotel) {
        if (hotel == null) {
            rate.setSelectedItem(null);
            chain.setText("");
            name.setText("");
            address1.setText("");
            address2.setText("");
            zip.setText("");
            city.setText("");            
            state.setText("");
            country.setSelectedItem(null);
            phone.setText("");
            fax.setText("");
            email.setText("");
            web.setText("");            
            fileChooser.setIcon(new BufferedImageIcon(null, IMAGE_WIDTH, IMAGE_HEIGHT));
        } else {
            rate.setSelectedItem(hotel.getRate());
            chain.setText(hotel.getChain());
            name.setText(hotel.getName());
            address1.setText(hotel.getAddress1());
            address2.setText(hotel.getAddress2());
            zip.setText(hotel.getZip());
            city.setText(hotel.getCity());            
            state.setText(hotel.getState());
            country.setSelectedItem(hotel.getCountry());
            phone.setText(hotel.getTelephone());
            fax.setText(hotel.getFax());
            email.setText(hotel.getEmail());
            web.setText(hotel.getWeb());
            fileChooser.setPressedIcon(null);
            fileChooser.setDisabledIcon(null);
            fileChooser.setSelectedIcon(null);
            fileChooser.setIcon(null);
            fileChooser.setIcon(new BufferedImageIcon(hotel.getLogoImage(), IMAGE_WIDTH, IMAGE_HEIGHT));
            fileChooser.repaint();
        }
    }

    public void save() throws InputException {
        sess.begin(); 
        try {
            Hotel hotel = sess.getHotel();
            if (hotel == null) {
                hotel = new Hotel();
            }
            hotel.setRate((String)rate.getSelectedItem());
            hotel.setChain(chain.getText());
            hotel.setName(name.getText());
            hotel.setAddress1(address1.getText());
            hotel.setAddress2(address2.getText());
            hotel.setZip(zip.getText());
            hotel.setState(state.getText());            
            hotel.setCity(city.getText());
            hotel.setCountry((String)country.getSelectedItem());
            hotel.setTelephone(phone.getText());
            hotel.setFax(fax.getText());
            hotel.setEmail(email.getText());
            hotel.setWeb(web.getText());
            BufferedImageIcon icon = (BufferedImageIcon)fileChooser.getIcon();
            hotel.setLogoImage(icon.getImage());
            sess.update(hotel);
            sess.commit();
        } finally {
            sess.end();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelRate = new javax.swing.JLabel();
        rate = new javax.swing.JComboBox();
        labelChain = new javax.swing.JLabel();
        chain = new javax.swing.JTextField();
        labelName = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        labelAddress = new javax.swing.JLabel();
        address1 = new javax.swing.JTextField();
        address2 = new javax.swing.JTextField();
        labelZipCity = new javax.swing.JLabel();
        zip = new javax.swing.JTextField();
        city = new javax.swing.JTextField();
        labelSateCountry = new javax.swing.JLabel();
        state = new javax.swing.JTextField();
        country = new javax.swing.JComboBox();
        labelPhoneFax = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        fax = new javax.swing.JTextField();
        labelEmailWeb = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        web = new javax.swing.JTextField();
        labelLogo = new javax.swing.JLabel();
        fileChooser = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources"); // NOI18N
        setTitle(bundle.getString("hotel.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        ok.setText(bundle.getString("dialog.ok")); // NOI18N
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 11, 5);
        jPanel2.add(ok, gridBagConstraints);

        cancel.setText(bundle.getString("dialog.cancel")); // NOI18N
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 11, 11);
        jPanel2.add(cancel, gridBagConstraints);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        labelRate.setText(bundle.getString("hotel.rate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        jPanel1.add(labelRate, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 5);
        jPanel1.add(rate, gridBagConstraints);

        labelChain.setText(bundle.getString("hotel.chain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(labelChain, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(chain, gridBagConstraints);

        labelName.setText(bundle.getString("hotel.name")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(labelName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(name, gridBagConstraints);

        labelAddress.setText(bundle.getString("hotel.address")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(labelAddress, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(address1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(address2, gridBagConstraints);

        labelZipCity.setText(bundle.getString("hotel.zip-city")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(labelZipCity, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel1.add(zip, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(city, gridBagConstraints);

        labelSateCountry.setText(bundle.getString("hotel.state-country")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(labelSateCountry, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel1.add(state, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(country, gridBagConstraints);

        labelPhoneFax.setText(bundle.getString("hotel.phone-fax")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(labelPhoneFax, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel1.add(phone, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(fax, gridBagConstraints);

        labelEmailWeb.setText(bundle.getString("hotel.email-web")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(labelEmailWeb, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel1.add(email, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(web, gridBagConstraints);

        labelLogo.setText(bundle.getString("hotel.logo")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(labelLogo, gridBagConstraints);

        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 11);
        jPanel1.add(fileChooser, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        try {
            JFileChooser chooserLogo = new JFileChooser();          
            int open = chooserLogo.showOpenDialog(this);
            if (open == JFileChooser.APPROVE_OPTION) {
                File logoFile = chooserLogo.getSelectedFile();
                BufferedImage image = ImageIO.read(logoFile);
                fileChooser.setIcon(new BufferedImageIcon(image, IMAGE_WIDTH, IMAGE_HEIGHT));
            }
        } catch (Exception e) {
            log.debug(" ",e);
            JOptionPane.showMessageDialog(this,
                    Util.getResource("error.hotel.unknown")
                            + e.getMessage());
        }
    }//GEN-LAST:event_fileChooserActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        closeDialog(null);
    }//GEN-LAST:event_cancelActionPerformed

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        try {
            save();
            closeDialog(null);
        } catch (InputException e) {
            log.debug(" ",e);
            e.showMessage(this);
        } catch (Exception e) {
            log.debug(" ",e);
            JOptionPane.showMessageDialog(this,
                    Util.getResource("error.hotel.unknown")
                            + e.getMessage());
        }
    }//GEN-LAST:event_okActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1;
    private javax.swing.JTextField address2;
    private javax.swing.JButton cancel;
    private javax.swing.JTextField chain;
    private javax.swing.JTextField city;
    private javax.swing.JComboBox country;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fax;
    private javax.swing.JButton fileChooser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelAddress;
    private javax.swing.JLabel labelChain;
    private javax.swing.JLabel labelEmailWeb;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelPhoneFax;
    private javax.swing.JLabel labelRate;
    private javax.swing.JLabel labelSateCountry;
    private javax.swing.JLabel labelZipCity;
    private javax.swing.JTextField name;
    private javax.swing.JButton ok;
    private javax.swing.JTextField phone;
    private javax.swing.JComboBox rate;
    private javax.swing.JTextField state;
    private javax.swing.JTextField web;
    private javax.swing.JTextField zip;
    // End of variables declaration//GEN-END:variables
    
}
