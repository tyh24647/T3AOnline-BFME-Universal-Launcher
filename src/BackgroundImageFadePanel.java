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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 2/26/18
 */
public class BackgroundImageFadePanel extends FadeInPanel {
    private Image b1Img, b2Img, rotwkImg;
    private String b1ImgPath, b2ImgPath, rotwkImgPath;

    public BackgroundImageFadePanel() {

    }

    public BackgroundImageFadePanel(String b1ImgPath, String b2ImgPath, String rotwkImgPath) {

    }

    public BackgroundImageFadePanel(Image b1Img, Image b2Img, Image rotwkImg) {

    }

    public BackgroundImageFadePanel(ImageIcon b1Img, ImageIcon b2Img, ImageIcon rotwkImg) {

    }

    private void initPanelDefaults() {
        setOpaque(true);
    }

    public void setB1Img(Image b1Img) {
        this.b1Img = b1Img;
    }

    public void setB2Img(Image b2Img) {
        this.b2Img = b2Img;
    }

    public void setRotwkImg(Image rotwkImg) {
        this.rotwkImg = rotwkImg;
    }

    public Image getB1Img() {
        Image tmp = null;

        if (b1Img == null) {
            try {
                ImageIO.read(getClass().getResource("assets/b1.png"));
            } catch (IOException e) {

            }
        }

        return tmp;
    }

    public Image getB2Img() {
        return b2Img;
    }

    public Image getRotwkImg() {
        return rotwkImg;
    }
}
