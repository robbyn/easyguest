/*
 * FrameEasyguest.java
 *
 * Created on 17 juin 2002, 22:06
 */
package org.tastefuljava.ezguest.gui;

import org.tastefuljava.ezguest.gui.config.RoomConfigDialog;
import org.tastefuljava.ezguest.gui.config.HotelConfigDialog;
import org.tastefuljava.ezguest.gui.config.TariffConfigDialog;
import org.tastefuljava.ezguest.gui.config.ArticleConfigDialog;
import org.tastefuljava.ezguest.gui.translator.TranslationDialog;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.DefaultListModel;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.tastefuljava.ezguest.session.EasyguestSession;
import javax.help.CSH;
import javax.help.HelpSet;
import javax.help.HelpBroker;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.tastefuljava.ezguest.util.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author  Denis Trimaille
 */
@SuppressWarnings("serial")
public class FrameEasyguest extends javax.swing.JFrame {
    private static final Log log = LogFactory.getLog(FrameEasyguest.class);

    private boolean initialized;
    private Configuration conf;
    private EasyguestSession sess;
    private DefaultListModel rubricListModel; 
    private HelpSet mainHS = null;
    private HelpBroker mainHB;
    private Map<String,Class<? extends JPanel>> classMap
            = new HashMap<String,Class<? extends JPanel>>();
    private Map<String,JPanel> panelMap = new HashMap<String,JPanel>();

    public FrameEasyguest(Configuration conf, EasyguestSession sess) {
        this.conf = conf;
        this.sess = sess;
        initComponents();
        listRubrics.setCellRenderer(new RubricListCellRenderer());
        listRubrics.setModel(new DefaultListModel());

        addPanel("summary", SummaryPanel.class);
        addPanel("map", MapPanel.class);
        addPanel("invoice", InvoicePanel.class);
        listRubrics.setSelectedIndex(-1);
        pack();      
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = conf.getInt("main.width", size.width*4/5);
        int height = conf.getInt("main.height", size.height*4/5);
        int left = conf.getInt("main.left", (size.width-width)/2);
        int top = conf.getInt("main.top", (size.height-height)/2);
        if (width > size.width) {
            width = size.width;
        }
        if (height > size.height) {
            height = size.height;
        }
        if (left+width > size.width) {
            left = size.width-width;
        }
        if (top+height > size.height) {
            top = size.height-height;
        }
        setBounds(left, top, width, height);
        createHelp();
        listRubrics.setSelectedIndex(0);
        initialized = true;
    }

    private void createHelp() {
	try {
	    URL url = HelpSet.findHelpSet(null, "help/ezh");
            mainHS = new HelpSet(null, url);
	} catch (Exception ee) {
            ee.printStackTrace();
	    return;
	} catch (ExceptionInInitializerError ex) {
	    System.err.println("initialization error:");
	    ex.getException().printStackTrace();
	}
	mainHB = mainHS.createHelpBroker();
    }        

    private void addPanel(String name, Class<? extends JPanel> clazz) {
        classMap.put(name, clazz);
        DefaultListModel model = (DefaultListModel)listRubrics.getModel();
        model.addElement(name);
    }

