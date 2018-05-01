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



/**
 *
 * <p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 2/18/18
 */
public class IniWriter {
    private String appDirPath;
    private UserInfo userInfo;

    public IniWriter() {

    }

    public IniWriter(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public IniWriter(String appDirPath) {
        this.userInfo = userInfo == null ? new UserInfo() : this.userInfo;
        this.appDirPath = appDirPath == null ? UserInfo.isMac() ?
                StringConstants.BFME1.MAC_INI_PATH : StringConstants.BFME1.WINDOWS_INI_PATH : appDirPath;
    }

    public IniWriter(UserInfo userInfo, String appDirPath) {
        this.userInfo = userInfo;
        this.appDirPath = appDirPath;
    }

    public void setAppDirPath(String appDirPath) {
        this.appDirPath = appDirPath == null ? T3ALauncherModel.generateAppDirPath() : appDirPath;
    }
}
