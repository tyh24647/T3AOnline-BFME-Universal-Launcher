import java.io.Console;

/**
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class T3ALauncherModel {
    private String applicationPath;
    private Game selectedGame;
    private Integer xRes;
    private Integer yRes;

    public static final Console CONSOLE = System.console();

    public T3ALauncherModel() {
        setSelectedGame(Game.BFME1);
        setApplicationPath(generateAppDirPath());
    }

    //region SETTERS
    public void setApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
    }

    public void setSelectedGame(Game selectedGame) {
        this.selectedGame = selectedGame;
    }
    //endregion

    //region GETTERS
    public String getApplicationPath() {
        return applicationPath;
    }

    public Game getSelectedGame() {
        return selectedGame;
    }
    //endregion

    public static String generateAppDirPath() {
        if (UserInfo.getOsType() == OS_Type.WINDOWS) {
            return UserInfo.getSelectedGame() == Game.BFME1 ? StringConstants.BFME1.WINDOWS_INI_PATH : StringConstants.BFME2.WINDOWS_INI_PATH;
        } else if (UserInfo.getOsType() == OS_Type.MAC) {
            return UserInfo.getSelectedGame() == Game.BFME2 ? StringConstants.BFME1.MAC_INI_PATH : StringConstants.BFME2.MAC_INI_PATH;
        }

        return null;
    }

    public Integer getyRes() {
        return yRes;
    }

    public Integer getxRes() {
        return xRes;
    }

    public void setxRes(Integer xRes) {
        this.xRes = xRes;
    }

    public void setyRes(Integer yRes) {
        this.yRes = yRes;
    }
}
