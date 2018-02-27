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

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import java.awt.Image;

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

    public BackgroundImageFadePanel(Image b1Img, Image b2Img) {
        //super(b1Img, b2Img);
    }

    public BackgroundImageFadePanel(Image b1Img, Image b2Img, Image rotwkImg) {
        super(b1Img, b2Img, rotwkImg);
    }

    public BackgroundImageFadePanel(ImageIcon b1Img, ImageIcon b2Img, ImageIcon rotwkImg) {

    }

    private void initPanelDefaults() {
        setOpaque(true);
    }

    //region INHERITED METHODS


    @Override
    public void fireImgShouldChange() {
        super.fireImgShouldChange();
    }

    public void fireImageShouldChange(JRadioButton b1, JRadioButton b2) {
        Image tmpImg1 = new ImageIcon("assets/" + b1.getActionCommand() + ".png").getImage();
        Image tmpImg2 = new ImageIcon("assets/" + b2.getActionCommand() + ".png").getImage();
        tmpImg1 = tmpImg1 == null ? FadeInPanel.INITIAL_IMG : tmpImg1;

        //setImg1(tmpImg1);
        //setImg2(tmpImg2);

        repaint();
    }


    //endregion

}
