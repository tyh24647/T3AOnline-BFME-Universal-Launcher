import com.sun.istack.internal.NotNull;

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
