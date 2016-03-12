/*
 * DateToday.java
 *
 * Created on 28 february 2003, 16:08
 */

package org.tastefuljava.ezguest.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class DateToday extends JLabel implements Runnable {
    private volatile Thread timer;
    private String format = "dd/MM/yyyy";
    private SimpleDateFormat formatter = new SimpleDateFormat(format);

    public DateToday() {
        update();
        start();
    }

    public void start() {
        timer = new Thread(this);
        timer.start();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String newValue) {
        format = newValue;
        synchronized(this) {
            formatter = new SimpleDateFormat(newValue);
        }
        update();
    }

    public void stop() {
        timer.interrupt();
    }

    public void run() {
        Thread me = Thread.currentThread();
        while (timer == me) {
            try {
                Thread.currentThread().sleep(300000);
            } catch (InterruptedException e) {
                break;
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    update();
                }
            });
        }
    }

    private void update() {
        synchronized (this) {
            setText(formatter.format(new Date()));
        }
    }
}
