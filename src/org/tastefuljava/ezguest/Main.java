package org.tastefuljava.ezguest;

/*
 * Main.java
 *
 * Created on 01 November 2002, 14:47
 */

/**
 * @author  Maurice Perry
 */
import org.tastefuljava.ezguest.gui.SplashWindow;
import org.tastefuljava.ezguest.gui.FrameEasyguest;
import org.tastefuljava.ezguest.gui.config.HotelConfigDialog;
import java.io.IOException;
import org.tastefuljava.ezguest.util.Configuration;
import org.tastefuljava.ezguest.util.Util;
import org.tastefuljava.ezguest.demo.DemoDb;
import org.tastefuljava.ezguest.components.MultilineToolTipUI;
import javax.swing.UIManager;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.tastefuljava.ezguest.session.EasyguestSession;

public class Main {

    public static void main(String args[]) {
        try {
            Configuration conf = new Configuration("ezguest.properties");           
            conf.load();
            String language = conf.getLanguage("language", "en");
            Locale loc = new Locale(language, Locale.getDefault().getCountry());
            Util.setLocale(loc);

            SplashWindow splashWindow = new SplashWindow();                 

            splashWindow.update(1, Util.getResource("splash.loading.localization"));

            splashWindow.update(2, Util.getResource("splash.loading.config"));

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            MultilineToolTipUI.install();
            splashWindow.update(4, Util.getResource("splash.initializing.mainWindow"));
            splashWindow.dispose();
            checkFirstLaunch(conf);
            EasyguestSession sess = new EasyguestSession();
            boolean hasHotel;
            sess.begin();
            try {
                hasHotel = sess.getHotel() != null;
            } finally {
                sess.end();
            }
            if (!hasHotel) {
                HotelConfigDialog dlg = new HotelConfigDialog(null, sess);
                dlg.setVisible(true);
                sess.begin();
                try {
                    hasHotel = sess.getHotel() != null;
                } finally {
                    sess.end();
                }
                if (!hasHotel) {
                    return;
                }
            }
            FrameEasyguest frame = new FrameEasyguest(conf, sess);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void checkFirstLaunch(Configuration conf) {
        if (conf.getBoolean("first-launch", true)) {
            EasyguestSession.createDb();            
            int confirm = JOptionPane.showConfirmDialog(null, 
                    Util.getResource("dialog.confirm.createdb.query"), 
                    Util.getResource("dialog.confirm.createdb.title"), 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);    
            if (confirm == JOptionPane.YES_OPTION) {
                DemoDb.create();
            }      
            conf.setBoolean("first-launch", false);
            try {
                conf.store();
            } catch(IOException io) {
                io.printStackTrace();
            }    
        }                
    }
}
