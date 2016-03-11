/*
 * Translator.java
 *
 * Created on 19 December 2006, 15:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package translator;

import translator.ods.OdsReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author maurice
 */
public class Translator {
    public static void main(String args[]) {
        try {
            File file = new File("translation", "demo-db.ods");
            for (int i = 0; i < args.length; ++i) {
                processFile(file, args[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processFile(File file, String language) throws Exception {
        System.err.println("processing " + file);
        File dir = new File("build/translation");
        dir.mkdirs();
        File outFile = new File(dir, "resources_" + language + ".properties");
        OdsReader reader = new OdsReader(file);

        Properties props = new Properties();
        while (reader.readNext()) {
            String key = reader.getKey();
            String value = reader.getValue(language);
            if (key != null && value != null) {
                props.put(key, value);      
            }
        }

        generateResources(props, outFile);
    }

    private static void generateResources(Properties p, File file) 
            throws Exception {
        OutputStream out = new FileOutputStream(file);
        
        try {            
            p.store(out," ");                       
        } finally {
            out.close();
        }
    }
}
