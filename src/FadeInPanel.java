/*******************************************************************************
 *
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
 ******************************************************************************/



import javax.swing.*;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 2/19/18
 */
public class FadeInPanel extends JPanel implements ActionListener {
    public static final Image INITIAL_IMG = new ImageIcon("assets/BFME1.png").getImage();

    //private Image img1 = new ImageIcon("assets/BFME1.png").getImage();
    //private Image img2 = new ImageIcon("assets/BFME2.png").getImage();
    //private Image img3 = new ImageIcon("assets/ROTWK.png").getImage();
    private static final Image BFME1_IMAGE = ImageAsset.BFME1.getImage();
    private static final Image BFME2_IMAGE = ImageAsset.BFME2.getImage();
    private static final Image ROTWK_IMAGE = ImageAsset.ROTWK.getImage();
    //private Image selectedImg1, selectedImg2;
    private Image selectedImg1, selectedImg2;
    private Timer timer1 = new Timer(25, this);
    private Timer timer2 = new Timer(25, this);

    private static float alpha = 1.0f;
    private static float alpha2 = 0.0f;
    private boolean shouldReverse = false;
    private boolean isFirstRender = true;

    public FadeInPanel() {
        //timer1.start();
        //this.selectedImg1 = this.img1;
        this.selectedImg1 = BFME1_IMAGE;
        repaint();
    }

    public FadeInPanel(Image img1, Image img2) {
        //this.img1 = img1;
        //this.img2 = img2;
    }

    public FadeInPanel(Image img1, Image img2, Image img3) {
        //this.selectedImg1 = this.img1;
        //this.selectedImg2 = this.img2;


        //setImg1(img1);
        //setImg2(img2);
        //setImg3(img3);

        this.selectedImg1 = BFME1_IMAGE;
        //repaint();
        timer1.start();
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        if (isFirstRender) {
            g2d.drawImage(BFME2_IMAGE, 0, 0, null);
            g2d.drawImage(ROTWK_IMAGE, 0, 0, null);
            g2d.drawImage(BFME1_IMAGE, 0, 0, null);
        }

        if (selectedImg2 != null) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, shouldReverse ? alpha2 : alpha));
            //g2d.drawImage(selectedImg1, 0, 0, null);
            g2d.drawImage(selectedImg1, 0, 0, null);
            //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, shouldReverse ? alpha : alpha2));
            //g2d.drawImage(selectedImg2, 0, 0, null);
            g2d.drawImage(selectedImg2, 0, 0, null);

        } else {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
            g2d.drawImage(BFME1_IMAGE, 0, 0, null);
        }

        isFirstRender = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (e != null) {
            fireAlphaShouldChange();
        }
    }

    private void fireAlphaShouldChange() {
        if (shouldReverse) {
            alpha += -0.01f;
            if (alpha <= 0) {
                alpha = 0;
                //stopTimers();
            }

            alpha2 += 0.01f;
            if (alpha2 >= 1) {
                alpha2 = 1;
                //stopTimers();
            }
        } else {
            alpha += 0.01f;
            if (alpha >= 1) {
                alpha = 1;
                //stopTimers();
            }

            alpha2 += -0.01f;
            if (alpha2 <= 0) {
                alpha2 = 0;
                //stopTimers();
            }
        }

        if (alpha == 0 || alpha == 1 || alpha2 == 0 || alpha2 == 1) {
            stopTimers();
        } else {
            repaint();
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

    /*
    public void chgImgSelections(Image img1, Image img2) {
        setImg1(img1);
        setImg2(img2);

        this.selectedImg1 = img1 == null ? this.img1 : img1;
        this.selectedImg2 = img2;
        fireImgShouldChange();
    }
    */

    public void chgImgSelectionsForActionCommand(String cmd1, String cmd2) {
        this.selectedImg1 = loadImageAssetFromActionCommand(cmd1);
        this.selectedImg2 = loadImageAssetFromActionCommand(cmd2);
        fireImgShouldChange();
    }

    private Image loadImageAssetFromActionCommand(String cmd) {
        if (cmd == null || ImageAsset.BFME1.getSrcPath().contains(cmd)) {
            return BFME1_IMAGE;
        } else if (ImageAsset.BFME2.getSrcPath().contains(cmd)) {
            return BFME2_IMAGE;
        } else if (ImageAsset.ROTWK.getSrcPath().contains(cmd)) {
            return ROTWK_IMAGE;
        }

        return BFME1_IMAGE;    // BFME1 as default
    }


    //region SETTERS

    /*
    public @NotNull Image setImg1(Image img1) {
        try {
            //return this.img1 = img1 == null ? ImageIO.read(getClass().getResource("BFME1.png")) : img1;
            return this.img1 = img1 == null ? ImageIO.read(getClass().getResource("BFME1.png")) : img1;
        } catch (Exception e) {
            LOG.err(e);
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        return this.img1;
    }

    public @NotNull Image setImg2(Image img2) {
        try {
            return this.img2 = img2 == null ? ImageIO.read(getClass().getResource("BFME2.png")) : img2;
        } catch (Exception e) {
            LOG.err(e);
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        return this.img2;
    }

    public @NotNull Image setImg3(Image img3) {
        try {
            this.img3 = img3 == null ? ImageIO.read(getClass().getResource("ROTWK.png")) : img3;
        } catch (Exception e) {
            LOG.err(e);
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        return this.img3;
    }
    */

    //endregion
}
