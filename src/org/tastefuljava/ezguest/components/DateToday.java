package org.tastefuljava.ezguest.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class DateToday extends JLabel {
    private static final Timer TIMER = new Timer();

    private volatile Thread timer;
    private String format = "dd/MM/yyyy";
    private SimpleDateFormat formatter = new SimpleDateFormat(format);

    public DateToday() {
        update();
        start();
    }

    public final void start() {
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });
            }
        }, 300000, 300000);
    }

    public final void stop() {
        timer.interrupt();
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

    private void update() {
        synchronized (this) {
            setText(formatter.format(new Date()));
        }
    }
}
