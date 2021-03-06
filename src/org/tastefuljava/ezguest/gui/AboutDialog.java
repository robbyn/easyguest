package org.tastefuljava.ezguest.gui;

import java.awt.Frame;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tastefuljava.ezguest.util.Configuration;

@SuppressWarnings("serial")
public class AboutDialog extends javax.swing.JDialog {
    private static final Log LOG = LogFactory.getLog(AboutDialog.class);   

    public AboutDialog(java.awt.Frame parent) {
        super(parent);
        initComponents();
        initDataDetailComponents();
        initialize(parent);       
    }

    private void initialize(Frame parent) {
        setLocation(parent.getX()+(parent.getWidth()-getWidth())/2,
                parent.getY()+(int)(parent.getWidth()*(0.618/1.618))-getHeight()/2);       
    }
    
    private void initDataDetailComponents() {
        Locale locale = Locale.getDefault();
        try {
            Configuration conf = new Configuration("ezguest.properties");
            conf.load();
            verproduct.setText("EasyGuest " + conf.getString("version", null));        
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        opsystem.setText(System.getProperty("os.name") + " " + 
                         System.getProperty("os.version") + " running on " +
                         System.getProperty("os.arch"));
        verjava.setText(System.getProperty("java.version"));
        virmachine.setText(System.getProperty("java.vm.name") + " " +
                           System.getProperty("java.vm.version"));
        vendor.setText(System.getProperty("java.vendor"));
        dirjava.setText(System.getProperty("java.home"));
        syslocale.setText(locale.getDisplayCountry());        
        username.setText(System.getProperty("user.name"));
        dirinstall.setText(System.getProperty("user.dir"));
        diruser.setText(System.getProperty("user.home"));        
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tabbedPaneAbout = new javax.swing.JTabbedPane();
        tabAbout = new javax.swing.JPanel();
        labeleZGuest = new javax.swing.JLabel();
        labelProgramming = new javax.swing.JLabel();
        programAuthors = new javax.swing.JLabel();
        labelTranslation = new javax.swing.JLabel();
        translationAuthors = new javax.swing.JLabel();
        labelSupport = new javax.swing.JLabel();
        labelFr = new javax.swing.JLabel();
        supportFr = new javax.swing.JLabel();
        addressFr = new javax.swing.JLabel();
        phoneFr = new javax.swing.JLabel();
        emailFr = new javax.swing.JLabel();
        labelCh = new javax.swing.JLabel();
        supportCh = new javax.swing.JLabel();
        phoneCh = new javax.swing.JLabel();
        emailCh = new javax.swing.JLabel();
        addressCh = new javax.swing.JLabel();
        labelIt = new javax.swing.JLabel();
        supportIt = new javax.swing.JLabel();
        addressIt = new javax.swing.JLabel();
        phoneIt = new javax.swing.JLabel();
        emailIt = new javax.swing.JLabel();
        tabDetail = new javax.swing.JPanel();
        labelInfo = new javax.swing.JLabel();
        labelVersion = new javax.swing.JLabel();
        labelOS = new javax.swing.JLabel();
        labelJava = new javax.swing.JLabel();
        labelVM = new javax.swing.JLabel();
        labelVendor = new javax.swing.JLabel();
        labelDirJava = new javax.swing.JLabel();
        labelSysLocale = new javax.swing.JLabel();
        labelUserName = new javax.swing.JLabel();
        labelDirInstall = new javax.swing.JLabel();
        labelDirUser = new javax.swing.JLabel();
        verproduct = new javax.swing.JLabel();
        opsystem = new javax.swing.JLabel();
        verjava = new javax.swing.JLabel();
        virmachine = new javax.swing.JLabel();
        vendor = new javax.swing.JLabel();
        dirjava = new javax.swing.JLabel();
        syslocale = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        dirinstall = new javax.swing.JLabel();
        diruser = new javax.swing.JLabel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources"); // NOI18N
        setTitle(bundle.getString("dialog.about.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        tabbedPaneAbout.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        tabAbout.setLayout(new java.awt.GridBagLayout());

        labeleZGuest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/tastefuljava/ezguest/images/splashEasyguest_1.01.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        tabAbout.add(labeleZGuest, gridBagConstraints);

        labelProgramming.setText(bundle.getString("dialog.about.tababout.labprogramming")); // NOI18N
        labelProgramming.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabAbout.add(labelProgramming, gridBagConstraints);

        programAuthors.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        programAuthors.setText(bundle.getString("dialog.about.tababout.programauthors")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabAbout.add(programAuthors, gridBagConstraints);

        labelTranslation.setText(bundle.getString("dialog.about.tababout.labtranslation")); // NOI18N
        labelTranslation.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabAbout.add(labelTranslation, gridBagConstraints);

        translationAuthors.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        translationAuthors.setText(bundle.getString("dialog.about.tababout.translatauthors")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabAbout.add(translationAuthors, gridBagConstraints);

        labelSupport.setText(bundle.getString("dialog.about.tababout.labsupport")); // NOI18N
        labelSupport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabAbout.add(labelSupport, gridBagConstraints);

        labelFr.setText(bundle.getString("country.fr")); // NOI18N
        labelFr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabAbout.add(labelFr, gridBagConstraints);

        supportFr.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        supportFr.setText(bundle.getString("dialog.about.tababout.supportFr")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabAbout.add(supportFr, gridBagConstraints);

        addressFr.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        addressFr.setText(bundle.getString("dialog.about.tababout.addressFr")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        tabAbout.add(addressFr, gridBagConstraints);

        phoneFr.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        phoneFr.setText(bundle.getString("dialog.about.tababout.phoneFr")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        tabAbout.add(phoneFr, gridBagConstraints);

        emailFr.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        emailFr.setText(bundle.getString("dialog.about.tababout.emailFr")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        tabAbout.add(emailFr, gridBagConstraints);

        labelCh.setText(bundle.getString("country.ch")); // NOI18N
        labelCh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabAbout.add(labelCh, gridBagConstraints);

        supportCh.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        supportCh.setText(bundle.getString("dialog.about.tababout.supportCh")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabAbout.add(supportCh, gridBagConstraints);

        phoneCh.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        phoneCh.setText(bundle.getString("dialog.about.tababout.phoneCh")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        tabAbout.add(phoneCh, gridBagConstraints);

        emailCh.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        emailCh.setText(bundle.getString("dialog.about.tababout.emailCh")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        tabAbout.add(emailCh, gridBagConstraints);

        addressCh.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        addressCh.setText(bundle.getString("dialog.about.tababout.addressCh")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        tabAbout.add(addressCh, gridBagConstraints);

        labelIt.setText(bundle.getString("country.it")); // NOI18N
        labelIt.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabAbout.add(labelIt, gridBagConstraints);

        supportIt.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        supportIt.setText(bundle.getString("dialog.about.tababout.supportIt")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabAbout.add(supportIt, gridBagConstraints);

        addressIt.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        addressIt.setText(bundle.getString("dialog.about.tababout.addressIt")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        tabAbout.add(addressIt, gridBagConstraints);

        phoneIt.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        phoneIt.setText(bundle.getString("dialog.about.tababout.phoneIt")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        tabAbout.add(phoneIt, gridBagConstraints);

        emailIt.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        emailIt.setText("Roberto Picchiottino MBTLC <picchio@mbtlc.it>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        tabAbout.add(emailIt, gridBagConstraints);

        tabbedPaneAbout.addTab(bundle.getString("dialog.about.tababout"), tabAbout); // NOI18N

        tabDetail.setLayout(new java.awt.GridBagLayout());

        labelInfo.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        labelInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/tastefuljava/ezguest/images/ezguest_64.png"))); // NOI18N
        labelInfo.setText(bundle.getString("dialog.about.tabdetail.info")); // NOI18N
        labelInfo.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/org/tastefuljava/ezguest/images/ezguest_64.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        tabDetail.add(labelInfo, gridBagConstraints);

        labelVersion.setText(bundle.getString("dialog.about.tabdetail.labversion")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelVersion, gridBagConstraints);

        labelOS.setText(bundle.getString("dialog.about.tabdetail.labos")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelOS, gridBagConstraints);

        labelJava.setText(bundle.getString("dialog.about.tabdetail.labjava")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelJava, gridBagConstraints);

        labelVM.setText(bundle.getString("dialog.about.tabdetail.labvm")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelVM, gridBagConstraints);

        labelVendor.setText(bundle.getString("dialog.about.tabdetail.labvendor")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelVendor, gridBagConstraints);

        labelDirJava.setText(bundle.getString("dialog.about.tabdetail.labjavahome")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 5);
        tabDetail.add(labelDirJava, gridBagConstraints);

        labelSysLocale.setText(bundle.getString("dialog.about.tabdetail.labsyslocal")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelSysLocale, gridBagConstraints);

        labelUserName.setText(bundle.getString("dialog.about.tabdetail.labusername")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelUserName, gridBagConstraints);

        labelDirInstall.setText(bundle.getString("dialog.about.tabdetail.labdirinstall")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelDirInstall, gridBagConstraints);

        labelDirUser.setText(bundle.getString("dialog.about.tabdetail.labdiruser")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        tabDetail.add(labelDirUser, gridBagConstraints);

        verproduct.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(verproduct, gridBagConstraints);

        opsystem.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(opsystem, gridBagConstraints);

        verjava.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(verjava, gridBagConstraints);

        virmachine.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(virmachine, gridBagConstraints);

        vendor.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(vendor, gridBagConstraints);

        dirjava.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(dirjava, gridBagConstraints);

        syslocale.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(syslocale, gridBagConstraints);

        username.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(username, gridBagConstraints);

        dirinstall.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(dirinstall, gridBagConstraints);

        diruser.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        tabDetail.add(diruser, gridBagConstraints);

        tabbedPaneAbout.addTab(bundle.getString("dialog.about.tabdetail"), tabDetail); // NOI18N

        getContentPane().add(tabbedPaneAbout, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressCh;
    private javax.swing.JLabel addressFr;
    private javax.swing.JLabel addressIt;
    private javax.swing.JLabel dirinstall;
    private javax.swing.JLabel dirjava;
    private javax.swing.JLabel diruser;
    private javax.swing.JLabel emailCh;
    private javax.swing.JLabel emailFr;
    private javax.swing.JLabel emailIt;
    private javax.swing.JLabel labelCh;
    private javax.swing.JLabel labelDirInstall;
    private javax.swing.JLabel labelDirJava;
    private javax.swing.JLabel labelDirUser;
    private javax.swing.JLabel labelFr;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JLabel labelIt;
    private javax.swing.JLabel labelJava;
    private javax.swing.JLabel labelOS;
    private javax.swing.JLabel labelProgramming;
    private javax.swing.JLabel labelSupport;
    private javax.swing.JLabel labelSysLocale;
    private javax.swing.JLabel labelTranslation;
    private javax.swing.JLabel labelUserName;
    private javax.swing.JLabel labelVM;
    private javax.swing.JLabel labelVendor;
    private javax.swing.JLabel labelVersion;
    private javax.swing.JLabel labeleZGuest;
    private javax.swing.JLabel opsystem;
    private javax.swing.JLabel phoneCh;
    private javax.swing.JLabel phoneFr;
    private javax.swing.JLabel phoneIt;
    private javax.swing.JLabel programAuthors;
    private javax.swing.JLabel supportCh;
    private javax.swing.JLabel supportFr;
    private javax.swing.JLabel supportIt;
    private javax.swing.JLabel syslocale;
    private javax.swing.JPanel tabAbout;
    private javax.swing.JPanel tabDetail;
    private javax.swing.JTabbedPane tabbedPaneAbout;
    private javax.swing.JLabel translationAuthors;
    private javax.swing.JLabel username;
    private javax.swing.JLabel vendor;
    private javax.swing.JLabel verjava;
    private javax.swing.JLabel verproduct;
    private javax.swing.JLabel virmachine;
    // End of variables declaration//GEN-END:variables
    
}
