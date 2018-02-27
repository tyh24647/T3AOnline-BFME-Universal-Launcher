import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/27/18
 */
public class GlassPanel extends JPanel implements FocusListener {

    private BufferedImage background;

    public GlassPanel() {
        initViewDefaults();
    }

    public GlassPanel(LayoutManager layout) {
        setLayout(layout);
        initViewDefaults();
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

                BufferedImage img = new BufferedImage(rootPane.getWidth(), rootPane.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = img.createGraphics();
                rootPane.printAll(g2d);
                g2d.dispose();

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
        final Color old = g.getColor();
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width, getSize().height);
        g.setColor(old);
        super.paint(g);
    }

    @Override
    public void focusGained(FocusEvent fe) {
        // nothing to do
    }
}