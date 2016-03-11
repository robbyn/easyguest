/*
 * InvoicePanel.java
 *
 * Created on 27 January 2003, 00:11
 */

package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import data.Invoice;
import data.Customer;
import data.Article;
import java.lang.String;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;
import java.util.Collection;
import java.util.MissingResourceException;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.awt.CardLayout;
import java.awt.Container;
import java.text.ParseException;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import session.EasyguestSession;
import reports.CollectionDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import util.Util;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class InvoicePanel extends javax.swing.JPanel {
    private EasyguestSession sess;
    private InvoiceTableModel tableModel;
    private InvoiceReservationTableModel reservationTableModel;
    private InvoiceItemTableModel itemTableModel;
    private Invoice invoice;
    private Customer customer;
    private Map<KeyStroke,Article> keyStrokeMap;

    public InvoicePanel(EasyguestSession sess) {
        this.sess = sess;
        initComponents();
        tableModel = new InvoiceTableModel(sess);
        invoiceTable.setModel(tableModel);
        invoiceTable.setDefaultEditor(Date.class, new DateCellEditor());
        TableCellRenderer renderer = new InvoiceRenderer();
        TableColumnModel model = invoiceTable.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); ++i) {
            model.getColumn(i).setCellRenderer(renderer);
        }

        reservationTableModel = new InvoiceReservationTableModel(sess);
        reservationTable.setModel(reservationTableModel);
        reservationTable.setDefaultEditor(Date.class, new DateCellEditor());
        renderer = new InvoiceReservationRenderer();
        model = reservationTable.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); ++i) {
            model.getColumn(i).setCellRenderer(renderer);
        }

        itemTableModel = new InvoiceItemTableModel(sess);
        itemTable.setModel(itemTableModel);
        itemTable.setDefaultEditor(Article.class, new ArticleCellEditor(sess));
        renderer = new InvoiceItemRenderer();
        model = itemTable.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); ++i) {
            model.getColumn(i).setCellRenderer(renderer);
        }

        searchByRoom.setSelected(true);
        invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        invoiceTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = invoiceTable.getSelectedRow();
                    Invoice inv = row >= 0 ? tableModel.getInvoice(row) : null;
                    setInvoice(inv);
                }
            }
        });
        fillTitleCombo();
        fillCountryCombo();
        setInvoice(null);
        keyStrokeMap = new HashMap<KeyStroke,Article>();
        sess.begin();
        try {
            for (Article article: sess.getExtent(Article.class)) {
                if (article.getKeyStroke() != null) {
                      keyStrokeMap.put(article.getKeyStroke(), article);                            
                }            
            }
        } finally {
            sess.end();
        }
        Util.clearWidthAll(this, JTextField.class);
        Util.clearWidthAll(this, JComboBox.class);
    }

    public void preview() {
        if (invoice != null) {
            try {
                JasperPrint jp;
                InputStream in = InvoicePanel.class.getResourceAsStream(
                        "/reports/invoice.jasper");
                try {
                    sess.begin();
                    try {
                        invoice = sess.getObjectById(Invoice.class, invoice.getId());
                        if (invoice == null) {
                            return;
                        }
                        jp = JasperFillManager.fillReport(
                                in, getParameters(), getDataSource());
                    } finally {
                        sess.end();
                    }
                } finally {
                    in.close();
                }
                PreviewDialog dlg = new PreviewDialog(getFrame(), jp);
                dlg.setVisible(true);
            } catch (JRException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void print() {
        if (invoice != null) {
            try {
                JasperPrint jp;
                InputStream in = InvoicePanel.class.getResourceAsStream(
                        "/reports/invoice.jasper");
                try {
                    sess.begin();
                    try {
                        invoice = sess.getObjectById(Invoice.class, invoice.getId());
                        if (invoice == null) {
                            return;
                        }
                        jp = JasperFillManager.fillReport(
                                in, getParameters(), getDataSource());
                    } finally {
                        sess.end();
                    }
                } finally {
                    in.close();
                }
                JasperPrintManager.printReport(jp, true);
            } catch (JRException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map getParameters() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("sess", sess);
        return params;
    }

    private JRDataSource getDataSource() {
        return new CollectionDataSource(
                invoice == null ? new Invoice[0] : new Invoice[] {invoice});
    }

    private void fillTitleCombo() {
        for(int i = 0; ; i++) {
            try {
                title.addItem(Util.getResource("dialog.reservation.titleperson." + i));
            } catch (MissingResourceException e) {
                break;
            }
        }
    }

    private void fillCountryCombo() {
        List<String> countries = new ArrayList<String>();
        for (String iso: Locale.getISOCountries()) {
            try { 
                countries.add(Util.getResource("country." + iso.toLowerCase()));              
            } catch (MissingResourceException e) {
                System.out.println("Missing country " + iso);
            }            
        }
        Collections.sort(countries);
        for(String cntry: countries) {               
            country.addItem(cntry);
        }        
    }        

    private void setInvoice(Invoice newValue) {
        invoice = newValue;
        reservationTableModel.setInvoiceId(newValue == null ? -1 : newValue.getId());
        itemTableModel.setInvoice(newValue);
        if (invoice == null) {
            setCustomer(null);
            id.setText("");
            dateCreated.setText("");
            statusOpen.setSelected(false);
            statusPayed.setSelected(false);
            print.setEnabled(false);
            preview.setEnabled(false);
        } else {
            setCustomer(invoice.getCustomer());
            id.setText(Integer.toString(invoice.getId()));
            dateCreated.setText(Util.date2str(invoice.getDateCreated()));
            if (invoice.getStatus() == Invoice.STATUS_OPEN) {
                statusOpen.setSelected(true);
            } else if (invoice.getStatus() == Invoice.STATUS_PAYED) {
                statusPayed.setSelected(true);
            }
            print.setEnabled(true);
            preview.setEnabled(true);
        }
    }

    private void enableCustomer(boolean enable) {
        title.setEnabled(enable);
        company.setEnabled(enable);
        firstname.setEnabled(enable);
        lastname.setEnabled(enable);
        address1.setEnabled(enable);
        address2.setEnabled(enable);
        zip.setEnabled(enable);
        state.setEnabled(enable);
        city.setEnabled(enable);
        country.setEnabled(enable);
        phone.setEnabled(enable);
        mobile.setEnabled(enable);
        fax.setEnabled(enable);
        email.setEnabled(enable);
    }

    private void setCustomer(Customer newValue) {
        customer = newValue;
        if (customer == null) {
            title.setSelectedItem(null);
            company.setText("");
            firstname.setText("");
            lastname.setText("");
            address1.setText("");
            address2.setText("");
            zip.setText("");
            state.setText("");
            city.setText("");
            country.setSelectedItem(null);
            phone.setText("");
            mobile.setText("");
            fax.setText("");
            email.setText("");
            modify.setEnabled(false);
            enableCustomer(true);
            newCustomer.setSelected(true);
        } else {
            title.setSelectedItem(customer.getTitlePerson());
            company.setText(customer.getCompany());
            firstname.setText(customer.getFirstName());
            lastname.setText(customer.getLastName());
            address1.setText(customer.getAddress1());
            address2.setText(customer.getAddress2());
            zip.setText(customer.getZip());
            state.setText(customer.getState());
            city.setText(customer.getCity());
            country.setSelectedItem(customer.getCountry());
            phone.setText(customer.getTelephone());
            mobile.setText(customer.getMobile());
            fax.setText(customer.getFax());
            email.setText(customer.getEmail());
            modify.setEnabled(true);
            enableCustomer(false);
            existingCustomer.setSelected(true);
        }
    }

    private JFrame getFrame() {
        for (Container cont = getParent(); cont != null; cont = cont.getParent()) {
            if (cont instanceof JFrame) {
                return (JFrame)cont;
            }
        }
        return null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        searchMode = new javax.swing.ButtonGroup();
        statusGrp = new javax.swing.ButtonGroup();
        customerGrp = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        searchByRoom = new javax.swing.JRadioButton();
        searchById = new javax.swing.JRadioButton();
        searchByCustomer = new javax.swing.JRadioButton();
        searchByDate = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        search = new javax.swing.JButton();
        searchParams = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        searchRoom = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        searchLastName = new javax.swing.JTextField();
        searchFirstName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        searchCompany = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        searchFromDate = new javax.swing.JTextField();
        searchToDate = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        invoiceTable = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        print = new javax.swing.JButton();
        preview = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        dateCreated = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        statusOpen = new javax.swing.JRadioButton();
        statusPayed = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        existingCustomer = new javax.swing.JRadioButton();
        newCustomer = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        customerSearch = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        searchCustomer = new javax.swing.JButton();
        modify = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        title = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        firstname = new javax.swing.JTextField();
        lastname = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        company = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        address1 = new javax.swing.JTextField();
        address2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        zip = new javax.swing.JTextField();
        city = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        state = new javax.swing.JTextField();
        country = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        mobile = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        fax = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        panelDetails = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reservationTable = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        addReservation = new javax.swing.JButton();
        deleteReservation = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        addArticle = new javax.swing.JButton();
        deleteArticle = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(360);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(892, 546));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(400, 167));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 546));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setMinimumSize(new java.awt.Dimension(400, 106));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 106));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.X_AXIS));

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 0, 4));
        jPanel4.setMaximumSize(new java.awt.Dimension(460, 27));
        jPanel4.setMinimumSize(new java.awt.Dimension(400, 27));
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 27));
        searchMode.add(searchByRoom);
        searchByRoom.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.byroom"));
        searchByRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByRoomActionPerformed(evt);
            }
        });

        jPanel4.add(searchByRoom);

        searchMode.add(searchById);
        searchById.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.byid"));
        searchById.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByIdActionPerformed(evt);
            }
        });

        jPanel4.add(searchById);

        searchMode.add(searchByCustomer);
        searchByCustomer.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.bycustomer"));
        searchByCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByCustomerActionPerformed(evt);
            }
        });

        jPanel4.add(searchByCustomer);

        searchMode.add(searchByDate);
        searchByDate.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.bydate"));
        searchByDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByDateActionPerformed(evt);
            }
        });

        jPanel4.add(searchByDate);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel5.setMinimumSize(new java.awt.Dimension(400, 35));
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 35));
        search.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search"));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        jPanel5.add(search);

        jPanel3.add(jPanel5, java.awt.BorderLayout.SOUTH);

        searchParams.setLayout(new java.awt.CardLayout());

        searchParams.setMinimumSize(new java.awt.Dimension(400, 44));
        searchParams.setPreferredSize(new java.awt.Dimension(400, 44));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jPanel13.setMinimumSize(new java.awt.Dimension(400, 38));
        jPanel13.setPreferredSize(new java.awt.Dimension(400, 38));
        jLabel20.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.room-number"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 11);
        jPanel13.add(jLabel20, gridBagConstraints);

        searchRoom.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 11);
        jPanel13.add(searchRoom, gridBagConstraints);

        searchParams.add(jPanel13, "byroom");

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jPanel8.setMinimumSize(new java.awt.Dimension(400, 44));
        jPanel8.setPreferredSize(new java.awt.Dimension(400, 44));
        jLabel2.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.name"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 11);
        jPanel8.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel8.add(searchLastName, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 11);
        jPanel8.add(searchFirstName, gridBagConstraints);

        jLabel3.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.company"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel8.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 160;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel8.add(searchCompany, gridBagConstraints);

        searchParams.add(jPanel8, "bycustomer");

        jPanel9.setLayout(new java.awt.GridBagLayout());

        jPanel9.setMinimumSize(new java.awt.Dimension(400, 20));
        jPanel9.setPreferredSize(new java.awt.Dimension(400, 20));
        jLabel4.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.dateminmax"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 11);
        jPanel9.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel9.add(searchFromDate, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 11);
        jPanel9.add(searchToDate, gridBagConstraints);

        searchParams.add(jPanel9, "bydate");

        jPanel7.setLayout(new java.awt.GridBagLayout());

        jPanel7.setMinimumSize(new java.awt.Dimension(400, 38));
        jPanel7.setPreferredSize(new java.awt.Dimension(400, 38));
        jLabel1.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search.id"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 11);
        jPanel7.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 11);
        jPanel7.add(searchId, gridBagConstraints);

        searchParams.add(jPanel7, "byid");

        jPanel3.add(searchParams, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 5, 5));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(400, 26));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 405));
        invoiceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        invoiceTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        jScrollPane1.setViewportView(invoiceTable);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel14.setMinimumSize(new java.awt.Dimension(400, 35));
        jPanel14.setPreferredSize(new java.awt.Dimension(400, 35));
        print.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.print"));
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        jPanel14.add(print);

        preview.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.preview"));
        preview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previewActionPerformed(evt);
            }
        });

        jPanel14.add(preview);

        jPanel1.add(jPanel14, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel16.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.id"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        jPanel2.add(jLabel16, gridBagConstraints);

        id.setEditable(false);
        id.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        id.setMinimumSize(new java.awt.Dimension(120, 20));
        id.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 11);
        jPanel2.add(id, gridBagConstraints);

        jLabel17.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.date-created"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 5);
        jPanel2.add(jLabel17, gridBagConstraints);

        dateCreated.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        dateCreated.setMinimumSize(new java.awt.Dimension(120, 20));
        dateCreated.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel2.add(dateCreated, gridBagConstraints);

        jLabel19.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.status"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 5);
        jPanel2.add(jLabel19, gridBagConstraints);

        statusGrp.add(statusOpen);
        statusOpen.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.status.open"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel2.add(statusOpen, gridBagConstraints);

        statusGrp.add(statusPayed);
        statusPayed.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.status.payed"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(statusPayed, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setMinimumSize(new java.awt.Dimension(14, 0));
        jPanel6.setPreferredSize(new java.awt.Dimension(14, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel2.add(jPanel6, gridBagConstraints);

        customerGrp.add(existingCustomer);
        existingCustomer.setMnemonic('c');
        existingCustomer.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.existing-customer"));
        existingCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingCustomerActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel2.add(existingCustomer, gridBagConstraints);

        customerGrp.add(newCustomer);
        newCustomer.setMnemonic('n');
        newCustomer.setSelected(true);
        newCustomer.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.new-customer"));
        newCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCustomerActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(newCustomer, gridBagConstraints);

        jLabel18.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search-label"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 5);
        jPanel2.add(jLabel18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(customerSearch, gridBagConstraints);

        jPanel11.setLayout(new java.awt.GridBagLayout());

        searchCustomer.setMnemonic('r');
        searchCustomer.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search"));
        searchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCustomerActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel11.add(searchCustomer, gridBagConstraints);

        modify.setMnemonic('m');
        modify.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.modify-client"));
        modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyActionPerformed(evt);
            }
        });

        jPanel11.add(modify, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel2.add(jPanel11, gridBagConstraints);

        jLabel5.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.title"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 11);
        jPanel2.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 5);
        jPanel2.add(title, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanel2.add(jLabel14, gridBagConstraints);

        jLabel6.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.firstname-lastname"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel2.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel2.add(firstname, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(lastname, gridBagConstraints);

        jLabel8.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.company"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel2.add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(company, gridBagConstraints);

        jLabel9.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.address"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel2.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(address1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(address2, gridBagConstraints);

        jLabel10.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.zip-city"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel2.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel2.add(zip, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(city, gridBagConstraints);

        jLabel11.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.state-country"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel2.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel2.add(state, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(country, gridBagConstraints);

        jLabel12.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.phone-mobile"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel2.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel2.add(phone, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel2.add(mobile, gridBagConstraints);

        jLabel13.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.fax-email"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 11, 11);
        jPanel2.add(jLabel13, gridBagConstraints);

        fax.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 5);
        jPanel2.add(fax, gridBagConstraints);

        email.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 11);
        jPanel2.add(email, gridBagConstraints);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("resources").getString("invoice.tab.general"), jPanel2);

        panelDetails.setLayout(new java.awt.GridLayout(2, 0, 0, 12));

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 11, 11));
        jLabel7.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.reservations"));
        jPanel10.add(jLabel7, java.awt.BorderLayout.NORTH);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(300, 80));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(300, 80));
        reservationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        reservationTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        jScrollPane2.setViewportView(reservationTable);

        jPanel10.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setLayout(new java.awt.GridBagLayout());

        addReservation.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.reservation.add"));
        addReservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addReservationActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel17.add(addReservation, gridBagConstraints);

        deleteReservation.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.reservation.delete"));
        deleteReservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteReservationActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 11, 0);
        jPanel17.add(deleteReservation, gridBagConstraints);

        jPanel16.add(jPanel17, java.awt.BorderLayout.NORTH);

        jPanel10.add(jPanel16, java.awt.BorderLayout.EAST);

        panelDetails.add(jPanel10);

        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 11, 11));
        jLabel15.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.articles"));
        jPanel15.add(jLabel15, java.awt.BorderLayout.NORTH);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane3.setMinimumSize(new java.awt.Dimension(320, 160));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(320, 160));
        itemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        itemTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        itemTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemTableKeyPressed(evt);
            }
        });

        jScrollPane3.setViewportView(itemTable);

        jPanel15.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel19.setLayout(new java.awt.GridBagLayout());

        addArticle.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.article.add"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel19.add(addArticle, gridBagConstraints);

        deleteArticle.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.article.delete"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 11, 0);
        jPanel19.add(deleteArticle, gridBagConstraints);

        jPanel18.add(jPanel19, java.awt.BorderLayout.NORTH);

        jPanel15.add(jPanel18, java.awt.BorderLayout.EAST);

        panelDetails.add(jPanel15);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("resources").getString("invoice.tab.content"), panelDetails);

        jPanel12.add(jTabbedPane1, java.awt.BorderLayout.NORTH);

        jSplitPane1.setRightComponent(jPanel12);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

    private void deleteReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteReservationActionPerformed
