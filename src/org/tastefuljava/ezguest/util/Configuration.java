/*
 * Configuration.java
 *
 * Created on 05 March 2005, 13:35
 */

package org.tastefuljava.ezguest.util;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Maurice Perry
 */
public class Configuration implements Serializable {
    private static final long serialVersionUID = 3257852077869839415L;

    private static final DecimalFormat NUMBER_FORMAT;
    
    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        NUMBER_FORMAT = new DecimalFormat("0.####");
        NUMBER_FORMAT.setDecimalFormatSymbols(symbols);
    }

    private Properties props = new Properties();
    private File file;
    
    public Configuration(File file) {
        this.file = file;
    }

    public Configuration(String fileName) {
        this(new File(System.getProperty("user.dir"), fileName));
    }

    public String[] getNames() {
        return props.keySet().toArray(new String[props.size()]);
    }

    public void load() throws IOException {
        props.clear();
        if (file.exists()) {
            InputStream in = new FileInputStream(file);
            try {
                props.load(in);
            } finally {
                in.close();
            }
        }
    }

    public void store() throws IOException {
        OutputStream out = new FileOutputStream(file);
        try {
            props.store(out, "configuration");
        } finally {
            out.close();
        }
    }


    public boolean getBoolean(String name, boolean def) {
        try {
            String value = getString(name, null);
            return isBlank(value) ? def : value.equals("true");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return def;
        }
    }


    public char getChar(String name, char def) {
        String value = getString(name, null);
        return isBlank(value) ? def : value.charAt(0);
    }


    public short getShort(String name, short def) {
        try {
            String value = getString(name, null);
            return isBlank(value) ? def : Short.parseShort(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return def;
        }
    }


    public int getInt(String name, int def) {
        try {
            String value = getString(name, null);
            return isBlank(value) ? def : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return def;
        }
    }


    public long getLong(String name, long def) {
        try {
            String value = getString(name, null);
            return isBlank(value) ? def : Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return def;
        }
    }


    public float getFloat(String name, float def) {
        try {
            String value = getString(name, null);
            return isBlank(value) ? def : Float.parseFloat(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return def;
        }
    }


    public double getDouble(String name, double def) {
        String value = getString(name, null);
        return isBlank(value) ? def : str2dbl(value);
    }


    public String getString(String name, String def) {
        return props.getProperty(name, def);
    }


    public Color getColor(String name, Color def) {
        int rgb = getInt(name, def.getRGB());
        return new Color(rgb);
    }


    public void setBoolean(String name, boolean val) {
        setString(name, val ? "true" : "false");
    }


    public void setChar(String name, char val) {
        setString(name, new String(new char[] {val}));
    }


    public void setShort(String name, short val) {
        setString(name, Short.toString(val));
    }


    public void setInt(String name, int val) {
        setString(name, Integer.toString(val));
    }


    public void setLong(String name, long val) {
        setString(name, Long.toString(val));
    }


    public void setFloat(String name, float val) {
        setString(name, NUMBER_FORMAT.format(val));
    }


    public void setDouble(String name, double val) {
        setString(name, NUMBER_FORMAT.format(val));
    }

    public void setString(String name, String val) {
        if (val != null) {
            props.setProperty(name, val);
        } else {
            props.remove(name);
        }
    }

    public void setColor(String name, Color val) {
        setInt(name, val.getRGB());
    }

    public String getLanguage(String name, String def) {
        String s = getString(name, def).toLowerCase();
        String allLanguages[] = Locale.getISOLanguages();
        if (Arrays.binarySearch(allLanguages, s) >= 0) {
            return s;
        }
        for (String lng: allLanguages) {
            Locale loc = new Locale(lng);
            if (s.equals(loc.getISO3Language())) {
                return lng;
            }
        }
        return def;
    }    

    public String getCountry(String name, String def) {
        String s = getString(name, def).toUpperCase();
        String allCountries[] = Locale.getISOCountries();
        if (Arrays.binarySearch(allCountries, s) >= 0) {
            return s;
        }
        for (String country: allCountries) {
            Locale loc = new Locale("en", country);
            if (s.equals(loc.getISO3Country())) {
                return country;
            }
        }
        return def;
    }

    public static double str2dbl(String s) {
        if (isBlank(s)) {
            return 0;
        } else {
            try {
                return NUMBER_FORMAT.parse(s).doubleValue();
            } catch (ParseException e) {
                throw new NumberFormatException("Invalid number " + s);
            }
        }
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }
}
