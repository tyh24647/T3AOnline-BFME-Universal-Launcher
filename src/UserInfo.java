import com.sun.istack.internal.NotNull;

/**
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class UserInfo {
    private static String osStr = System.getProperty("os.name").toLowerCase();
    private static OS_Type osType;
    private static Game selectedGame;
    private static ResObject targetRes;

    public UserInfo() {
        determineOSType();
    }

    private void determineOSType() {
        if (osStr.contains("win")) {
            osType = OS_Type.WINDOWS;
        } else if (osStr.contains("mac")) {
            osType = OS_Type.MAC;
        } else if (osStr.contains("nix")) {
            osType = OS_Type.UNIX;
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
        return osType;
    }

    public static boolean isWindows() {
        return (osStr.contains("win"));
    }

    public static boolean isMac() {
        return (osStr.contains("mac"));
    }

    public static boolean isUnix() {
        return (osStr.contains("nix") || osStr.contains("nux"));
    }

    public static boolean isSolaris() {
        return (osStr.contains("sunos"));
    }

    public void setSelectedGame(@NotNull Game selectedGame) {
        this.selectedGame = selectedGame;
    }

    @NotNull
    public static Game getSelectedGame() {
        return selectedGame == null ? Game.BFME1 : selectedGame;
    }
}
