package org.tastefuljava.ezguest.gui;

import org.tastefuljava.ezguest.data.Article;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class ArticleCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final EasyguestSession sess;
    private final JTextField textField = new JTextField();

    public ArticleCellEditor(EasyguestSession sess) {
        this.sess = sess;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
       textField.setText("");
       if (value instanceof Article) {
           textField.setText(((Article)value).getCode());
           textField.setSelectionStart(0);
           textField.setSelectionEnd(textField.getText().length());
       }
       return textField;
    }

    @Override
    public Object getCellEditorValue() {
        return sess.getArticle(textField.getText());
    }
}
