/*
 * ReservationDialog.java
 *
 * Created on 22 novembre 2002, 16:49
 */

package gui;

import data.Customer;
import data.Hotel;
import data.Invoice;
import data.Reservation;
import data.Room;
import javax.swing.JTextField;
import org.apache.commons.collections.ArrayStack;
import session.EasyguestSession;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.lang.String;
import java.util.MissingResourceException;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Util;


/**
 *
 * @author  denis
 */
@SuppressWarnings("serial")
public class ReservationDialog extends javax.swing.JDialog {
    private static Log log = LogFactory.getLog(ReservationDialog.class);

    private Hotel hotel;
    private Reservation reservation;
    private Customer customer;
    private EasyguestSession sess;
    
    public ReservationDialog(Frame parent, EasyguestSession sess,
            Reservation reservation) {
        this(parent, sess, reservation.getFromDate(), reservation.getToDate(),
                reservation.getRoom());
        this.reservation = reservation;
        Invoice invoice = reservation.getInvoice();
        customer = invoice == null ? null : invoice.getCustomer();
        if (customer != null) {
            setCustomer(customer);
        }
        statusReserved.setSelected(true);
        if (reservation.getStatus() == Reservation.STATUS_OCCUPIED) {
            statusOccupied.setSelected(true);
        } else if (reservation.getStatus() == Reservation.STATUS_RELEASED) {
            statusReleased.setSelected(true);
        } else if (reservation.getStatus() == Reservation.STATUS_CANCELED) {
            statusCanceled.setSelected(true);
        }
    }

    public ReservationDialog(Frame parent, EasyguestSession sess,
                                Date fromDate, Date toDate, Room room) {
        super(parent, true);
        this.sess = sess;
        initComponents();
        initOtherDataComponents(fromDate, toDate, room);
        Util.clearWidthAll(this, JTextField.class);
        Util.clearWidthAll(this, JComboBox.class);
        pack();
        if (fromDate.after(Util.today())) {
            statusReserved.setSelected(true);
        } else {
            statusOccupied.setSelected(true);
        }
        getRootPane().setDefaultButton(ok);
        setLocation(parent.getX()+(parent.getWidth()-getWidth())/2,
                parent.getY()+(int)(parent.getWidth()*(0.618/1.618))-getHeight()/2);
    }

    public void initOtherDataComponents(Date fromDate, Date toDate,
            Room room) {
        if (fromDate != null) {
            this.fromDate.setText(Util.date2str(fromDate));
        }
        if (toDate != null) {
            this.toDate.setText(Util.date2str(toDate));
        }
        hotel = room.getHotel();
        sess.begin();
        try {
            for (Room r: sess.getHotel().getRooms()) {
                roomNumber.addItem(r.getNumber());
            }
        } finally {
            sess.end();
        }
        roomNumber.setSelectedItem(room.getNumber());
        fillCombo(title, "dialog.reservation.titleperson.");
        fillCountryCombo();
    }

