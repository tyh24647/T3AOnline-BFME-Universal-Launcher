import javax.swing.JComponent;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 4/28/18
 */
public class DefaultSettings extends Settings implements ISharedApplicationObjects {

    public DefaultSettings() {
        /* Do nothing! :) */
    }

    @Override
    public GameConstants getGameConstants() {
        return new GameConstants();
    }

    @Override
    public JComponent getParentComponent() {
        return super.getParentComponent() == null ? super.ui == null ? super.ui.getCenterContents() : null : null;
    }

    @Override
    public Log getLog() {
        return LOG;
    }

    @Override
    public MainGUI getUi() {
        return ui == null ? new MainGUI() : this.ui;
    }

    @Override
    public OS_Type getOsType() {
        return sysInfo == null ? super.osType == null ? null : null : OS_Type.WINDOWS;
    }

    @Override
    public ResObject getResolution() {
        return new ResObject(1280, 800);
    }

    @Override
    public String getBfme1Path() {
        return UserInfo.isMac() ? StringConstants.BFME1.MAC_EXE_PATH : UserInfo.isWindows() ? StringConstants.BFME1.WINDOWS_EXE_PATH : UserInfo.isLinux() ?
                " LINUX PATH !!! YAYAYA " : "Application path not specified";  // TODO add linux path to string constants after testing
    }

    @Override
    public String getBfme2Path() {
        return UserInfo.isMac() ? StringConstants.BFME2.MAC_APP_PATH : UserInfo.isWindows() ? StringConstants.BFME2.WINDOWS_EXE_PATH : UserInfo.isLinux() ?
                " LINUX PATH !!! YAYAYA " : "Application path not specified"; // TODO add linux path to string constants after testing
    }

    @Override
    public String getRotwkPath() {
        return UserInfo.isMac() ? StringConstants.ROTWK.MAC_APP_PATH : UserInfo.isWindows() ? StringConstants.ROTWK.WINDOWS_EXE_PATH : UserInfo.isLinux() ?
                StringConstants.ROTWK.LINUX_APP_PATH : "Application path not specified";
    }

    @Override
    public String getResolutionStr() {
        return "1280 x 800";
    }

    @Override
    public SysInfo getSysInfo() {
        return new SysInfo();
    }

    @Override
    public UserInfo getUserInfo() {
        return new UserInfo();
    }
}
