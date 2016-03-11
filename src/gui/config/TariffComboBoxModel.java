/*
 * TariffComboBoxModel.java
 *
 * Created on 30 November 2002, 17:57
 */

package gui.config;

import data.Tariff;
import session.EasyguestSession;
import java.util.List;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class TariffComboBoxModel extends AbstractListModel
        implements ComboBoxModel, TableModelListener {
    private Tariff selection = null;
    private List<Tariff> tariffs = new ArrayList<Tariff>();
    private TariffTableModel tariffModel;
    
    public TariffComboBoxModel(EasyguestSession sess, TariffTableModel tariffModel) {
        this.tariffModel = tariffModel;
        tariffs.addAll(sess.getExtent(Tariff.class));
        tariffModel.addTableModelListener(this);
    }

    public int getSize() {
        return tariffs.size();
    }

    public Object getElementAt(int index) {
        return tariffs.get(index);
    }

    public Object getSelectedItem() {
        return selection;
    }

    public void setSelectedItem(Object anItem) {
        selection = null;
        for (Tariff t: tariffs) {
            if (t.equals(anItem)) {
                selection = t;
                break;
            }
        }
    }

    public void add(Tariff tariff) {
        int pos = tariffs.size();
        tariffs.add(tariff);
        fireIntervalAdded(this, pos, pos);
    }

    public void remove(Tariff tariff) {
        int pos = tariffs.indexOf(tariff);
        if (pos >= 0) {
            tariffs.remove(pos);
            fireIntervalRemoved(this, pos, pos);
        }
    }

    public void change(Tariff tariff) {
        int pos = tariffs.indexOf(tariff);
        if (pos >= 0) {
            fireContentsChanged(this, pos, pos);
        }
    }

    public void tableChanged(TableModelEvent e) {
        switch (e.getType()) {
            case TableModelEvent.DELETE:
                for (int i = e.getFirstRow(); i <= e.getLastRow(); ++i) {
                    remove(tariffModel.getTariff(i));
                }
                break;
            case TableModelEvent.INSERT:
                for (int i = e.getFirstRow(); i <= e.getLastRow(); ++i) {
                    add(tariffModel.getTariff(i-1));
                }
                break;
        }
    }
}