    private JPanel getPanel(String name)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        JPanel panel = panelMap.get(name);
        if (panel == null) {
            Class<? extends JPanel> clazz = classMap.get(name);
            Constructor cons = clazz.getConstructor(EasyguestSession.class);
            panel = (JPanel)cons.newInstance(sess);
            panelContentRubric.add(panel, name);
            panelMap.put(name, panel);
        }
        return panel;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelContentRubric = new javax.swing.JPanel();
        nullPanel = new javax.swing.JPanel();
        scrollPaneRubrics = new javax.swing.JScrollPane();
        listRubrics = new javax.swing.JList();
        barMenu = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        itemPreview = new javax.swing.JMenuItem();
        itemPrint = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        quit = new javax.swing.JMenuItem();
        menuConfig = new javax.swing.JMenu();
        typeRoomConfig = new javax.swing.JMenuItem();
        tariffPeriodConfig = new javax.swing.JMenuItem();
        categoryArticleConfig = new javax.swing.JMenuItem();
        hotelConfig = new javax.swing.JMenuItem();
        translation = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemContent = new javax.swing.JMenuItem();
        menuItemWhat = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        menuItemIntro = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        menuItemAbout = new javax.swing.JMenuItem();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources"); // NOI18N
        setTitle(bundle.getString("main.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        panelContentRubric.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panelContentRubric.setLayout(new java.awt.CardLayout());
        panelContentRubric.add(nullPanel, "nullPanel");

        getContentPane().add(panelContentRubric, java.awt.BorderLayout.CENTER);

        scrollPaneRubrics.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPaneRubrics.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneRubrics.setMaximumSize(new java.awt.Dimension(120, 410));
        scrollPaneRubrics.setMinimumSize(new java.awt.Dimension(120, 410));
        scrollPaneRubrics.setPreferredSize(new java.awt.Dimension(120, 410));

        listRubrics.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listRubrics.setMinimumSize(new java.awt.Dimension(120, 0));
        listRubrics.setPreferredSize(new java.awt.Dimension(120, 0));
        listRubrics.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listRubricsValueChanged(evt);
            }
        });
        scrollPaneRubrics.setViewportView(listRubrics);

        getContentPane().add(scrollPaneRubrics, java.awt.BorderLayout.WEST);

        menuFile.setText(bundle.getString("menu.file")); // NOI18N
        menuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileActionPerformed(evt);
            }
        });

        itemPreview.setText(bundle.getString("menu.file.preview")); // NOI18N
        itemPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPreviewActionPerformed(evt);
            }
        });
        menuFile.add(itemPreview);

        itemPrint.setText(bundle.getString("menu.file.print")); // NOI18N
        itemPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPrintActionPerformed(evt);
            }
        });
        menuFile.add(itemPrint);

        jSeparator1.setBackground(javax.swing.UIManager.getDefaults().getColor("MenuItem.background"));
        jSeparator1.setForeground(javax.swing.UIManager.getDefaults().getColor("MenuItem.foreground"));
        menuFile.add(jSeparator1);

        quit.setText(bundle.getString("menu.file.quit")); // NOI18N
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitActionPerformed(evt);
            }
        });
        menuFile.add(quit);

        barMenu.add(menuFile);

        menuConfig.setText(bundle.getString("menu.config")); // NOI18N

        typeRoomConfig.setText(bundle.getString("menu.config.rooms")); // NOI18N
        typeRoomConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeRoomConfigActionPerformed(evt);
            }
        });
        menuConfig.add(typeRoomConfig);

        tariffPeriodConfig.setText(bundle.getString("menu.config.tariff")); // NOI18N
        tariffPeriodConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tariffPeriodConfigActionPerformed(evt);
            }
        });
        menuConfig.add(tariffPeriodConfig);

        categoryArticleConfig.setText(bundle.getString("menu.config.articles")); // NOI18N
        categoryArticleConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryArticleConfigActionPerformed(evt);
            }
        });
        menuConfig.add(categoryArticleConfig);

        hotelConfig.setMnemonic('h');
        hotelConfig.setText(bundle.getString("menu.config.hotel")); // NOI18N
        hotelConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotelConfigActionPerformed(evt);
            }
        });
        menuConfig.add(hotelConfig);

        translation.setText(bundle.getString("menu.config.translation")); // NOI18N
        translation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationActionPerformed(evt);
            }
        });
        menuConfig.add(translation);

        barMenu.add(menuConfig);

        menuHelp.setText(bundle.getString("menu.help")); // NOI18N

        menuItemContent.setText(bundle.getString("menu.help.content")); // NOI18N
        menuItemContent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemContentActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemContent);

        menuItemWhat.setText(bundle.getString("menu.help.what")); // NOI18N
        menuHelp.add(menuItemWhat);

        jSeparator3.setBackground(javax.swing.UIManager.getDefaults().getColor("MenuItem.background"));
        jSeparator3.setForeground(javax.swing.UIManager.getDefaults().getColor("MenuItem.foreground"));
        menuHelp.add(jSeparator3);

        menuItemIntro.setText(bundle.getString("menu.help.introduction")); // NOI18N
        menuHelp.add(menuItemIntro);

        jSeparator4.setBackground(javax.swing.UIManager.getDefaults().getColor("MenuItem.background"));
        jSeparator4.setForeground(javax.swing.UIManager.getDefaults().getColor("MenuItem.foreground"));
        menuHelp.add(jSeparator4);

        menuItemAbout.setText(bundle.getString("menu.help.about")); // NOI18N
        menuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemAbout);

        barMenu.add(menuHelp);

        setJMenuBar(barMenu);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        if (initialized) {
            try {
                conf.setInt("main.left", this.getX());
                conf.setInt("main.top", this.getY());
                conf.store();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_formComponentMoved

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (initialized) {
            try {
                conf.setInt("main.width", this.getWidth());
                conf.setInt("main.height", this.getHeight());
                conf.store();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_formComponentResized

    private void translationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationActionPerformed
        try {
            new TranslationDialog(this).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_translationActionPerformed

    private void menuItemContentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemContentActionPerformed
        new CSH.DisplayHelpFromSource(mainHB).actionPerformed(evt);
    }//GEN-LAST:event_menuItemContentActionPerformed

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAboutActionPerformed
        new AboutDialog(this).setVisible(true);
    }//GEN-LAST:event_menuItemAboutActionPerformed

    private void categoryArticleConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryArticleConfigActionPerformed
        new ArticleConfigDialog(this, sess).setVisible(true);
    }//GEN-LAST:event_categoryArticleConfigActionPerformed

    private void tariffPeriodConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tariffPeriodConfigActionPerformed
        new TariffConfigDialog(this, sess).setVisible(true);
    }//GEN-LAST:event_tariffPeriodConfigActionPerformed

    private void hotelConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotelConfigActionPerformed
        new HotelConfigDialog(this, sess).setVisible(true);
    }//GEN-LAST:event_hotelConfigActionPerformed

    private void typeRoomConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeRoomConfigActionPerformed
        new RoomConfigDialog(this, sess).setVisible(true);
    }//GEN-LAST:event_typeRoomConfigActionPerformed
        
    private void listRubricsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listRubricsValueChanged
        if (!evt.getValueIsAdjusting()) {
            try {
                CardLayout layout = (CardLayout)panelContentRubric.getLayout();
                String name = (String)listRubrics.getSelectedValue();
                if (name == null) {
                    name = "nullPanel";
                } else {
                    getPanel(name);
                }
                layout.show(panelContentRubric, name);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_listRubricsValueChanged

    private void itemPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPrintActionPerformed
        try {
            InvoicePanel panel = (InvoicePanel)getPanel("invoice");
            panel.print();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_itemPrintActionPerformed

    private void itemPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPreviewActionPerformed
        try {
            InvoicePanel panel = (InvoicePanel)getPanel("invoice");
            panel.preview();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_itemPreviewActionPerformed

    private void quitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitActionPerformed
        quit();
    }//GEN-LAST:event_quitActionPerformed

    private void menuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_menuFileActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        quit();
    }//GEN-LAST:event_exitForm

    private void quit() {
        if (sess != null) {
            sess.close();
        }
        System.exit(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barMenu;
    private javax.swing.JMenuItem categoryArticleConfig;
    private javax.swing.JMenuItem hotelConfig;
    private javax.swing.JMenuItem itemPreview;
    private javax.swing.JMenuItem itemPrint;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JList listRubrics;
    private javax.swing.JMenu menuConfig;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemContent;
    private javax.swing.JMenuItem menuItemIntro;
    private javax.swing.JMenuItem menuItemWhat;
    private javax.swing.JPanel nullPanel;
    private javax.swing.JPanel panelContentRubric;
    private javax.swing.JMenuItem quit;
    private javax.swing.JScrollPane scrollPaneRubrics;
    private javax.swing.JMenuItem tariffPeriodConfig;
    private javax.swing.JMenuItem translation;
    private javax.swing.JMenuItem typeRoomConfig;
    // End of variables declaration//GEN-END:variables
}
