/*
 * Copyright (C) 2006-2007, Maurice Perry, Denis Trimaille
 * All rights reserved
 */
package translator.ods;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 * @author denis
 */
public class OdsReader {
    private Iterator rowIterator;
    private String key;
    private Map<String,String> values = new HashMap<String,String>();

    private int keyCol = -1;
    private Map<String,Integer> languageCols = new HashMap<String,Integer>();

    public OdsReader(File file) throws Exception {
        this(importDocument(file));
    }

    public OdsReader(InputStream in) throws Exception {
        this(importDocument(in));
    }

    private OdsReader(Element table) throws Exception {
        rowIterator = getTableRows(table).iterator();
        String columns[] = null;
        while (columns == null && rowIterator.hasNext()) {
            columns = getRowCells((Element)rowIterator.next());
        }
        if (columns == null) {
            throw new Exception("No column definition");
        }
        for (int j = 0; j < columns.length; j++) {
            if (columns[j] != null) {
                String matchVal = columns[j].trim().toLowerCase();
                if (matchVal.startsWith("key")) {
                    keyCol = j;
                } else if (matchVal.length() == 2) {
                    languageCols.put(matchVal, j);
                }                 
            }
        }
    }

    public boolean readNext() throws JaxenException {
        String columns[] = null;
        while (columns == null && rowIterator.hasNext()) {
            columns = getRowCells((Element)rowIterator.next());
        }
        if (columns == null) {
            return false;
        }
        decodeColumns(columns);
        return true;
    }

    public String getKey() {
        return key;
    }

    public String[] getLanguages() {
        return languageCols.keySet().toArray(new String[languageCols.size()]);
    }

    public String getValue(String language) {
        return values.get(language);
    }

    private void decodeColumns(String columns[]) {
        key = null;
        values.clear();
        for (int i = 0; i < columns.length; ++i) {
            String value = columns[i];
            if (value != null) {
                if (i == keyCol) {
                    key = value;
                } else  {
                    for (String language: getLanguages()) {
                        if (languageCols.get(language) == i) {
                            values.put(language, value);
                        }
                    }
                } 
            }
        }
    }

    private static Element importDocument(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        try {
            return importDocument(is);
        } finally {
            is.close();
        }
    }

    private static Element importDocument(InputStream is) throws Exception {
        JarInputStream jis = new JarInputStream(is);
        for (JarEntry je = jis.getNextJarEntry(); je != null;
                je = jis.getNextJarEntry()) {
            if (je.getName().equalsIgnoreCase("content.xml")) {
                DocumentBuilder builder;
                DocumentBuilderFactory factory = DocumentBuilderFactory
                            .newInstance();
                factory.setNamespaceAware(true);
                builder = factory.newDocumentBuilder();
                Document document = builder.parse(jis);             

                XPath colPath = new DOMXPath("table:table-cell");
                colPath.addNamespace("office",
                    "urn:oasis:names:tc:opendocument:xmlns:office:1.0");
                colPath.addNamespace("table",
                    "urn:oasis:names:tc:opendocument:xmlns:table:1.0");
                return getTable(document,1,null);
            }   
        }
        return null;
    }

    private static List getTableRows(Element table) throws JaxenException {
        XPath rowPath = new DOMXPath("table:table-row");
        rowPath.addNamespace("office",
                "urn:oasis:names:tc:opendocument:xmlns:office:1.0");
        rowPath.addNamespace("table",
                "urn:oasis:names:tc:opendocument:xmlns:table:1.0");
        return rowPath.selectNodes(table);
    }

    private static String[] getRowCells(Element row) throws JaxenException {
        XPath colPath = new DOMXPath("table:table-cell");
        colPath.addNamespace("office",
                "urn:oasis:names:tc:opendocument:xmlns:office:1.0");
        colPath.addNamespace("table",
                "urn:oasis:names:tc:opendocument:xmlns:table:1.0");
        boolean somethingFound = false;
        List cols = colPath.selectNodes(row);
        List<String> result = new ArrayList<String>();
        int ix = 0;
        for (Iterator it = cols.iterator(); it.hasNext(); ) {
            Element col = (Element)it.next();
            String sRepeat = col.getAttributeNS(
                    "urn:oasis:names:tc:opendocument:xmlns:table:1.0",
                    "number-columns-repeated");
            int repeat = (sRepeat == null || sRepeat.length() == 0)
                    ? 1 : Integer.parseInt(sRepeat);
            String val = col.hasChildNodes()
                    ? col.getFirstChild().getFirstChild().getNodeValue()
                    : null;
            if (val != null) {
                val = val.trim();
                if (val.length() == 0) {
                    val = null;
                } else {
                    somethingFound = true;
                }
            }
            for (int j = 0; j < repeat; j++) {
                result.add(val);
            }
        }
        return somethingFound
                ? result.toArray(new String[result.size()])
                : null;
    }

    private static Element getTable(Document doc,int tableNum,String name) 
            throws JaxenException {
        XPath tablePath = new DOMXPath(
                "/office:document-content/office:body/office:spreadsheet/table:table[" + tableNum + "]");
        tablePath.addNamespace("office",
                "urn:oasis:names:tc:opendocument:xmlns:office:1.0");
        tablePath.addNamespace("table",
                "urn:oasis:names:tc:opendocument:xmlns:table:1.0");
        Element table = (Element)tablePath.selectSingleNode(doc.getDocumentElement());
        if(table != null && name != null) {
            String tableName=table.getAttributeNS(
                "urn:oasis:names:tc:opendocument:xmlns:table:1.0","name");
            System.err.println("Found table " + tableName);
            if(!name.equalsIgnoreCase(tableName)) {
                System.err.println("Skipping...");
                table=null;
            }
        }
        return table;
    }

    private static String[] split(String s) {
        StringTokenizer tokenizer = new StringTokenizer(s, "/");
        String result[] = new String[tokenizer.countTokens()];
        for (int i = 0; i < result.length; ++i) {
            result[i] = tokenizer.nextToken();
        }
        return result;
    }
}
