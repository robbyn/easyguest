package org.tastefuljava.ezguest.gui.config;

import org.tastefuljava.ezguest.data.ArticleCategory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tastefuljava.ezguest.session.EasyguestSession;
import org.tastefuljava.ezguest.util.ListenerList;
import org.tastefuljava.ezguest.util.Util;

public class ArticleCategoryTreeModel implements TreeModel {
    private static final Log LOG = LogFactory.getLog(ArticleCategoryTreeModel.class);

    private final ListenerList listeners = new ListenerList();
    private final TreeModelListener notifier
            = listeners.getNotifier(TreeModelListener.class);
    private final EasyguestSession sess;
    private Node root;

    public ArticleCategoryTreeModel(EasyguestSession sess) {
        this.sess = sess;
    }

    public int getCategoryId(Object object) {
        if (object instanceof Node) {
            return ((Node)object).id;
        }
        return -1;
    }

    @Override
    public Object getRoot() {
        if (root == null) {
            sess.begin();
            try {
                ArticleCategory cat = sess.getObjectById(ArticleCategory.class, 1);
                if (cat == null) {
                    LOG.info("Root category does not exist: create it");
                    cat = new ArticleCategory();
                    cat.setName(Util.getResource("articles.rootcategory"));
                    sess.makePersistent(cat);
                    sess.commit();
                }
                root = new Node(null, cat.getId(), cat.getName());
            } finally {
                sess.end();
            }
        }
        return root;
    }

    @Override
    public Object getChild(Object object, int i) {
        Node node = (Node)object;
        if (node.children != null) {
            sess.begin();
            try {
                requireChildren(node);
            } finally {
                sess.end();
            }
        }
        return node.children.get(i);
    }

    @Override
    public int getChildCount(Object object) {
        Node node = (Node)object;
        if (node.children == null) {
            sess.begin();
            try {
                requireChildren(node);
            } finally {
                sess.end();
            }
        }
        return node.children.size();
    }

    @Override
    public boolean isLeaf(Object object) {
        return false;
    }

    @Override
    public void valueForPathChanged(TreePath treePath, Object value) {
        Node node = (Node)treePath.getLastPathComponent();
        if (value instanceof String && !value.equals(node.name)) {
            node.name = (String)value;
            sess.begin();
            try {
                ArticleCategory cat = sess.getObjectById(ArticleCategory.class, node.id);
                if (cat == null) {
                    notifyRemoved(node);
                    throw new RuntimeException("Node was removed: " + node.name);
                }
                cat.setName(node.name);
                sess.commit();
            } finally {
                sess.end();
            }
            notifyChanged(node);
        }
    }

    public Object addNode(TreePath parentPath, String name) {
        Node node;
        Node parent = (Node)parentPath.getLastPathComponent();
        sess.begin();
        try {
            ArticleCategory parentCat = sess.getObjectById(
                    ArticleCategory.class, parent.id);
            requireChildren(parent, parentCat);
            ArticleCategory cat = new ArticleCategory();
            cat.setName(name);
            cat.setParent(parentCat);
            sess.makePersistent(cat);
            node = new Node(parent, cat.getId(), cat.getName());
            sess.commit();
        } finally {
            sess.end();
        }
        parent.children.add(node);
        notifyInserted(node);
        return node;
    }

    public void removeNode(TreePath path) {
        Node node = (Node)path.getLastPathComponent();
        Node parent = node.parent;
        sess.begin();
        try {
            ArticleCategory cat = sess.getObjectById(ArticleCategory.class, node.id);
            if (cat == null) {
                notifyRemoved(node);
                throw new RuntimeException("Node was removed: " + node.name);
            }
            ArticleCategory parentCat = sess.getObjectById(
                    ArticleCategory.class, parent.id);
            if (parentCat == null) {
                notifyRemoved(parent);
                throw new RuntimeException("Node was removed: " + parent.name);
            }
            parentCat.getSubCategories().remove(cat);
            sess.deletePersistent(cat);
            sess.commit();
        } finally {
            sess.end();
        }
        notifyRemoved(node);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent == null || child == null) {
            return -1;
        }
        Node node = (Node)parent;
        if (node.children == null) {
            return -1;
        }
        return node.children.indexOf(child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener listener) {
        listeners.addListener(listener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        listeners.removeListener(listener);
    }

    private void checkNode(Node node, ArticleCategory cat) {
        if (cat == null) {
            notifyRemoved(node);
            throw new RuntimeException("Node was removed: " + node.name);
        }
        if (!node.name.equals(cat.getName())) {
            node.name = cat.getName();
            notifyChanged(node);
        }
    }

    private void checkNode(Node node) {
        ArticleCategory cat = sess.getObjectById(ArticleCategory.class, node.id);
        checkNode(node, cat);
    }

    private void requireChildren(Node node) {
        ArticleCategory cat = sess.getObjectById(ArticleCategory.class, node.id);
        checkNode(node, cat);
        requireChildren(node, cat);
    }

    private void requireChildren(Node node, ArticleCategory cat) {
        if (node.children == null) {
            node.children = new ArrayList<>();
            for (ArticleCategory child: cat.getSubCategories()) {
                node.children.add(new Node(node, child.getId(), child.getName()));
            }
            Collections.sort(node.children);
        }
    }

    private void notifyRemoved(Node node) {
        Node parent = node.parent;
        int index = -1;
        if (parent != null && parent.children != null) {
            index = parent.children.indexOf(node);
        }
        if (index < 0) {
            notifier.treeNodesRemoved(new TreeModelEvent(this, node.getPath()));
        } else if (parent != null) {
            notifier.treeNodesRemoved(new TreeModelEvent(this, parent.getPath(), 
                    new int[] {index}, new Object[] {node}));
        }
    }

    private void notifyChanged(Node node) {
        notifier.treeNodesChanged(new TreeModelEvent(this, node.getPath()));
    }

    private void notifyInserted(Node node) {
        Node parent = node.parent;
        int index = -1;
        if (parent != null && parent.children != null) {
            index = parent.children.indexOf(node);
        }
        if (index < 0) {
            notifier.treeNodesInserted(new TreeModelEvent(this, node.getPath()));
        } else if (parent != null) {
            notifier.treeNodesInserted(new TreeModelEvent(this, parent.getPath(), 
                    new int[] {index}, new Object[] {node}));
        }
    }

    void releaseChildren(TreePath path) {
        Node node = (Node)path.getLastPathComponent();
        node.children = null;
    }

    private static class Node implements Comparable<Node> {
        Node parent;
        int id;
        String name;
        long timestamp;
        List<Node> children;

        Node(Node parent, int id, String name) {
            this.parent = parent;
            this.id = id;
            this.name = name;
        }

        @Override
        public int hashCode() {
            return id & 0x7FFFFFFF;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Node && ((Node)other).id == id;
        }

        @Override
        public String toString() {
            return name;
        }

        public int getLevel() {
            int level = 0;
            Node n = parent;
            while (n != null) {
                ++level;
                n = n.parent;
            }
            return level;
        }

        public Object[] getPath() {
            Object nodes[] = new Object[getLevel()+1];
            Node n = this;
            for (int i = nodes.length; --i >= 0; ) {
                nodes[i] = n;
                n = n.parent;
            }
            return nodes;
        }

        @Override
        public int compareTo(Node other) {
            int res = name.compareTo(other.name);
            if (res != 0) {
                return res;
            } else if (id < other.id) {
                return -1;
            } else if (id > other.id) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
