package org.tastefuljava.ezguest.gui.translator;

import org.tastefuljava.ezguest.util.Util;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class TranslationTableModel extends AbstractTableModel {
    public static final int COLUMN_KEY_LANG         = 0;
    public static final int COLUMN_DEFAULT_LANG     = 1;
    public static final int COLUMN_TRANSLATE_LANG   = 2;

    private List<String> keys;
    private Properties defaultProps = new Properties();
    private Properties translatProps = new Properties();
    private File file;
    private boolean hasChanged = false;

    public TranslationTableModel() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            try (InputStream in = cl.getResourceAsStream(
                    "/org/tastefuljava/ezguest/resources.properties")) {
                defaultProps.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        keys = new ArrayList<>();
        for (Object o: defaultProps.keySet()) {
            keys.add((String)o);
        }
        Collections.sort(keys);
    }

    public void load(String language) throws IOException {
        translatProps.clear();
        File dir = new File("lib/translation");
        file = new File(dir, "resources_" + language + ".properties");
        if (file.isFile()) {
            try (InputStream in = new FileInputStream(file)) {
                translatProps.load(in);
            }
        }
        Set<String> set = new HashSet<>();
        for (Object o: defaultProps.keySet()) {
            set.add((String)o);
        }
        for (Object o: translatProps.keySet()) {
            set.add((String)o);
        }
        keys = new ArrayList<>(set);
        Collections.sort(keys);
        hasChanged = false;
        fireTableDataChanged();
    }

    public void save() throws IOException {
        if (file != null && hasChanged) {
            try (OutputStream out = new FileOutputStream(file)) {
                for (String key: keys) {
                    if (!defaultProps.containsKey(key)) {
                        translatProps.remove(key);
                    }
                }
                translatProps.store(out, "EasyGuest translation");
                hasChanged = false;
            }
        }
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return keys.size();
    }
    
    public boolean isDeleted(int row) { 
        return !defaultProps.containsKey(keys.get(row));
    }    
        
    public boolean isAdded(int row) {
         return !translatProps.containsKey(keys.get(row));
     }
    
    @Override
    public String getColumnName(int index) {
        switch (index) {
            case COLUMN_KEY_LANG:
                return Util.getResource("translation.column.keyLang");
            case COLUMN_DEFAULT_LANG:
                return Util.getResource("translation.column.defaultLang");
            case COLUMN_TRANSLATE_LANG:
                return Util.getResource("translation.column.translateLang");
        }
        return null;
    }        

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String key = keys.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_KEY_LANG:
                return key;
            case COLUMN_DEFAULT_LANG:
                return defaultProps.getProperty(key, "");
            case COLUMN_TRANSLATE_LANG:
                return translatProps.getProperty(key, "");
        }
        return null;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_KEY_LANG:
                return String.class;
            case COLUMN_DEFAULT_LANG:
                return String.class;
            case COLUMN_TRANSLATE_LANG:
                return String.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COLUMN_KEY_LANG:
                return false;
            case COLUMN_DEFAULT_LANG:
                return false;
            case COLUMN_TRANSLATE_LANG:
                String key = keys.get(rowIndex);
                return defaultProps.containsKey(key);
        }        
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COLUMN_KEY_LANG:
                break;
            case COLUMN_DEFAULT_LANG:
                break;
            case COLUMN_TRANSLATE_LANG:
                String key = keys.get(rowIndex);
                String s = (String)value;
                if (s != null) {
                    s = s.trim();
                    if (s.length() == 0) {
                        s = null;
                    }
                }
                if (s == null) {
                    hasChanged = translatProps.getProperty(key) != null;
                } else {
                    hasChanged = !s.equals(translatProps.getProperty(key));
                }
                translatProps.setProperty(key, (String)value);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
