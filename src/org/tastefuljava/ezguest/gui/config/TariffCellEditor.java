package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.session.EasyguestSession;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JComboBox;
import org.tastefuljava.ezguest.data.Tariff;

@SuppressWarnings("serial")
public class TariffCellEditor extends AbstractCellEditor
        implements TableCellEditor {
    private final JComboBox tariffComboBox;
    private final TariffComboBoxRenderer tariffComboBoxRenderer;
    private final TariffComboBoxModel tariffComboBoxModel;

    public TariffCellEditor(EasyguestSession sess,
            TariffTableModel tariffModel) {
        tariffComboBoxRenderer = new TariffComboBoxRenderer();
        tariffComboBoxModel = new TariffComboBoxModel(sess, tariffModel);
        tariffComboBox = new JComboBox();
        tariffComboBox.setModel(tariffComboBoxModel);
        tariffComboBox.setRenderer(tariffComboBoxRenderer);
        tariffComboBox.setBorder(null);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        tariffComboBox.setSelectedItem((Tariff)value);
        return tariffComboBox;
    }

    @Override
    public Object getCellEditorValue() {
        return tariffComboBox.getSelectedItem();
    }
}
