/*
 * Compile.java
 *
 * Created on 01 February 2003, 16:31
 */

package org.tastefuljava.ezguest.reports;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author  Maurice Perry
 */
public class Compile {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            try {
                String srcName = args[i];
                String dstName = JasperCompileManager.compileReportToFile(srcName);
                System.out.println(srcName + " compiled to " + dstName);
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
    }
}
