import com.sun.istack.internal.NotNull;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.image.GaussianBlurFilter;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

/**
 * <p>
 *     DetailsView provides a specialized view within the hierarchy of the layers
 *     of the main window. This allows for functionality such as animation effects,
 *     view blurring, and alpha modification.
 * </p>
 * <p>
 *     This class should be used as a <code>GlassPane</code> object in most cases,
 *     and shouldn't be used to modify the alpha values of primary views. Instead,
 *     it should overlay on top of the frame, and blur the specified background panel
 *     behind it with the given bounds.
 * </p>
 * 
 * @see org.jdesktop.swingx.JXPanel for Inherited superclass method implementations.
 * @see T3ALauncher#performJava9UpdateBeforeLaunch() for a usage example
 * @see <a href="http://www.curious-creature.com/2007/08/01/blurred-background-for-dialogs-extreme-gui-makeover-2007">Curious-Creature</a>
 *     for original process used in these classes.
 * 
 * 
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 3/2/18
 */
public class DetailsView extends JXPanel {
    private TDetailPanel detailPanel;
    private BufferedImage blurBuffer;
    private BufferedImage backBuffer;
    private float alpha = 0.0f;

    /**
     * <p>
     * Constructs a <code>DetailsView</code> object using the given <code>TDetailPanel</code>
     * view panel (custom subclass of {@code JFXPanel}).
     * </p>
     * 
     * @param detailPanel   The <code>TDetailPanel</code> object in which the images
     *                      and other details should display.
     */
    public DetailsView(@NotNull TDetailPanel detailPanel) {
        setLayout(new GridBagLayout());
        this.detailPanel = detailPanel;
        this.detailPanel.setAlpha(0.0f);
        add(detailPanel, new GridBagConstraints());
        setOpaque(false);
        addMouseListener(new MouseAdapter() { });   // <-- empty mouse listener to disable key events
    }

    /**
     * <p>
     *     Causes a fade effect as the popup panel is displayed.
     * </p>
     * 
     * <p>
     *     This method blurs and darkens the background image while leaving the
     *     foreground image as it is, giving the effect of a popup window as an
     *     overlay, rather than a separate dialog box.
     * </p>
     */
    public void fadeIn() {
        applyBlurEffectToBkgdWindow();
        setVisible(true);

        SwingUtilities.invokeLater(() -> {
            Animator animator = PropertySetter.createAnimator(400, detailPanel, "alpha", 1.0f);
            animator.setAcceleration(0.2f);
            animator.setDeceleration(0.3f);
            animator.addTarget(new PropertySetter(DetailsView.this, "alpha", 1.0f));
            animator.start();
        });
    }

    /**
     * <p>
     *     Changes the background image to a blurry, darkened version in order to provide
     *     contrast between the layers when displaying a message to the user.
     * </p>
     * 
     * @apiNote Make sure to create this object with a <code>TDetailPanel</code> object that
     * has both a root pane and a frame object. Otherwise, if the object is by itself, this will
     * throw a <code>NullPointerException</code>.
     * 
     * If the object doesn't have a specified size, an exception will be thrown in the
     * method: <code>GraphicsUtilities.createThumbnail(BufferedImage image, int newSize)</code>
     * 
     * @see GraphicsUtilities for implementation details
      */
    private void applyBlurEffectToBkgdWindow() {
        JRootPane root = SwingUtilities.getRootPane(this);
        blurBuffer = GraphicsUtilities.createCompatibleImage(getWidth(), getHeight());
        Graphics2D g2 = blurBuffer.createGraphics();
        root.paint(g2);
        g2.dispose();

        backBuffer = blurBuffer;
        blurBuffer = GraphicsUtilities.createThumbnail(blurBuffer, getWidth() / 2);
        blurBuffer = new GaussianBlurFilter(5).filter(blurBuffer, null);
    }

    /**
     * <p>
     *     Returns the alpha value for the object (inherited from <code>JXPanel</code>.
     * </p>
     * 
     * @return  The alpha transparency value of the panel
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * <p>
     *     Assigns the specified alpha transparency value to the current instance
     *     of the object, then repaints itself to apply the graphics changes.
     * </p>
     * 
     * @implNote Be sure to pass in a value greater than 0.0 and less than 1.0, otherwise
     * the value is invalid.
     * 
     * @implNote If the transparency doesn't change after changing the alpha value, make
     * sure that the <code>TDetailPanel</code>'s opacity is set to <code>true</code>, which
     * is counter-intuitive, however if the value is <code>false</code>, the alpha value cannot
     * be applied because it will always be transparent.
     * 
     * @param alpha     The desired transparency value in which to set the rendering panel
     *                  when the <code>paintComponent()</code> method is called.
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    /**
     * <p>
     *     Paints this <code>JComponent</code> with customizations including image
     *     effects.
     * </p>
     * <p>
     *     Ensure the size of this object has been set to a valid value before this
     *     method is called, otherwise an <code>Exception</code> is thrown due to
     *     lacking the minimum size requirement to render the image.
     * </p>
     * 
     * @param g     The <code>Graphics</code> object in which the renderer should
     *              paint, and may be passed to its superclass using
     *              {@code super.paintComponent(g);} if the superclass painting methods
     *              are desired.
     */
    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);

        if (isVisible() && blurBuffer != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

            g2.drawImage(backBuffer, 0, 0, null);
            g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
            g2.drawImage(blurBuffer, 0, 0, getWidth(), getHeight(), null);
            g2.dispose();
        }
    }
}
