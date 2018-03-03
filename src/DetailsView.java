import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.image.GaussianBlurFilter;
import org.jdesktop.swingx.JXPanel;
import org.softsmithy.lib.swing.JXScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 3/2/18
 */
public class DetailsView extends JXPanel {
    private DetailPanel detailPanel;
    private JXScrollPane scrollPane;
    private BufferedImage blurBuffer;
    private BufferedImage backBuffer;
    private float alpha = 0.0f;

    public DetailsView(DetailPanel detailPanel) {
        setLayout(new GridBagLayout());

        this.detailPanel = detailPanel;
        this.detailPanel.setAlpha(0.0f);
        add(detailPanel, new GridBagConstraints());
        setOpaque(false);

        // Should also disable key events...
        addMouseListener(new MouseAdapter() { });
    }

    public void fadeIn() {
        createBlur();

        setVisible(true);
        SwingUtilities.invokeLater(() -> {
            Animator animator = PropertySetter.createAnimator(
                    400, detailPanel, "alpha", 1.0f);
            animator.setAcceleration(0.2f);
            animator.setDeceleration(0.3f);
            animator.addTarget(
                    new PropertySetter(DetailsView.this, "alpha", 1.0f));
            animator.start();
        });
    }

    private void createBlur() {
        JRootPane root = SwingUtilities.getRootPane(this);
        blurBuffer = GraphicsUtilities.createCompatibleImage(
                getWidth(), getHeight());
        Graphics2D g2 = blurBuffer.createGraphics();
        root.paint(g2);
        g2.dispose();

        backBuffer = blurBuffer;

        blurBuffer = GraphicsUtilities.createThumbnail(
                blurBuffer, getWidth() / 2);
        blurBuffer = new GaussianBlurFilter(5).filter(blurBuffer, null);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isVisible() && blurBuffer != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(backBuffer, 0, 0, null);

            g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
            g2.drawImage(blurBuffer, 0, 0, getWidth(), getHeight(), null);
            g2.dispose();
        }
    }
}