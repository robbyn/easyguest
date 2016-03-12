/*
 * TariffCellEditor.java
 *
 * Created on 30 novembre 2002, 17:43
 */

package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.session.EasyguestSession;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JComboBox;
import org.tastefuljava.ezguest.data.Tariff;

/**
 * @author  denis
 */
@SuppressWarnings("serial")
public class TariffCellEditor extends AbstractCellEditor
        implements TableCellEditor {
    private JComboBox tariffComboBox;
    private TariffComboBoxRenderer tariffComboBoxRenderer;
    private TariffComboBoxModel tariffComboBoxModel;

    public TariffCellEditor(EasyguestSession sess,
            TariffTableModel tariffModel) {
        tariffComboBoxRenderer = new TariffComboBoxRenderer();
        tariffComboBoxModel = new TariffComboBoxModel(sess, tariffModel);
        tariffComboBox = new JComboBox();
        tariffComboBox.setModel(tariffComboBoxModel);
        tariffComboBox.setRenderer(tariffComboBoxRenderer);
        tariffComboBox.setBorder(null);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        tariffComboBox.setSelectedItem((Tariff)value);
        return tariffComboBox;
    }

    public Object getCellEditorValue() {
        return tariffComboBox.getSelectedItem();
    }
}