// TODO add your handling code here:
        int index = reservationTable.getSelectedRow();
    }//GEN-LAST:event_deleteReservationActionPerformed

    private void addReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addReservationActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_addReservationActionPerformed

    private void itemTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemTableKeyPressed
        KeyStroke ks = KeyStroke.getKeyStrokeForEvent(evt);
        System.out.println(ks.toString());
        Article article = keyStrokeMap.get(ks);
        if (article != null) {
            TableCellEditor editor = itemTable.getCellEditor();
            if (editor != null) {
                editor.stopCellEditing();
            }
            itemTableModel.addArticle(article);
        }
    }//GEN-LAST:event_itemTableKeyPressed

    private void previewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previewActionPerformed
        preview();
    }//GEN-LAST:event_previewActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        print();
    }//GEN-LAST:event_printActionPerformed

    private void searchByRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByRoomActionPerformed
        if (searchByRoom.isSelected()) {
            CardLayout layout = (CardLayout)searchParams.getLayout();
            layout.show(searchParams, "byroom");
        }
    }//GEN-LAST:event_searchByRoomActionPerformed

    private void existingCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingCustomerActionPerformed
        if (existingCustomer.isSelected()) {
            enableCustomer(false);
        }
    }//GEN-LAST:event_existingCustomerActionPerformed

    private void newCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerActionPerformed
        if (newCustomer.isSelected()) {
            setCustomer(null);
        }
    }//GEN-LAST:event_newCustomerActionPerformed

    private void modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyActionPerformed
        enableCustomer(true);
    }//GEN-LAST:event_modifyActionPerformed

    private void searchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCustomerActionPerformed
        String str = customerSearch.getText().trim();
        //Customer customers[] = sess.getCustomers(str);
        Customer customers[];
        sess.begin();
        try {
            customers = sess.getCustomers(str);
        } finally {
            sess.end();
        }        
        if (customers.length == 1) {
            setCustomer(customers[0]);
        } else if (customers.length > 1) {
            CustomerSelectDialog dlg = new CustomerSelectDialog(getFrame(), customers);
            dlg.setVisible(true);
            if (dlg.getCustomer() != null) {
                setCustomer(dlg.getCustomer());
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    Util.getResource("dialog.reservation.result.0"),
                    Util.getResource("dialog.reservation.result"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_searchCustomerActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        try {
            if (searchByRoom.isSelected()) {
                int number;
                try {
                    number = Integer.parseInt(searchRoom.getText());
                } catch (NumberFormatException e) {
                    throw new InputException(searchRoom, "error.badnumber",
                            searchRoom.getText());
                }
                tableModel.queryRoom(number);
            } else if (searchById.isSelected()) {
                int id;
                try {
                    id = Integer.parseInt(searchId.getText());
                } catch (NumberFormatException e) {
                    throw new InputException(searchId, "error.badnumber",
                            searchId.getText());
                }
                tableModel.query(id);
            } else if (searchByCustomer.isSelected()) {
                tableModel.query(searchFirstName.getText(),
                        searchLastName.getText(), searchCompany.getText());
            } else if (searchByDate.isSelected()) {
                Date fromDate;
                Date toDate;
                try {
                    fromDate = Util.str2date(searchFromDate.getText());
                } catch (ParseException e) {
                    throw new InputException(searchFromDate, "error.baddate",
                            searchFromDate.getText());
                }
                try {
                    toDate = Util.str2date(searchToDate.getText());
                } catch (ParseException e) {
                    throw new InputException(searchToDate, "error.baddate",
                            searchToDate.getText());
                }
                tableModel.query(fromDate, toDate);
            }
        } catch (InputException e) {
            e.printStackTrace();
            e.showMessage(this);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, Util.getResource("error.search"));
        }
    }//GEN-LAST:event_searchActionPerformed

    private void searchByDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByDateActionPerformed
        if (searchByDate.isSelected()) {
            CardLayout layout = (CardLayout)searchParams.getLayout();
            layout.show(searchParams, "bydate");
        }
    }//GEN-LAST:event_searchByDateActionPerformed

    private void searchByCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByCustomerActionPerformed
        if (searchByCustomer.isSelected()) {
            CardLayout layout = (CardLayout)searchParams.getLayout();
            layout.show(searchParams, "bycustomer");
        }
    }//GEN-LAST:event_searchByCustomerActionPerformed

    private void searchByIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByIdActionPerformed
        if (searchById.isSelected()) {
            CardLayout layout = (CardLayout)searchParams.getLayout();
            layout.show(searchParams, "byid");
        }
    }//GEN-LAST:event_searchByIdActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addArticle;
    private javax.swing.JButton addReservation;
    private javax.swing.JTextField address1;
    private javax.swing.JTextField address2;
    private javax.swing.JTextField city;
    private javax.swing.JTextField company;
    private javax.swing.JComboBox country;
    private javax.swing.ButtonGroup customerGrp;
    private javax.swing.JTextField customerSearch;
    private javax.swing.JTextField dateCreated;
    private javax.swing.JButton deleteArticle;
    private javax.swing.JButton deleteReservation;
    private javax.swing.JTextField email;
    private javax.swing.JRadioButton existingCustomer;
    private javax.swing.JTextField fax;
    private javax.swing.JTextField firstname;
    private javax.swing.JTextField id;
    private javax.swing.JTable invoiceTable;
    private javax.swing.JTable itemTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lastname;
    private javax.swing.JTextField mobile;
    private javax.swing.JButton modify;
    private javax.swing.JRadioButton newCustomer;
    private javax.swing.JPanel panelDetails;
    private javax.swing.JTextField phone;
    private javax.swing.JButton preview;
    private javax.swing.JButton print;
    private javax.swing.JTable reservationTable;
    private javax.swing.JButton search;
    private javax.swing.JRadioButton searchByCustomer;
    private javax.swing.JRadioButton searchByDate;
    private javax.swing.JRadioButton searchById;
    private javax.swing.JRadioButton searchByRoom;
    private javax.swing.JTextField searchCompany;
    private javax.swing.JButton searchCustomer;
    private javax.swing.JTextField searchFirstName;
    private javax.swing.JTextField searchFromDate;
    private javax.swing.JTextField searchId;
    private javax.swing.JTextField searchLastName;
    private javax.swing.ButtonGroup searchMode;
    private javax.swing.JPanel searchParams;
    private javax.swing.JTextField searchRoom;
    private javax.swing.JTextField searchToDate;
    private javax.swing.JTextField state;
    private javax.swing.ButtonGroup statusGrp;
    private javax.swing.JRadioButton statusOpen;
    private javax.swing.JRadioButton statusPayed;
    private javax.swing.JComboBox title;
    private javax.swing.JTextField zip;
    // End of variables declaration//GEN-END:variables

}
