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



import org.jetbrains.annotations.NotNull;

/**
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class ResObject {
    private String description;
    private Integer xVal;
    private Integer yVal;

    public ResObject() {
        setDescription(null);
        setxVal(null);
        setyVal(null);
    }

    public ResObject(String description) {
        setDescription(description);
        setxVal(null);
        setyVal(null);
    }

    public ResObject(Integer xVal, Integer yVal) {
        setDescription(null, xVal, yVal);
        setxVal(xVal);
        setyVal(yVal);
    }

    public ResObject(String description, Integer xVal, Integer yVal) {
        setDescription(description);
        setxVal(xVal);
        setyVal(yVal);
    }

    //region Setters
    public void setDescription(String description) {
        this.description = description == null ?
                StringConstants.GameSettings.Resolution.DEFAULT_INI_X_VAL
                        + "x" + StringConstants.GameSettings.Resolution.DEFAULT_INI_Y_VAL
                : description;
    }

    public void setDescription(String description, Integer xVal, Integer yVal) {
        this.description = description == null ? xVal + "x" + yVal : description;
    }

    public void setxVal(Integer xVal) {
        this.xVal = xVal == null ? Integer.parseInt(
                StringConstants.GameSettings.Resolution.DEFAULT_INI_X_VAL
        ) : xVal;
    }

    public void setyVal(Integer yVal) {
        this.yVal = yVal == null ? Integer.parseInt(
                StringConstants.GameSettings.Resolution.DEFAULT_INI_Y_VAL
        ) : yVal;
    }
    //endregion

    //region Getters

    /**
     *
     * @return
     */
    @NotNull
    public String getDescription() {
        return description == null ? xVal + "x" + yVal : this.description;
    }

    /**
     *
     * @return
     */
    @NotNull
    public Integer getxVal() {
        return xVal;
    }

    /**
     *
     * @return
     */
    @NotNull
    public Integer getyVal() {
        return yVal;
    }

    //endregion


    @Override
    public String toString() {
        return getDescription();
    }
}
