import org.jdesktop.swingx.graphics.GraphicsUtilities;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * <p></p>
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

    private void initViewDefaults() {
        addMouseListener(new MouseAdapter() {});

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        });

        addFocusListener(this);
        setOpaque(false);
        setFocusable(true);
        setBackground(new Color(1, 1, 1, 0.10f));
    }

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

    // Once we have focus, keep it if we're visible
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

    @Override
    public void focusGained(FocusEvent fe) {
        // nothing to do
    }
}