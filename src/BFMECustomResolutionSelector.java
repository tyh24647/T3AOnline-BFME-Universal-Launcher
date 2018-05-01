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



import javax.swing.JComboBox;
import java.awt.Dimension;

/**
 *
 * </p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 4/30/18
 */
public class BFMECustomResolutionSelector extends JComboBox<String> implements ISharedApplicationObjects {

    private static final String[] DEFAULT_RESOLUTIONS = new String[] {
            "Select...", "800x600", "1280x800", "1440x900", "1680x1050",
            "1920x1080", "1920 x 1200", "2880 x 1800"
    };

    private String[] resOptions;

    /**
     * Default constructor
     */
    public BFMECustomResolutionSelector() {
        initWithDefaults();
    }

    /**
     * Create resolution picker object using custom values passed to the
     * object constructor when instantiated.
     *
     * @param resOptions    The custom resolutions to be listed for user-selection.
     */
    public BFMECustomResolutionSelector(String[] resOptions) {
        setResOptions(resOptions);
    }

    /**
     *
     */
    private void initWithDefaults() {
        setResOptions(DEFAULT_RESOLUTIONS);
    }

    @Override
    public boolean isOpaque() {
        return super.isOpaque();
    }

    /**
     *
     * @param isOpaque
     */
    @Override
    public void setOpaque(boolean isOpaque) {
        super.setOpaque(isOpaque);
    }

    /**
     *
     * @param d
     */
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
    }

    /**
     *
     * @return
     */
    @Override
    public Dimension getSize() {
        return super.getSize();
    }

    /**
     *
     * @param rv
     * @return
     */
    @Override
    public Dimension getSize(Dimension rv) {
        return super.getSize(rv);
    }

    /**
     *
     */
    @Override
    protected void selectedItemChanged() {
        if (getSelectedIndex() > 0) {
            super.selectedItemChanged();
        }

        ViewController.CURRENT_VC.getView().getLayeredPane().repaint();
    }

    /**
     *
     */
    @Override
    protected void fireActionEvent() {
        super.fireActionEvent();
        ViewController.CURRENT_VC.getView().getLayeredPane().repaint();
    }

    /**
     *
     */
    @Override
    public void firePopupMenuCanceled() {
        super.firePopupMenuCanceled();
        ViewController.CURRENT_VC.getView().getLayeredPane().repaint();
    }

    /*
    resChooser = new JComboBox<>(resOptions) {
            @Override
            public boolean isOpaque() {
                return false;
            }

            @Override
            protected void selectedItemChanged() {
                if (getSelectedIndex() > 0) {
                    super.selectedItemChanged();
                }

                layeredPane.repaint();
            }

            @Override
            protected void fireActionEvent() {
                super.fireActionEvent();
                layeredPane.repaint();
            }

            @Override
            public void firePopupMenuCanceled() {
                super.firePopupMenuCanceled();
                layeredPane.repaint();
            }
        };

        resChooser.setOpaque(true);
        resChooser.setSize(new Dimension(400, (int) super.getPreferredSize().getHeight() + 4));
     */

    /**
     *
     * @param resOptions
     */
    private void setResOptions(String[] resOptions) {
        this.resOptions = resOptions == null ? this.resOptions == null ? DEFAULT_RESOLUTIONS : this.resOptions : resOptions;
    }

    /**
     *
     * @return
     */
    public String[] getResOptions() {
        return resOptions == null ? DEFAULT_RESOLUTIONS : this.resOptions;
    }
}
