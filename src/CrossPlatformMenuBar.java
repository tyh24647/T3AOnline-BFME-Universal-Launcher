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



import javax.swing.DefaultSingleSelectionModel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * <p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 4/30/18
 */
public class CrossPlatformMenuBar extends JMenuBar implements ISharedApplicationObjects {
    private JMenu[] menus = new JMenu[6];
    private JMenuItem[] menuItems = new JMenuItem[5];
    private String[] resolutions;

    /** Default constructor */
    public CrossPlatformMenuBar() {
        super();
        setFocusTraversalKeysEnabled(false);
        setSelectionModel(new DefaultSingleSelectionModel());
        updateUI();
        setupCustomizations();
    }

    /**
     *
     * @param menuBar
     */
    public CrossPlatformMenuBar(JMenuBar menuBar) {
        super();
        setFocusTraversalKeysEnabled(menuBar.getFocusTraversalKeysEnabled());
        setSelectionModel(menuBar.getSelectionModel());
        menuBar.updateUI();
        setupCustomizations();
    }

    /**  */
    private void setupCustomizations() {
        var ui = ViewController.CURRENT_UI;

        setMenus(new JMenu[] {
                ui.getFileMenu(), ui.getEditMenu(),
                ui.getViewMenu(), ui.getDevMenu(), getHelpMenu()
        });
    }

    /**
     *
     * @param menus
     */
    public void setMenus(JMenu[] menus) { this.menus = menus == null ? this.menus : menus; }

    /**
     *
     * @return
     */
    public JMenu[] getMenus() {
        return menus;
    }

    /**
     *
     * @param resolutions
     */
    public void setResolutions(String[] resolutions) {
        this.resolutions = resolutions == null ? this.resolutions == null ?
                GameConstants.DEFAULT_RESOLUTIONS : this.resolutions : resolutions;
    }

    /**
     *
     * @return
     */
    public String[] getResolutions() {
        return this.resolutions == null ? GameConstants.DEFAULT_RESOLUTIONS : resolutions;
    }

    /**
     *
     * @param menuItems
     */
    public void setMenuItems(JMenuItem[] menuItems) {
        this.menuItems = menuItems == null ? this.menuItems : menuItems;
    }

    /**
     *
      * @return
     */
    public JMenuItem[] getMenuItems() {
        return menuItems;
    }

    /**
     *
     * @return
     */
    @Override public String toString() { return super.toString(); }
}
