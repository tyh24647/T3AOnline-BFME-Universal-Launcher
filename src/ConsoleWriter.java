/*******************************************************************************
 *
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
 ******************************************************************************/

import java.util.Date;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 2/26/18
 */
public interface ConsoleWriter {
    String sessionId = String.valueOf(String.valueOf(new Date().getTime()).hashCode());

    //region DEBUG MESSAGE HANDLING
    void dbg(String message);
    void dbg(String title, String message);
    void dbg(Object sender, String message);
    void dbg(Object sender, String title, String message);
    void dbg(Class currentClass, String message);
    void dbg(Class currentClass, String messageTitle, String message);
    void dbg(Class currentClass, Object sender, String message);
    void dbg(Class currentClass, Object sender, String messageTitle, String message);
    //endregion

    //region ERROR MESSAGE HANDLING
    void err(String errorMessage);
    void err(String errorTitle, String errorMessage);
    void err(Object sender, String errorMessage);
    void err(Object sender, String errorTitle, String errorMessage);
    void err(Class currentClass, String errorMessage);
    void err(Class currentClass, String errorTitle, String errorMessage);
    void err(Class currentClass, Object sender, String errorMessage);
    void err(Class currentClass, Object sender, String errorTitle, String errorMessage);
    void err(Exception e);
    void err(Exception e, String errorMessage);
    void err(Exception e, String errorTitle, String errorMessage);
    void err(Exception e, Object sender, String errorMessage);
    void err(Exception e, Object sender, String errorTitle, String errorMessage);
    void err(Exception e, Class currentClass, String errorMessage);
    void err(Exception e, Class currentClass, String errorTitle, String errorMessage);
    void err(Exception e, Class currentClass, Object sender, String errorMessage);
    void err(Exception e, Class currentClass, Object sender, String errorTitle, String errorMessage);
    void err(Error e);
    void err(Error e, String errorMessage);
    void err(Error e, String errorTitle, String errorMessage);
    void err(Error e, Object sender, String errorMessage);
    void err(Error e, Object sender, String errorTitle, String errorMessage);
    void err(Error e, Class currentClass, String errorMessage);
    void err(Error e, Class currentClass, String errorTitle, String errorMessage);
    void err(Error e, Class currentClass, Object sender, String errorMessage);
    void err(Error e, Class currentClass, Object sender, String errorTitle, String errorMessage);
    //endregion

}
