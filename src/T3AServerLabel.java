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



import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * <p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 4/30/18
 */
public class T3AServerLabel extends JLabel implements ISharedApplicationObjects {
    public static final String DEFAULT_TITLE = "PLAYERS: ";
    public static final Color DEFAULT_FOREGROUND_COLOR = new Color(215, 186, 147);
    public static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    public static final Font DEFAULT_FONT = Font.getFont("Arial").deriveFont(Font.PLAIN, 13);

    private String title;
    private Color fgdColor, bkgdColor;
    private Font font;


    //region - CONSTRUCTORS

    /** Default constructor*/
    public T3AServerLabel() {
        setTitle(DEFAULT_TITLE);
        setFgdColor(DEFAULT_FOREGROUND_COLOR);
        setBkgdColor(DEFAULT_BACKGROUND_COLOR);
        setFont(DEFAULT_FONT);
    }

    /**
     *
     * @param title
     */
    public T3AServerLabel(String title) {
        setTitle(title);
        setFgdColor(DEFAULT_FOREGROUND_COLOR);
        setBkgdColor(DEFAULT_BACKGROUND_COLOR);
        setFont(DEFAULT_FONT);
    }

    /**
     *
     * @param title
     * @param fgdColor
     * @param bkgdColor
     */
    public T3AServerLabel(String title, Color fgdColor, Color bkgdColor) {
        setTitle(title);
        setFgdColor(fgdColor);
        setBkgdColor(bkgdColor);
        setFont(DEFAULT_FONT);
    }

    /**
     *
     * @param font
     */
    public T3AServerLabel(Font font) {
        setTitle(DEFAULT_TITLE);
        setFgdColor(DEFAULT_FOREGROUND_COLOR);
        setBkgdColor(DEFAULT_BACKGROUND_COLOR);
        setFont(font);
    }

    /**
     *
     * @param title
     * @param font
     */
    public T3AServerLabel(String title, Font font) {
        setTitle(title);
        setFgdColor(DEFAULT_FOREGROUND_COLOR);
        setBkgdColor(DEFAULT_BACKGROUND_COLOR);
        setFont(font);
    }

    /**
     *
     * @param title
     * @param font
     * @param fgdColor
     * @param bkgdColor
     */
    public T3AServerLabel(String title, Font font, Color fgdColor, Color bkgdColor) {
        setTitle(title);
        setFont(font);
        setFgdColor(fgdColor);
        setBkgdColor(bkgdColor);
    }
    //endregion


    //region - INHERITED METHODS
    /**
     *
     * @return
     */
    @Override
    public Color getBackground() {
        return Color.BLACK;
    }

    /**
     *
     * @return
     */
    @Override
    public Color getForeground() {
        return new Color(215 ,186, 147);
    }

    /**
     *
     * @return
     */
    @Override
    public Font getFont() {
        return font;
    }
    //endregion


    //region - setters and getters
    public String getTitle() {
        return title == null ? DEFAULT_TITLE : this.title;
    }

    public void setTitle(String title) {
        this.title = title == null ? this.title == null ? DEFAULT_TITLE : this.title : title;
    }

    public Color getFgdColor() {
        return fgdColor == null ? DEFAULT_FOREGROUND_COLOR : this.fgdColor;
    }

    public void setFgdColor(Color fgdColor) {
        this.fgdColor = fgdColor ==  null ? this.fgdColor == null ?
                DEFAULT_FOREGROUND_COLOR : this.fgdColor : fgdColor;
    }

    public Color getBkgdColor() {
        return bkgdColor == null ? DEFAULT_BACKGROUND_COLOR : this.bkgdColor;
    }

    public void setBkgdColor(Color bkgdColor) {
        this.bkgdColor = bkgdColor == null ? this.bkgdColor : bkgdColor;
    }

    public Font getCustomFont() {
        return font == null ? DEFAULT_FONT : this.font;
    }

    public void setCustomFont(Font font) {
        this.font = font == null ? this.font == null ? DEFAULT_FONT : this.font : font;
    }
    //endregion
}
