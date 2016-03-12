package org.tastefuljava.ezguest.gui.config;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import org.tastefuljava.ezguest.util.Util;
import org.tastefuljava.ezguest.data.RoomType;
import org.tastefuljava.ezguest.data.Room;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.awt.Frame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


@SuppressWarnings("serial")
public class RoomConfigDialog extends javax.swing.JDialog {
    private final EasyguestSession sess;
    private RoomTypeTableModel roomTypeTableModel;
    private RoomTypeRenderer typeRenderer;
    private RoomTableModel roomTableModel;
    private RoomRenderer roomRenderer;
    private TypeComboBoxRenderer typeComboBoxRenderer;
    private TypeComboBoxModel typeComboBoxModel;        

    public RoomConfigDialog(Frame parent, EasyguestSession sess) {
        super(parent, true);
        this.sess = sess;
        initComponents();
        sess.begin();
        try {
            typeRenderer = new RoomTypeRenderer();
            roomTypeTableModel = new RoomTypeTableModel(sess);
            roomTypeTable.setModel(roomTypeTableModel);
            int typeNumColumn = roomTypeTable.getColumnCount();
            for (int i = 0; i < typeNumColumn; i++) {
                roomTypeTable.getColumnModel().getColumn(i).setCellRenderer(typeRenderer);
            }
            roomTypeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);                        
            roomTypeScrollPane.getViewport().setBackground(roomTypeTable.getBackground());
                                
            roomRenderer = new RoomRenderer();
            roomTableModel = new RoomTableModel(sess);
            roomTable.setModel(roomTableModel);
            int roomNumColumn = roomTable.getColumnCount();
            for (int i = 0; i < roomNumColumn; i++) {
                roomTable.getColumnModel().getColumn(i).setCellRenderer(roomRenderer);
            }
            roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);                                                
            roomTable.setDefaultEditor(RoomType.class, new TypeCellEditor(sess, roomTypeTableModel));
            roomScrollPane.getViewport().setBackground(roomTable.getBackground());
            
