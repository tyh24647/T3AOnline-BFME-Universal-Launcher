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
 * @version 1.0.0
 * @since 2/18/18
 */
public class StringConstants {
    public static final String BUILD_NUMBER = ".*";
    public static final String VERSION = "v1.0.0";
    public static final String COPYRIGHT = "© Tyler Hostager, 2018. All Rights Reserved";

    public static class T3AOnline {
        public static String SERVER_JSON_DATA_URL = "https://info.server.cnc-online.net/";
    }

    public static class BFME1 {
        public static String MAC_EXE_PATH = "/Applications/Battle for Middle-Earth/Battle for Middle-Earth.app/Contents/Resources/drive_c/Program Files/EA GAMES/The Battle for Middle-earth (tm)/lotrbfme.exe";
        public static String MAC_INI_PATH = "/Applications/Battle for Middle-Earth/Battle for Middle-Earth.app/Contents/Resources/drive_c/users/Wineskin/Application Data/My Battle for Middle-earth Files/options.ini";
        public static String WINDOWS_EXE_PATH = "C:\\Program Files\\EA GAMES\\The Battle for Middle-earth (tm)\\lotrbfme.exe";
        public static String WINDOWS_INI_PATH = "C:\\Application Data\\My Battle for Middle-earth Files\\options.ini";
    }

    public static class BFME2 {
        public static String MAC_APP_PATH = "";
        public static String MAC_INI_PATH = "";
        public static String WINDOWS_APP_PATH = "/Applications/Battle for Middle-Earth/";
        public static String WINDOWS_EXE_PATH = "C:\\Program Files\\EA GAMES\\The Battle for Middle-earth (tm) II\\lotrbfme.exe";
        public static String WINDOWS_INI_PATH = "C:\\Application Data\\My The Lord of the Rings, The Rise of the Witch-king Files\\options.ini";
    }

    public static class ROTWK {
        public static String MAC_APP_PATH = "";//WARNING TODO Add this !!!!
        public static String WINDOWS_EXE_PATH = "";//WARNING TODO Add this !!!!
        public static String LINUX_APP_PATH = "";//WARNING TODO Add this !!!!
    }

    public static class Titles {
        public static final String EMPTY = "";
        public static final String GENERIC_PANEL = "Default Panel";
        public static final String MAIN_PANEL = "BFME Resolution Switcher";
        public static final String APPLY_BTN = "Apply";
        public static final String TEST_BTN = "Test Settings";
        public static final String SAVE_BTN = "Save Changes";
        public static final String DEFAULT_SETTINGS_BTN = "Revert to Default Settings";
        public static final String RESET_SETTINGS_BTN = "Reset";
        public static final String DETECT_RESOLUTION_BTN = "Detect Resolution...";
        public static final String SHOW_DEBUG_CONSOLE = "Show Debug Console";
        public static final String HIDE_DEBUG_CONSOLE = "Hide Debug Console";

    }

    public static class OperatingSystems {
        public static final String WINDOWS = "Windows";
        public static final String MACOS = "macOS";
        public static final String LINUX = "Linux";
        public static final String SOLARIS = "Solaris";
    }

    public static class System {

    }

    public static class GameSettings {

        public static class Resolution {
            public static final String INI_HEADER = "Resolution";
            public static final String INI_FULL_STR = "Resolution = 1024 768";
            public static final String INI_INCOMPLETE_STR = "Resolution = ";
            public static final String DEFAULT_AS_STRING = "1024x768";
            public static final String DEFAULT_INI_VALUE= "1024 768";
            public static final String DEFAULT_INI_X_VAL = "1024";
            public static final String DEFAULT_INI_Y_VAL = "768";

            public final String[] DEFAULT_STANDARD_RESOLUTIONS = {
                    "800x600", "1024x768", "1280x800", "1440x900", "1680x1050", "1920x1080"
            };


        }




    }

}
