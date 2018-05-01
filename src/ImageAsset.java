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
    BFME2("assets/minas-morgul.png"),// BFME2.png"),
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
