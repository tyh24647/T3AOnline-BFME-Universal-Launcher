import org.jdesktop.swingx.JXPanel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 3/2/18
 */
public class TDetailPanel extends JXPanel {

    public TDetailPanel() {

    }

    public TDetailPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public TDetailPanel(LayoutManager layout) {
        super(layout);
    }


    public TDetailPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public float getAlpha() {
        return super.getAlpha();
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        repaint();
    }

    public JXPanel getJXPanel() {
        return this;
    }

    public JPanel getJPanel() {
        return (JPanel) getJXPanel();
    }
}
