package org.tastefuljava.ezguest.components;
import java.util.List;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.UIManager;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ToolTipUI;

public class MultilineToolTipUI extends ToolTipUI {
    private static final MultilineToolTipUI INSTANCE = new MultilineToolTipUI();
    private static final int INSET = 3;

    private MultilineToolTipUI() {
    }

    public static void install() {
        Class clazz = MultilineToolTipUI.class;
        UIManager.put("ToolTipUI", clazz.getName());
        UIManager.put(clazz.getName(), clazz);
    }

    public static ComponentUI createUI(JComponent c) {
        return INSTANCE;
    }

    @Override
    public void installUI(JComponent c) {
        LookAndFeel.installColorsAndFont(c, "ToolTip.background",
                "ToolTip.foreground", "ToolTip.font");
        LookAndFeel.installBorder(c, "ToolTip.border");
    }

    @Override
    public void uninstallUI(JComponent c) {
        LookAndFeel.uninstallBorder(c);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Font font = c.getFont();
        FontMetrics fm = c.getFontMetrics(font);
        int lineHeight = fm.getHeight();
        String lines[] = breakupLines(((JToolTip)c).getTipText());
        int height = lineHeight*lines.length;
        int width = 0;
        for (int i = 0; i < lines.length; ++i) {
            int w = fm.stringWidth(lines[i]);
            if (w > width) {
                width = w;
            }
        }
        return new Dimension(width + 2*INSET, height + 2*INSET);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Font font = c.getFont();
        FontMetrics fm = c.getFontMetrics(font);
        int ascent = fm.getAscent();
        int lineHeight = fm.getHeight();
        String lines[] = breakupLines(((JToolTip)c).getTipText());
        Dimension dim = c.getSize();
        g.setColor(c.getBackground());
        g.fillRect(0, 0, dim.width, dim.height);
        g.setColor(c.getForeground());
        int y = ascent + INSET;
        for (int i = 0;  i < lines.length; ++i) {
            g.drawString(lines[i], INSET, y);
            y += lineHeight;
        }
    }

    private static String[] breakupLines(String s) {
        List<String> lines = new ArrayList<>();
        int begin = 0;
        while (true) {
            int end = s.indexOf('\n', begin);
            if (end < 0) {
                lines.add(s.substring(begin));
                break;
            }
            lines.add(s.substring(begin, end));
            begin = end+1;
        }
        return lines.toArray(new String[lines.size()]);
    }
}
