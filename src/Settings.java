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
public class Settings implements ISharedApplicationObjects {

    protected ResObject resolution;
    protected OS_Type osType;
    protected UserInfo userInfo;
    protected SysInfo sysInfo;
    protected Log log;
    protected MainGUI ui;
    protected JComponent parentComponent;
    protected GameConstants gameConstants;

    protected boolean showAdvancedSettings;
    protected boolean isDebug;
    protected boolean isWidescreen;
    protected boolean bfme1Installed;
    protected boolean bfme2Installed;
    protected boolean rotwkInstalled;
    protected boolean useDefaults;
    protected boolean resolutionWasDetected;

    protected String bfme1Path;
    protected String bfme2Path;
    protected String rotwkPath;
    protected String resolutionStr;

    public Settings() {
        setUseDefaults(true);
    }

    public ResObject getResolution() {
        return resolution;
    }

    public void setResolution(ResObject resolution) {
        this.resolution = resolution;
    }

    public OS_Type getOsType() {
        return osType;
    }

    public void setOsType(OS_Type osType) {
        this.osType = osType;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SysInfo getSysInfo() {
        return sysInfo;
    }

    public void setSysInfo(SysInfo sysInfo) {
        this.sysInfo = sysInfo;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public MainGUI getUi() {
        return ui;
    }

    public void setUi(MainGUI ui) {
        this.ui = ui;
    }

    public JComponent getParentComponent() {
        return parentComponent;
    }

    public void setParentComponent(JComponent parentComponent) {
        this.parentComponent = parentComponent;
    }

    public GameConstants getGameConstants() {
        return gameConstants;
    }

    public void setGameConstants(GameConstants gameConstants) {
        this.gameConstants = gameConstants;
    }

    public boolean isShowAdvancedSettings() {
        return showAdvancedSettings;
    }

    public void setShowAdvancedSettings(boolean showAdvancedSettings) { this.showAdvancedSettings = showAdvancedSettings; }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public boolean isWidescreen() {
        return isWidescreen;
    }

    public void setWidescreen(boolean widescreen) {
        isWidescreen = widescreen;
    }

    public boolean isBfme1Installed() {
        return bfme1Installed;
    }

    public void setBfme1Installed(boolean bfme1Installed) {
        this.bfme1Installed = bfme1Installed;
    }

    public boolean isBfme2Installed() {
        return bfme2Installed;
    }

    public void setBfme2Installed(boolean bfme2Installed) {
        this.bfme2Installed = bfme2Installed;
    }

    public boolean isRotwkInstalled() {
        return rotwkInstalled;
    }

    public void setRotwkInstalled(boolean rotwkInstalled) {
        this.rotwkInstalled = rotwkInstalled;
    }

    public boolean isUseDefaults() {
        return useDefaults;
    }

    public void setUseDefaults(boolean useDefaults) {
        this.useDefaults = useDefaults;
    }

    public boolean isResolutionWasDetected() {
        return resolutionWasDetected;
    }

    public void setResolutionWasDetected(boolean resolutionWasDetected) { this.resolutionWasDetected = resolutionWasDetected; }

    public String getBfme1Path() {
        return bfme1Path;
    }

    public void setBfme1Path(String bfme1Path) {
        this.bfme1Path = bfme1Path;
    }

    public String getBfme2Path() {
        return bfme2Path;
    }

    public void setBfme2Path(String bfme2Path) {
        this.bfme2Path = bfme2Path;
    }

    public String getRotwkPath() {
        return rotwkPath;
    }

    public void setRotwkPath(String rotwkPath) {
        this.rotwkPath = rotwkPath;
    }

    public String getResolutionStr() {
        return resolutionStr;
    }

    public void setResolutionStr(String resolutionStr) {
        this.resolutionStr = resolutionStr;
    }
}
