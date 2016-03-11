/*
 * InputException.java
 *
 * Created on 28 January 2003, 20:18
 */

package gui;

import java.util.Locale;
import java.text.MessageFormat;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JOptionPane;
import util.Util;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class InputException extends Exception {
    private Component comp;

    public InputException(Component comp, String key, Object args[]) {
        super(formatMsg(key, args));
        this.comp = comp;
    }

    public InputException(Component comp, String key) {
        this(comp, key, new Object[] {});
    }

    public InputException(Component comp, String key, Object p1) {
        this(comp, key, new Object[] {p1});
    }

    public InputException(Component comp, String key, Object p1, Object p2) {
        this(comp, key, new Object[] {p1, p2});
    }

    public InputException(Component comp, String key, Object p1, Object p2,
            Object p3) {
        this(comp, key, new Object[] {p1, p2, p3});
    }

    public void showMessage(Component frame) {
        JOptionPane.showMessageDialog(frame, getMessage(),
                Util.getResource("input-error.title"), JOptionPane.ERROR_MESSAGE);
        comp.requestFocus();
    }

    private static String formatMsg(String key, Object args[]) {
        String pattern = Util.getResource(key);
        MessageFormat format = new MessageFormat(pattern, Locale.getDefault());
        return format.format(args);
    }
}
