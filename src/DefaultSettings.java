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



import javax.swing.JComponent;

/**
 *
 * <p>
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
