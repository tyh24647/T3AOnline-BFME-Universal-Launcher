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
    BFME2("assets/BFME2.png"),
    ROTWK("assets/ROTWK.png");

    private final String srcPath;
    //private Image image;

    ImageAsset(final String srcPath) {
        this.srcPath = srcPath;

        /*
        this.image = image;

        if (this.image == null) {
            try {
                this.image = ImageIO.read(getClass().getResource(srcPath));
            } catch (Exception e) {
                Log.e(e, e.getMessage());
                this.image = new ImageIcon(srcPath).getImage();
            }
        }
        */
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
