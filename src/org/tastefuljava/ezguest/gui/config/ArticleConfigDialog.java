package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.data.Article;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.tastefuljava.ezguest.util.Util;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.awt.Frame;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.tree.TreePath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author  Maurice Perry
 */
@SuppressWarnings("serial")
public class ArticleConfigDialog extends javax.swing.JDialog {
    private static final Log LOG = LogFactory.getLog(ArticleConfigDialog.class);

    private final EasyguestSession sess;
    private final ArticleCategoryTreeModel artCategoryTreeModel;
    private final ArticleTableModel articleTableModel;
    private KeyStroke ks;

    public ArticleConfigDialog(JFrame parent, EasyguestSession sess) {
        super(parent, true);
        this.sess = sess;
        initComponents();
        artCategoryTreeModel = new ArticleCategoryTreeModel(sess);
        articleTableModel = new ArticleTableModel(sess);
        categoryTree.setModel(artCategoryTreeModel);
//        categoryTree.setCellEditor(new CategoryCellEditor(categoryTree));
        articleTable.setModel(articleTableModel);
        articleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        articleScrollPane.getViewport().setBackground(articleTable.getBackground());
        articleTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                articleChanged(articleTable.getSelectedRow());
            }
        });
        initialize(parent);
    }

    private void initialize(JFrame parent) {
        setLocation(parent.getX()+(parent.getWidth()-getWidth())/2,
                parent.getY()+(int)(parent.getWidth()*(0.618/1.618))-getHeight()/2);
        pack();
    }

    public void insertCategory(Frame frame) {
        TreePath treePath = categoryTree.getSelectionPath();
        if (treePath == null) {
            treePath = categoryTree.getPathForRow(0);
        }
        Object node = artCategoryTreeModel.addNode(
                treePath, Util.getResource("articles.newcategory"));
        treePath = treePath.pathByAddingChild(node);
        categoryTree.startEditingAtPath(treePath);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        categoriesarticlesPanel = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        categoriesPanel = new javax.swing.JPanel();
        categoriesLabel = new javax.swing.JLabel();
        categoriesScrollPane = new javax.swing.JScrollPane();
        categoryTree = new javax.swing.JTree();
        categoriesNewDelPanel = new javax.swing.JPanel();
        newCategory = new javax.swing.JButton();
        deleteCategory = new javax.swing.JButton();
        articlesPanel = new javax.swing.JPanel();
        articlesLabel = new javax.swing.JLabel();
        articleScrollPane = new javax.swing.JScrollPane();
        articleTable = new javax.swing.JTable();
        formArticlePanel = new javax.swing.JPanel();
        formArticlesPanel = new javax.swing.JPanel();
        codeLabel = new javax.swing.JLabel();
        code = new javax.swing.JTextField();
        labelLabel = new javax.swing.JLabel();
        label = new javax.swing.JTextField();
        priceLabel = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        keystrokeLabel = new javax.swing.JLabel();
        keyStroke = new javax.swing.JTextField();
        buttonsArticlePanel = new javax.swing.JPanel();
        newArticleButton = new javax.swing.JButton();
        delArticleButton = new javax.swing.JButton();
        categoriesarticlesClosePanel = new javax.swing.JPanel();
        close = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org.tastefuljava.ezguest.resources"); // NOI18N
        setTitle(bundle.getString("articles.dialog.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        categoriesarticlesPanel.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOneTouchExpandable(true);

        categoriesPanel.setLayout(new java.awt.BorderLayout());

        categoriesLabel.setText(bundle.getString("articles.dialog.categories")); // NOI18N
        categoriesLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 0, 5));
        categoriesPanel.add(categoriesLabel, java.awt.BorderLayout.NORTH);

        categoriesScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 0, 5));

        categoryTree.setEditable(true);
        categoryTree.setLargeModel(true);
        categoryTree.setShowsRootHandles(true);
        categoryTree.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            public void treeExpanded(javax.swing.event.TreeExpansionEvent evt) {
            }
            public void treeCollapsed(javax.swing.event.TreeExpansionEvent evt) {
                categoryTreeTreeCollapsed(evt);
            }
        });
        categoryTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                categoryTreeValueChanged(evt);
            }
        });
        categoriesScrollPane.setViewportView(categoryTree);

        categoriesPanel.add(categoriesScrollPane, java.awt.BorderLayout.CENTER);

        categoriesNewDelPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        newCategory.setText(bundle.getString("articles.dialog.categories.button.new")); // NOI18N
        newCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCategoryActionPerformed(evt);
            }
        });
        categoriesNewDelPanel.add(newCategory);

        deleteCategory.setText(bundle.getString("articles.dialog.categories.button.delete")); // NOI18N
        deleteCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCategoryActionPerformed(evt);
            }
        });
        categoriesNewDelPanel.add(deleteCategory);

        categoriesPanel.add(categoriesNewDelPanel, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(categoriesPanel);

        articlesPanel.setLayout(new java.awt.BorderLayout());

        articlesLabel.setText(bundle.getString("articles.dialog.articles")); // NOI18N
        articlesLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 0, 5));
        articlesPanel.add(articlesLabel, java.awt.BorderLayout.NORTH);

        articleScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        articleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        articleTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        articleScrollPane.setViewportView(articleTable);

        articlesPanel.add(articleScrollPane, java.awt.BorderLayout.CENTER);

        formArticlePanel.setLayout(new java.awt.BorderLayout());

        formArticlesPanel.setLayout(new java.awt.GridBagLayout());

        codeLabel.setText(bundle.getString("articles.dialog.article.code")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        formArticlesPanel.add(codeLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 11);
        formArticlesPanel.add(code, gridBagConstraints);

        labelLabel.setText(bundle.getString("articles.dialog.article.label")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        formArticlesPanel.add(labelLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        formArticlesPanel.add(label, gridBagConstraints);

        priceLabel.setText(bundle.getString("articles.dialog.article.price")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        formArticlesPanel.add(priceLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 11);
        formArticlesPanel.add(price, gridBagConstraints);

        keystrokeLabel.setText(bundle.getString("articles.dialog.article.keys")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 11, 11);
        formArticlesPanel.add(keystrokeLabel, gridBagConstraints);

        keyStroke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyStrokeActionPerformed(evt);
            }
        });
        keyStroke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyStrokeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                keyStrokeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                keyStrokeKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 11, 11);
        formArticlesPanel.add(keyStroke, gridBagConstraints);

        formArticlePanel.add(formArticlesPanel, java.awt.BorderLayout.NORTH);

        buttonsArticlePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        newArticleButton.setText(bundle.getString("articles.dialog.articles.button.new")); // NOI18N
        newArticleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newArticleButtonActionPerformed(evt);
            }
        });
        buttonsArticlePanel.add(newArticleButton);

        delArticleButton.setText(bundle.getString("articles.dialog.articles.button.delete")); // NOI18N
        buttonsArticlePanel.add(delArticleButton);

        formArticlePanel.add(buttonsArticlePanel, java.awt.BorderLayout.CENTER);

        articlesPanel.add(formArticlePanel, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(articlesPanel);

        categoriesarticlesPanel.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(categoriesarticlesPanel, java.awt.BorderLayout.CENTER);

        categoriesarticlesClosePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        categoriesarticlesClosePanel.setLayout(new java.awt.GridBagLayout());

        close.setText(bundle.getString("dialog.close")); // NOI18N
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 11, 11);
        categoriesarticlesClosePanel.add(close, gridBagConstraints);

        getContentPane().add(categoriesarticlesClosePanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newArticleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newArticleButtonActionPerformed
        setArticle(new Article());
    }//GEN-LAST:event_newArticleButtonActionPerformed

    private void keyStrokeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyStrokeKeyReleased
        evt.consume();
    }//GEN-LAST:event_keyStrokeKeyReleased

    private void keyStrokeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyStrokeKeyTyped
        evt.consume();
    }//GEN-LAST:event_keyStrokeKeyTyped

    private void keyStrokeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyStrokeKeyPressed
        int keyCode = evt.getKeyCode();
        if (keyCode != 0 && keyCode != KeyEvent.VK_ALT 
                && keyCode != KeyEvent.VK_ALT_GRAPH
                && keyCode != KeyEvent.VK_CONTROL
                && keyCode != KeyEvent.VK_META
                && keyCode != KeyEvent.VK_SHIFT
                && keyCode != KeyEvent.VK_CAPS_LOCK) {
            System.out.println(keyCode);
            ks = KeyStroke.getKeyStrokeForEvent(evt);
            keyStroke.setText(Util.toString(ks));
        }
        evt.consume();
    }//GEN-LAST:event_keyStrokeKeyPressed

    private void keyStrokeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyStrokeActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_keyStrokeActionPerformed

    private void categoryTreeTreeCollapsed(javax.swing.event.TreeExpansionEvent evt) {//GEN-FIRST:event_categoryTreeTreeCollapsed
        TreePath path = evt.getPath();
        if (path != null) {
            artCategoryTreeModel.releaseChildren(path);
        }
    }//GEN-LAST:event_categoryTreeTreeCollapsed

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        closeDialog(null);
    }//GEN-LAST:event_closeActionPerformed

    private void categoryTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_categoryTreeValueChanged
        TreePath path = categoryTree.getSelectionPath();
        if (path == null) {
            articleTableModel.setCategoryId(-1);
        } else {
            int id = artCategoryTreeModel.getCategoryId(path.getLastPathComponent());
            articleTableModel.setCategoryId(id);
        }
    }//GEN-LAST:event_categoryTreeValueChanged

    private void deleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCategoryActionPerformed
        TreePath path = categoryTree.getSelectionPath();
        if (path != null) {
            artCategoryTreeModel.removeNode(path);
        }
    }//GEN-LAST:event_deleteCategoryActionPerformed

    private void newCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCategoryActionPerformed
        insertCategory(null);
    }//GEN-LAST:event_newCategoryActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane articleScrollPane;
    private javax.swing.JTable articleTable;
    private javax.swing.JLabel articlesLabel;
    private javax.swing.JPanel articlesPanel;
    private javax.swing.JPanel buttonsArticlePanel;
    private javax.swing.JLabel categoriesLabel;
    private javax.swing.JPanel categoriesNewDelPanel;
    private javax.swing.JPanel categoriesPanel;
    private javax.swing.JScrollPane categoriesScrollPane;
    private javax.swing.JPanel categoriesarticlesClosePanel;
    private javax.swing.JPanel categoriesarticlesPanel;
    private javax.swing.JTree categoryTree;
    private javax.swing.JButton close;
    private javax.swing.JTextField code;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JButton delArticleButton;
    private javax.swing.JButton deleteCategory;
    private javax.swing.JPanel formArticlePanel;
    private javax.swing.JPanel formArticlesPanel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField keyStroke;
    private javax.swing.JLabel keystrokeLabel;
    private javax.swing.JTextField label;
    private javax.swing.JLabel labelLabel;
    private javax.swing.JButton newArticleButton;
    private javax.swing.JButton newCategory;
    private javax.swing.JTextField price;
    private javax.swing.JLabel priceLabel;
    // End of variables declaration//GEN-END:variables

    private void setArticle(Article article) {
        if (article != null) {
            code.setText(article.getCode());
            label.setText(article.getLabel());
            price.setText(Util.dbl2str(article.getPrice()));
            keyStroke.setText(Util.toString(article.getKeyStroke()));
        } else {
            code.setText("");
            label.setText("");
            price.setText("");
            keyStroke.setText("");
        }
    }

    private void articleChanged(int index) {
        sess.begin();
        try {
            setArticle(articleTableModel.getArticle(index));
        } finally {
            sess.end();
        }
    }
}
