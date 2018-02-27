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

    private Image image = new ImageIcon("assets/b1.png").getImage();
    private Image image2 = new ImageIcon("assets/b2.png").getImage();
    private Timer timer = new Timer(20, this);
    private Timer timer2 = new Timer(20, this);

    private static float alpha = 1.0f;
    private static float alpha2 = 0.0f;
    private boolean shouldReverse = false;

    public FadeInPanel() {
        timer.start();
    }

    public FadeInPanel(Image img1, Image img2) {
        this.image = img1;
        this.image2 = img2;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(image, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha2));
        g2d.drawImage(image2, 0, 0, null);
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
                stopTimers();
            }

            alpha2 += 0.01f;
            if (alpha2 >= 1) {
                alpha2 = 1;
                stopTimers();
            }
        } else {
            alpha += 0.01f;
            if (alpha >= 1) {
                alpha = 1;
                stopTimers();
            }

            alpha2 += -0.01f;
            if (alpha2 <= 0) {
                alpha2 = 0;
                stopTimers();
            }
        }

        repaint();
    }

    private void stopTimers() {
        timer.stop();
        timer2.stop();
    }

    public void fireImageShouldChange() {
        shouldReverse = !shouldReverse;
        timer.start();
        timer2.start();
        repaint();
    }
}
