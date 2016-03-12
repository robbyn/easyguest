/*
 * Util.java
 *
 * Created on 01 November 2002, 15:42
 */

package org.tastefuljava.ezguest.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.MessageFormat;
import javax.swing.JComponent;
import javax.swing.KeyStroke;


/**
 *
 * @author  Maurice Perry
 */
public class Util {
    public static final int SATURDAY = Calendar.SATURDAY;
    public static final int SUNDAY = Calendar.SUNDAY;

    public static final long DAY_INCREMENT = 1000L*60*60*24;

    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static Locale locale = Locale.getDefault();

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale loc) {
        locale = loc;
        Locale.setDefault(loc);
    }

    public static Date makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1+Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static int daysBetween(Date d1, Date d2) {
        Calendar cal = Calendar.getInstance();
        Calendar utc = Calendar.getInstance(UTC);
        cal.setTime(d1);
        utc.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        utc.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        utc.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        utc.set(Calendar.HOUR_OF_DAY, 0);
        utc.set(Calendar.MINUTE, 0);
        utc.set(Calendar.SECOND, 0);
        utc.set(Calendar.MILLISECOND, 0);
        long tm1 = utc.getTimeInMillis();
        cal.setTime(d2);
        utc.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        utc.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        utc.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        utc.set(Calendar.HOUR_OF_DAY, 0);
        utc.set(Calendar.MINUTE, 0);
        utc.set(Calendar.SECOND, 0);
        utc.set(Calendar.MILLISECOND, 0);
        long tm2 = utc.getTimeInMillis();
        return (int)((tm2-tm1)/DAY_INCREMENT);
    }

    public static Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    public static Date yesterday() {
        return addDays(today(), -1);
    }

    public static Date today() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date tomorrow() {
        return addDays(today(), 1);
    }

    public static int year(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int month(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH)-Calendar.JANUARY+1;
    }

    public static int day(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int dayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean isBlank(String s) {
        return s == null ? true : s.trim().length() == 0;
    }

    public static Date str2date(String s) throws ParseException {
        if (isBlank(s)) {
            return null;
        }
        return dateFormat.parse(s);
    }

    public static String date2str(Date d) {
        return d == null ? null : dateFormat.format(d);
    }

    public static String dbl2str(double d) {
        return decimalFormat.format(d);
    }

    public static String getResource(String name) {
        ResourceBundle bundle = ResourceBundle.getBundle("resources", locale);
        return bundle.getString(name);
    }

    public static String format(String key, Object args[]) {
        MessageFormat format = new MessageFormat(
                Util.getResource(key), Locale.getDefault());
        return format.format(args);
    }

    public static String format(String key, Object arg) {
        return format(key, new Object[] {arg});
    }

    public static String format(String key, Object arg1, Object arg2) {
        return format(key, new Object[] {arg1, arg2});
    }

    public static String format(String key, Object arg1, Object arg2, Object arg3) {
        return format(key, new Object[] {arg1, arg2, arg3});
    }

    public static String toString(KeyStroke ks) {
        StringBuffer buf = new StringBuffer();
        if (ks != null) {
            if (ks.getModifiers() != 0) {
                buf.append(KeyEvent.getKeyModifiersText(ks.getModifiers()));
                buf.append('+');
            }
            buf.append(KeyEvent.getKeyText(ks.getKeyCode()));
        }
        return buf.toString();
    }

    public static void clearWidth(JComponent comp) {
        Dimension size = comp.getMinimumSize();
        size.width = 0;
        comp.setMinimumSize(size);
        size = comp.getPreferredSize();
        size.width = 0;
        comp.setPreferredSize(size);
    }

    public static void clearWidthAll(Container cont, Class clazz) {
        Component children[] = cont.getComponents();
        for (int i = 0; i < children.length; ++i) {
            Component child = children[i];
            if (clazz.isInstance(child)) {
                clearWidth((JComponent)child);
            }
            if (child instanceof Container) {
                clearWidthAll((Container)child, clazz);
            }
        }
    }
}