    private void fillCombo(JComboBox combo, String prefix) {
        for(int i = 0; ; i++) {
            try {
                combo.addItem(Util.getResource(prefix + i));
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

    public void save() throws InputException {
        if (Util.isBlank(lastname.getText())
                && Util.isBlank(company.getText())) {
            throw new InputException(lastname, "error.reservation.nullname");
        }
        sess.begin();
        try {
            if (customer == null) {
                customer = new Customer();
            }
            customer.setTitlePerson((String)title.getSelectedItem());
            customer.setCompany(company.getText());
            customer.setFirstName(firstname.getText());
            customer.setLastName(lastname.getText());
            customer.setAddress1(address1.getText());
            customer.setAddress2(address2.getText());
            customer.setState(state.getText());
            customer.setZip(zip.getText());
            customer.setCity(city.getText());
            customer.setCountry((String)country.getSelectedItem());
            customer.setTelephone(phone.getText());
            customer.setMobile(mobile.getText());
            customer.setFax(fax.getText());
            customer.setEmail(email.getText());
            sess.update(customer);

            if (reservation == null) {
                reservation = new Reservation();
            }
            try {
                reservation.setFromDate(Util.str2date(fromDate.getText()));
            } catch (ParseException e) {
                throw new InputException(fromDate, "error.baddate",
                        fromDate.getText());
            }
            try {
                reservation.setToDate(Util.str2date(toDate.getText()));
            } catch (ParseException e) {
                throw new InputException(fromDate, "error.baddate",
                        toDate.getText());
            }
            int num = 0;
            if (roomNumber.getSelectedItem() == null) {
                throw new InputException(roomNumber, "error.badnumber", "");
            }
            num = (Integer)roomNumber.getSelectedItem();
            Room room = sess.getRoom(hotel, num);
            if (room == null) {
                throw new InputException(roomNumber, "error.roomnotfound", num);
            }
            reservation.setRoom(room);
            if (statusReserved.isSelected()) {
                reservation.setStatus(Reservation.STATUS_RESERVED);
            } else if (statusOccupied.isSelected()) {
                reservation.setStatus(Reservation.STATUS_OCCUPIED);
            } else if (statusReleased.isSelected()) {
                reservation.setStatus(Reservation.STATUS_RELEASED);
            } else if (statusCanceled.isSelected()) {
                reservation.setStatus(Reservation.STATUS_CANCELED);
            }
            Invoice invoice = reservation.getInvoice();
            if (invoice == null) {
                invoice = new Invoice();
                invoice.setDateCreated(Util.today());
                invoice.setHotel(room.getHotel());
                sess.makePersistent(invoice);
                reservation.setInvoice(invoice);
            }
            invoice.setCustomer(customer);
            sess.update(reservation);
            sess.commit();
        } catch (InputException e) {
            throw e;
        } finally {
            sess.end();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        statusGrp = new javax.swing.ButtonGroup();
        clientGrp = new javax.swing.ButtonGroup();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        roomNumber = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        fromDate = new javax.swing.JTextField();
        toDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        statusReserved = new javax.swing.JRadioButton();
        statusOccupied = new javax.swing.JRadioButton();
        statusReleased = new javax.swing.JRadioButton();
        statusCanceled = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        existingCustomer = new javax.swing.JRadioButton();
        newCustomer = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        customerSearch = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        search = new javax.swing.JButton();
        modify = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        title = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        jLabel7.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.invoice"));

        setTitle(java.util.ResourceBundle.getBundle("resources").getString("dialog.reservation.title"));
        setName("");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.room"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        jPanel1.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 11);
        jPanel1.add(roomNumber, gridBagConstraints);

        jLabel1.setLabelFor(fromDate);
        jLabel1.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.date-min-max"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel1.add(fromDate, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(toDate, gridBagConstraints);

        jLabel3.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(jLabel3, gridBagConstraints);

        statusGrp.add(statusReserved);
        statusReserved.setMnemonic('r');
        statusReserved.setSelected(true);
        statusReserved.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status.reserved"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel1.add(statusReserved, gridBagConstraints);

        statusGrp.add(statusOccupied);
        statusOccupied.setMnemonic('o');
        statusOccupied.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status.occupied"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(statusOccupied, gridBagConstraints);

        statusGrp.add(statusReleased);
        statusReleased.setMnemonic('q');
        statusReleased.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status.released"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(statusReleased, gridBagConstraints);

        statusGrp.add(statusCanceled);
        statusCanceled.setMnemonic('a');
        statusCanceled.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.status.canceled"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(statusCanceled, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setMinimumSize(new java.awt.Dimension(14, 1));
        jPanel6.setPreferredSize(new java.awt.Dimension(14, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 0);
        jPanel1.add(jPanel6, gridBagConstraints);

        clientGrp.add(existingCustomer);
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
        jPanel1.add(existingCustomer, gridBagConstraints);

        clientGrp.add(newCustomer);
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
        jPanel1.add(newCustomer, gridBagConstraints);

        jLabel14.setText(java.util.ResourceBundle.getBundle("resources").getString("reservation.search-label"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 5);
        jPanel1.add(jLabel14, gridBagConstraints);

        customerSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                customerSearchKeyTyped(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel1.add(customerSearch, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        search.setMnemonic('r');
        search.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.search-client"));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel4.add(search, gridBagConstraints);

        modify.setMnemonic('m');
        modify.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.modify-client"));
        modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanel4.add(modify, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel1.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanel5.add(jPanel1, gridBagConstraints);

        jPanel8.setLayout(null);

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        jPanel5.add(jPanel8, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.title"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        jPanel7.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 5);
        jPanel7.add(title, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanel7.add(jLabel2, gridBagConstraints);

        jLabel6.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.firstname-lastname"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel7.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel7.add(firstname, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel7.add(lastname, gridBagConstraints);

        jLabel8.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.company"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel7.add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel7.add(company, gridBagConstraints);

        jLabel9.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.address"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel7.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel7.add(address1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel7.add(address2, gridBagConstraints);

        jLabel10.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.zip-city"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel7.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel7.add(zip, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel7.add(city, gridBagConstraints);

        jLabel11.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.state-country"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel7.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel7.add(state, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel7.add(country, gridBagConstraints);

        jLabel12.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.phone-mobile"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel7.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel7.add(phone, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel7.add(mobile, gridBagConstraints);

        jLabel13.setText(java.util.ResourceBundle.getBundle("resources").getString("invoice.fax-email"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        jPanel7.add(jLabel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanel7.add(fax, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        jPanel7.add(email, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanel5.add(jPanel7, gridBagConstraints);

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        jPanel2.setMinimumSize(new java.awt.Dimension(444, 53));
        jPanel2.setPreferredSize(new java.awt.Dimension(444, 53));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        ok.setText(java.util.ResourceBundle.getBundle("resources").getString("button.ok"));
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(17, 12, 11, 5);
        jPanel3.add(ok, gridBagConstraints);

        cancel.setText(java.util.ResourceBundle.getBundle("resources").getString("button.cancel"));
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(17, 0, 11, 11);
        jPanel3.add(cancel, gridBagConstraints);

        jPanel2.add(jPanel3);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerActionPerformed
        if (newCustomer.isSelected()) {
            setCustomer(null);
        }
    }//GEN-LAST:event_newCustomerActionPerformed

    private void existingCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingCustomerActionPerformed
        if (existingCustomer.isSelected()) {
            enableCustomer(false);
        }
    }//GEN-LAST:event_existingCustomerActionPerformed

    private void modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyActionPerformed
        enableCustomer(true);
    }//GEN-LAST:event_modifyActionPerformed

    private void customerSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerSearchKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            evt.consume();
            search.doClick();
        }
    }//GEN-LAST:event_customerSearchKeyTyped

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        String str = customerSearch.getText().trim();
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
            CustomerSelectDialog dlg = new CustomerSelectDialog(this, customers);
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
    }//GEN-LAST:event_searchActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        closeDialog(null);
    }//GEN-LAST:event_cancelActionPerformed

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        try {
            save();
            closeDialog(null);
        } catch (InputException e) {
            log.debug(" ",e);
            e.showMessage(this);
        } catch (Exception e) {
            log.debug(" ",e);
            JOptionPane.showMessageDialog(this,
                    Util.getResource("error.reservation.unknown")
                            + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_okActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1;
    private javax.swing.JTextField address2;
    private javax.swing.JButton cancel;
    private javax.swing.JTextField city;
    private javax.swing.ButtonGroup clientGrp;
    private javax.swing.JTextField company;
    private javax.swing.JComboBox country;
    private javax.swing.JTextField customerSearch;
    private javax.swing.JTextField email;
    private javax.swing.JRadioButton existingCustomer;
    private javax.swing.JTextField fax;
    private javax.swing.JTextField firstname;
    private javax.swing.JTextField fromDate;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTextField lastname;
    private javax.swing.JTextField mobile;
    private javax.swing.JButton modify;
    private javax.swing.JRadioButton newCustomer;
    private javax.swing.JButton ok;
    private javax.swing.JTextField phone;
    private javax.swing.JComboBox roomNumber;
    private javax.swing.JButton search;
    private javax.swing.JTextField state;
    private javax.swing.JRadioButton statusCanceled;
    private javax.swing.ButtonGroup statusGrp;
    private javax.swing.JRadioButton statusOccupied;
    private javax.swing.JRadioButton statusReleased;
    private javax.swing.JRadioButton statusReserved;
    private javax.swing.JComboBox title;
    private javax.swing.JTextField toDate;
    private javax.swing.JTextField zip;
    // End of variables declaration//GEN-END:variables

}
