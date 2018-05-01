/*
 *  (c) Tyler Hostager, 2018.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */



import org.jdesktop.swingx.JXPanel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

/**
 *
 * <p>
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
