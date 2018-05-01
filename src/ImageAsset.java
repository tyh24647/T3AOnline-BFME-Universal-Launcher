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



import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 2/27/18
 */
public enum ImageAsset {
    BFME1("assets/BFME1.png"),
    BFME2("assets/BFME2.png"),// BFME2.png"),
    ROTWK("assets/ROTWK.png"),
    DIFF_CLOUDS("assets/differenceClouds.png"),
    SHADED_BLUR("assets/shaded_blur.png"),
    WHITE_BLANK("assets/WHITE_BLANK.png")
    ;

    private final String srcPath;

    ImageAsset(final String srcPath) {
        this.srcPath = srcPath;
    }

    public final String getSrcPath() {
        return this.srcPath;
    }

    public Image getImage() {
        Image tmp = null;

        try {
            tmp = ImageIO.read(getClass().getResource(this.srcPath));
        } catch (IOException e) {
            Log.e(e, e.getMessage());
            tmp = new ImageIcon(this.srcPath).getImage();
        }

        return tmp;
    }

    @Override
    public String toString() {
        return this.getSrcPath();
    }
}
