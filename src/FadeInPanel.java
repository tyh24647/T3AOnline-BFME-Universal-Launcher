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



import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import static java.awt.RenderingHints.*;

/**
 *
 * <p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 2/19/18
 */
public class FadeInPanel extends JPanel implements ActionListener, ImageObserver {
    private static final Image BFME1_IMAGE = ImageAsset.BFME1.getImage();
    private static final Image BFME2_IMAGE = ImageAsset.BFME2.getImage();
    private static final Image ROTWK_IMAGE = ImageAsset.ROTWK.getImage();

    private Image selectedImg1, selectedImg2;
    private Timer timer1 = new Timer(30, this);
    private Timer timer2 = new Timer(30, this);

    private static float alpha = 0.9f;
    private static float alpha2 = 0.1f;
    private boolean shouldReverse = false;

    /**
     * Default constructor
     */
    public FadeInPanel() {
        repaint();

        /*

        TODO TEST TESTA TEST TESTE TSETES
        */
        loadImageAssetFromActionCommand("BFME1");  //   DOESNT WORK
        /*
        TODO TEST TESTA TEST TESTE TSETES

         */
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
        return super.imageUpdate(img, infoflags, x, y, w, h);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        renderBackgroundImage(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            var imgUrl = getClass().getResource("assets/hd_1122-copy-2.gif");
            Icon imgIcon = new ImageIcon(imgUrl);
            var img = ((ImageIcon) imgIcon).getImage();

            var tmpRect = getVisibleRect();

            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(img, tmpRect.x, tmpRect.y, tmpRect.width, tmpRect.height, this); //graphics2D.drawImage(img, 0, 0, 800, 640, null);
            g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHint(KEY_RENDERING, VALUE_RENDER_DEFAULT);
            g2d.setRenderingHint(KEY_RESOLUTION_VARIANT, VALUE_RESOLUTION_VARIANT_SIZE_FIT);

            imageUpdate(img, Image.SCALE_SMOOTH, 0, 0, 800, 680);

            FadeInPanel.this.repaint();
            g2d.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderBackgroundImage(@NotNull Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC, (
                                selectedImg2 != null ? (
                                        shouldReverse ? alpha2 : alpha
                                ) : alpha
                        )
                )
        );

        g2d.drawImage(
                selectedImg2 != null ? this.selectedImg2 : (
                        selectedImg1 == null ?
                                BFME1_IMAGE
                                : this.selectedImg1
                ),

                0, 0, this
        );

        g2d.dispose();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e != null) {
            if (shouldReverse) {
                alpha += -0.01f;
                if (alpha <= 0) {
                    alpha = 0;
                }

                alpha2 += 0.01f;
                if (alpha2 >= 1) {
                    alpha2 = 1;
                }
            } else {
                alpha += 0.01f;
                if (alpha >= 1) {
                    alpha = 1;
                }

                alpha2 += -0.01f;
                if (alpha2 <= 0) {
                    alpha2 = 0;
                }
            }

            if (alpha == 0 || alpha == 1 || alpha2 == 0 || alpha2 == 1) {
                stopTimers();
            } else {
                repaint();
            }
        }
    }

    private void stopTimers() {
        timer1.stop();
        timer2.stop();
    }

    public void fireImgShouldChange() {
        shouldReverse = !shouldReverse;
        timer1.start();
        timer2.start();
        repaint();
    }

    public void chgImgSelectionsForActionCommands(String cmd1, String cmd2) {
        this.selectedImg1 = loadImageAssetFromActionCommand(cmd1);
        this.selectedImg2 = loadImageAssetFromActionCommand(cmd2); // new ImageIcon(getClass().getResource("assets/minas-morgul.png")).getImage(); //
        fireImgShouldChange();
    }

    private Image loadImageAssetFromActionCommand(String cmd) {
        if (cmd == null || ImageAsset.BFME1.getSrcPath().contains(cmd) || cmd.equals("BFME") || cmd.equals("BFME1")) {
            try {
                var imgUrl = getClass().getResource("assets/hd_1122-copy-2.gif");
                Icon imgIcon = new ImageIcon(imgUrl);
                var img = ((ImageIcon) imgIcon).getImage();
                //img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
                ((ImageIcon) imgIcon).setImageObserver(this);
                var lbl = new JLabel(imgIcon);
                //getRootPane().getContentPane().add(lbl);



                var bkgdPanel = new JPanel(new BorderLayout()) {

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        var tmpRect = getVisibleRect();

                        Graphics2D g2d = (Graphics2D) g;
                        g2d.drawImage(img, tmpRect.x, tmpRect.y, tmpRect.width, tmpRect.height, this); //graphics2D.drawImage(img, 0, 0, 800, 640, null);

                        g2d.dispose();
                        FadeInPanel.this.repaint();
                        repaint();
                    }

                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(800, 640);
                    }

                    @Override
                    public boolean isVisible() {
                        return true;
                    }
                };

                getRootPane().getContentPane().add(bkgdPanel, BorderLayout.CENTER);
                bkgdPanel.repaint();
                validate();
                repaint();


                img.flush();
                //validate();
                //repaint();

                //lbl = new JLabel(imgIcon);


                //getRootPane().getContentPane().remove(lbl);
                //getRootPane().getContentPane().add(lbl);
                //validate();
                //repaint();
                return img;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return BFME1_IMAGE;
        } else if (ImageAsset.BFME2.getSrcPath().contains(cmd) || cmd.equals("BFME2")) {
            return
                    BFME2_IMAGE;
                    /*
                    new ImageIcon(getClass().getResource(
                          "assets/hd_1122-copy-2.gif" // "assets/minas-morgul.png"
                    )).getImage(); //
                    */
        } else if (ImageAsset.ROTWK.getSrcPath().contains(cmd) || cmd.equals("ROTWK")) {
            return ROTWK_IMAGE;
        }

        return BFME1_IMAGE;    // BFME1 as default
    }
}