            typeComboBoxRenderer = new TypeComboBoxRenderer();
            typeComboBoxModel = new TypeComboBoxModel(sess, roomTypeTableModel);
            typeCombo.setModel(typeComboBoxModel);
            typeCombo.setRenderer(typeComboBoxRenderer);
        } finally {
            sess.end();
        }
        
        roomTypeTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                roomTypeChanged(roomTypeTable.getSelectedRow());
            }
        });        
        
        roomTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                roomChanged(roomTable.getSelectedRow());
            }
        });         
        initialize(parent);
    }

    private void initialize(Frame parent) {
        getRootPane().setDefaultButton(close);
        setLocation(parent.getX()+(parent.getWidth()-getWidth())/2,
                parent.getY()+(int)(parent.getWidth()*(0.618/1.618))-getHeight()/2);
        Util.clearWidthAll(this, JTextField.class);
        Util.clearWidthAll(this, JComboBox.class);
        pack();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        typesroomsTabbedPane = new javax.swing.JTabbedPane();
        roomTypesPanel = new javax.swing.JPanel();
        roomTypeScrollPane = new javax.swing.JScrollPane();
        roomTypeTable = new javax.swing.JTable();
        formRoomTypePanel = new javax.swing.JPanel();
        formRoomTypesPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        priceLabel = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        description = new javax.swing.JTextField();
        buttonsRoomTypePanel = new javax.swing.JPanel();
        newTypeButton = new javax.swing.JButton();
        delRoomTypeButton = new javax.swing.JButton();
        roomsPanel = new javax.swing.JPanel();
        roomScrollPane = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();
        formRoomPanel = new javax.swing.JPanel();
        formRoomsPanel = new javax.swing.JPanel();
        numberLabel = new javax.swing.JLabel();
        number = new javax.swing.JTextField();
        typeLabel = new javax.swing.JLabel();
        typeCombo = new javax.swing.JComboBox();
        buttonsRoomPanel = new javax.swing.JPanel();
        newRoomButton = new javax.swing.JButton();
        delRoomButton = new javax.swing.JButton();
        typeroomsClosePanel = new javax.swing.JPanel();
        close = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources"); // NOI18N
        setTitle(bundle.getString("room.dialog.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        roomTypesPanel.setLayout(new java.awt.BorderLayout());

        roomTypeScrollPane.setBorder(null);
        roomTypeScrollPane.setPreferredSize(new java.awt.Dimension(450, 400));

        roomTypeTable.setModel(new javax.swing.table.DefaultTableModel(
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
        roomTypeTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        roomTypeScrollPane.setViewportView(roomTypeTable);

        roomTypesPanel.add(roomTypeScrollPane, java.awt.BorderLayout.CENTER);

        formRoomTypePanel.setLayout(new java.awt.BorderLayout());

        formRoomTypesPanel.setMinimumSize(new java.awt.Dimension(291, 90));
        formRoomTypesPanel.setPreferredSize(new java.awt.Dimension(291, 90));
        formRoomTypesPanel.setLayout(new java.awt.GridBagLayout());

        nameLabel.setText(bundle.getString("roomtypes.dialog.roomtype.name")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        formRoomTypesPanel.add(nameLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 11);
        formRoomTypesPanel.add(name, gridBagConstraints);

        priceLabel.setText(bundle.getString("roomtypes.dialog.roomtype.price")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        formRoomTypesPanel.add(priceLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 61;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        formRoomTypesPanel.add(price, gridBagConstraints);

        descriptionLabel.setText(bundle.getString("roomtypes.dialog.roomtype.description")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 11, 11);
        formRoomTypesPanel.add(descriptionLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 11);
        formRoomTypesPanel.add(description, gridBagConstraints);

        formRoomTypePanel.add(formRoomTypesPanel, java.awt.BorderLayout.CENTER);

        buttonsRoomTypePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        newTypeButton.setText(bundle.getString("roomtypes.dialog.types.button.new")); // NOI18N
        newTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTypeButtonActionPerformed(evt);
            }
        });
        buttonsRoomTypePanel.add(newTypeButton);

        delRoomTypeButton.setText(bundle.getString("roomtypes.dialog.types.button.delete")); // NOI18N
        delRoomTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delRoomTypeButtonActionPerformed(evt);
            }
        });
        buttonsRoomTypePanel.add(delRoomTypeButton);

        formRoomTypePanel.add(buttonsRoomTypePanel, java.awt.BorderLayout.SOUTH);

        roomTypesPanel.add(formRoomTypePanel, java.awt.BorderLayout.SOUTH);

        typesroomsTabbedPane.addTab(bundle.getString("room.dialog.tab.types"), roomTypesPanel); // NOI18N

        roomsPanel.setLayout(new java.awt.BorderLayout());

        roomScrollPane.setBorder(null);
        roomScrollPane.setPreferredSize(new java.awt.Dimension(450, 400));

        roomTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        roomScrollPane.setViewportView(roomTable);

        roomsPanel.add(roomScrollPane, java.awt.BorderLayout.CENTER);

        formRoomPanel.setLayout(new java.awt.BorderLayout());

        formRoomsPanel.setMinimumSize(new java.awt.Dimension(291, 70));
        formRoomsPanel.setPreferredSize(new java.awt.Dimension(291, 70));
        formRoomsPanel.setLayout(new java.awt.GridBagLayout());

        numberLabel.setText(bundle.getString("rooms.dialog.room.number")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        formRoomsPanel.add(numberLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        formRoomsPanel.add(number, gridBagConstraints);

        typeLabel.setText(bundle.getString("rooms.dialog.room.type")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 11, 11);
        formRoomsPanel.add(typeLabel, gridBagConstraints);

        typeCombo.setMinimumSize(new java.awt.Dimension(32, 23));
        typeCombo.setPreferredSize(new java.awt.Dimension(32, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 11);
        formRoomsPanel.add(typeCombo, gridBagConstraints);

        formRoomPanel.add(formRoomsPanel, java.awt.BorderLayout.NORTH);

        buttonsRoomPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        newRoomButton.setText(bundle.getString("rooms.dialog.rooms.button.new")); // NOI18N
        newRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRoomButtonActionPerformed(evt);
            }
        });
        buttonsRoomPanel.add(newRoomButton);

        delRoomButton.setText(bundle.getString("rooms.dialog.rooms.button.delete")); // NOI18N
        buttonsRoomPanel.add(delRoomButton);

        formRoomPanel.add(buttonsRoomPanel, java.awt.BorderLayout.CENTER);

        roomsPanel.add(formRoomPanel, java.awt.BorderLayout.SOUTH);

        typesroomsTabbedPane.addTab(bundle.getString("room.dialog.tab.rooms"), roomsPanel); // NOI18N

        getContentPane().add(typesroomsTabbedPane, java.awt.BorderLayout.CENTER);

        typeroomsClosePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        typeroomsClosePanel.setLayout(new java.awt.GridBagLayout());

        close.setText(bundle.getString("dialog.close")); // NOI18N
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 11, 11);
        typeroomsClosePanel.add(close, gridBagConstraints);

        getContentPane().add(typeroomsClosePanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void delRoomTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delRoomTypeButtonActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_delRoomTypeButtonActionPerformed

    private void newTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTypeButtonActionPerformed
        setRoomType(new RoomType());
    }//GEN-LAST:event_newTypeButtonActionPerformed

    private void newRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newRoomButtonActionPerformed
        setRoom(new Room());
    }//GEN-LAST:event_newRoomButtonActionPerformed

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        closeDialog(null);
    }//GEN-LAST:event_closeActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsRoomPanel;
    private javax.swing.JPanel buttonsRoomTypePanel;
    private javax.swing.JButton close;
    private javax.swing.JButton delRoomButton;
    private javax.swing.JButton delRoomTypeButton;
    private javax.swing.JTextField description;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JPanel formRoomPanel;
    private javax.swing.JPanel formRoomTypePanel;
    private javax.swing.JPanel formRoomTypesPanel;
    private javax.swing.JPanel formRoomsPanel;
    private javax.swing.JTextField name;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton newRoomButton;
    private javax.swing.JButton newTypeButton;
    private javax.swing.JTextField number;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JTextField price;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JScrollPane roomScrollPane;
    private javax.swing.JTable roomTable;
    private javax.swing.JScrollPane roomTypeScrollPane;
    private javax.swing.JTable roomTypeTable;
    private javax.swing.JPanel roomTypesPanel;
    private javax.swing.JPanel roomsPanel;
    private javax.swing.JComboBox typeCombo;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JPanel typeroomsClosePanel;
    private javax.swing.JTabbedPane typesroomsTabbedPane;
    // End of variables declaration//GEN-END:variables

    private void setRoomType(RoomType roomType) {      
        if (roomType != null) {
            name.setText(roomType.getName());
            price.setText(Util.dbl2str(roomType.getBasePrice()));
            description.setText(roomType.getDescription());
        } else {
            name.setText("");
            price.setText("");
            description.setText("");
        }                    
    }
    
    private void setRoom(Room room) {
        if (room != null) {
            number.setText(String.valueOf(room.getNumber()));
            typeCombo.setSelectedItem(room.getType()); 
        } else {
           number.setText("");
           typeCombo.setSelectedItem(null);             
        }
        typeCombo.repaint();
    }

    private void roomTypeChanged(int index) {
        setRoomType(roomTypeTableModel.getRoomType(index));
    }        

    private void roomChanged(int index) {
        setRoom(roomTableModel.getRoom(index));
    }        
}
