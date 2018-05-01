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
 *
 * <p>
 *
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class UserInfo {
    private static String osStr = System.getProperty("os.name").toLowerCase();
    private static OS_Type osType;
    private static Game selectedGame;
    private static ResObject targetRes;
    private static String selectedCBoxItem;

    public UserInfo() {
        determineOSType();
    }

    private static void determineOSType() {
        if (osStr.contains("win")) {
            osType = OS_Type.WINDOWS;
        } else if (osStr.contains("mac")) {
            osType = OS_Type.MAC;
        } else if (osStr.contains("nix")) {
            osType = OS_Type.Linux;
        } else if (osStr.contains("nux")) {
            osType = OS_Type.LINUX;
        } else if (osStr.contains("sunos")) {
            osType = OS_Type.SOLARIS;
        } else {
            osType = OS_Type.UNKNOWN;
        }
    }

    @NotNull
    public String getOsStr() {
        return osStr;
    }

    @NotNull
    public static OS_Type getOsType() {
        determineOSType();
        return osType;
    }

    public static boolean isWindows() {
        return (osStr.contains("win"));
    }

    public static boolean isMac() {
        return (osStr.contains("mac"));
    }

    public static boolean isLinux() {
        return (osStr.contains("nix") || osStr.contains("nux"));
    }

    public static boolean isSolaris() {
        return (osStr.contains("sunos"));
    }

    public void setSelectedGame(@NotNull Game selectedGame) {
        this.selectedGame = selectedGame;
    }

    public static void setSelectedCBoxItem(String selectedCBoxItem) {
        UserInfo.selectedCBoxItem = selectedCBoxItem;
    }

    public static String getSelectedCBoxItem() {
        return selectedCBoxItem;
    }

    @NotNull
    public static Game getSelectedGame() {
        return selectedGame == null ? Game.BFME1 : selectedGame;
    }
}
