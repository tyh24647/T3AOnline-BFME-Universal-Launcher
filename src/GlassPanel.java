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



import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import java.awt.*;;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;

/**
 *
 * <p>
 *
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/27/18
 */
public class GlassPanel extends JPanel implements FocusListener {
    private final Image SHADED_BLUR = ImageAsset.SHADED_BLUR.getImage();
    private final Image DIFFERENCE_CLOUDS = ImageAsset.DIFF_CLOUDS.getImage();
    private BufferedImage background, transparencyImg;
    private Image test;

    /**
     *
     */
    public GlassPanel() {
        initViewDefaults();
    }

    /**
     *
     * @param layout
     */
    public GlassPanel(LayoutManager layout) {
        setLayout(layout);
        initViewDefaults();
        if (SHADED_BLUR != null) {
            transparencyImg = (BufferedImage) SHADED_BLUR;
        }
    }

    /**
     *
     */
    private void initViewDefaults() {

        /*
        addMouseListener(new MouseAdapter() {});

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        });
        */

        addFocusListener(this);
        setOpaque(true/*false*/);
        //setFocusable(true);
        setFocusable(false);
        setBackground(new Color(1, 1, 1, 0.10f));
    }

    /**
     *
     * @param v
     */
    @Override
    public final void setVisible(boolean v) {
        // Make sure we grab the focus so that key events don't go astray.
        if (v) {
            requestFocus();

            Container parent = SwingUtilities.getAncestorOfClass(JRootPane.class, this);
            if (parent != null) {
                JRootPane rootPane = (JRootPane) parent;

                /*
                BufferedImage img = new BufferedImage(rootPane.getWidth(), rootPane.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = img.createGraphics();
                rootPane.printAll(g2d);
                g2d.dispose();
                */

                if (transparencyImg != null) {
                    Graphics2D g2d = transparencyImg.createGraphics();
                    g2d.setComposite(AlphaComposite.SrcAtop.derive(0.75f));
                    g2d.drawImage(transparencyImg, 0, 0, 400, 300, null);
                    //rootPane.paint(g2d);

                    g2d.dispose();
                }

                //background = GlowEffectFactory.generateBlur(img, 40);
            }

        }

        super.setVisible(v);
    }

    /**
     *
     * @param fe
     */
    @Override
    public final void focusLost(FocusEvent fe) {
        /*
        if (isVisible()) {
            requestFocus();
        }
        */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void paint(Graphics g) {
        super.paint(g);

        /*
        if (transparencyImg != null) {
            Graphics2D g2d = transparencyImg.createGraphics();
            g2d.setComposite(AlphaComposite.SrcAtop.derive(0.75f));
            g2d.drawImage(transparencyImg, 0, 0, 400, 300, null);
            g2d.dispose();
        }
        */
    }

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (transparencyImg != null) {
            Graphics2D g2d = transparencyImg.createGraphics();
            g2d.setComposite(AlphaComposite.SrcAtop.derive(1f));
            g2d.drawImage(transparencyImg, 0, 0, 400, 300, null);
            g2d.dispose();
        }
    }

    /**
     *
     * @param fe
     */
    @Override
    public void focusGained(FocusEvent fe) {
        // nothing to do
    }
}