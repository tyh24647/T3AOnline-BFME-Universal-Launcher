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
