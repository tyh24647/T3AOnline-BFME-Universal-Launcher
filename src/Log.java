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

import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 2/26/18
 */
public final class Log implements ConsoleWriter {
    private static final String DEBUG_HEADER = "/> ";
    private static final String ERROR_HEADER = "ERROR/> ";
    private File logFile;

    public static void d() {
        System.out.println("> ");
    }

    public static void d(@Nullable String message) {
        message = message == null ? "" : message;

        String msg = DEBUG_HEADER.concat(message);
        System.out.println(msg);

        //TODO: Add write to log file
    }

    public static void d(Object...args) {
        StringBuilder sb = new StringBuilder();

        if (args.length == 0) {
            d();
        }

        for (Object obj : args) {
            if (obj instanceof String) {
                obj = obj.toString();
            } else if (obj.getClass().equals(Class.class)) {
                obj = obj.getClass().getName();
            }

            sb.append(String.valueOf(obj).concat(obj == null ? "" : " "));
        }

        d(sb.toString());
    }

    public static void e(String message) {
        String msg = ERROR_HEADER.concat(message);
        System.out.println(msg);

        //TODO: Add write to log file
    }

    public static void e(Object...args) {
        if (args.length == 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (Object obj : args) {
            if (obj instanceof Error) {
                obj = ((Error) obj).getCause().toString();
            } else if (obj instanceof Exception) {
                obj = ((Exception) obj).getCause().toString();
            } else if (obj instanceof String) {
                obj = obj.toString();
            } else if (obj instanceof Class) {
                obj = obj.getClass().getName();
            }

            sb.append(String.valueOf(obj).concat(obj == null ? "" : " "));
        }

        e(sb.toString());
    }

    @Override
    public void dbg(String message) {
        d(message);
    }

    @Override
    public void dbg(String title, String message) {
        d(title, message);
    }

    @Override
    public void dbg(Object sender, String message) {
        d(sender, message);
    }

    @Override
    public void dbg(Object sender, String title, String message) {
        d(sender, title, message);
    }

    @Override
    public void dbg(Class currentClass, String message) {
        d(currentClass, message);
    }

    @Override
    public void dbg(Class currentClass, String messageTitle, String message) {
        d(currentClass, messageTitle, message);
    }

    @Override
    public void dbg(Class currentClass, Object sender, String message) {
        d(currentClass, sender, message);
    }

    @Override
    public void dbg(Class currentClass, Object sender, String messageTitle, String message) {
        d(currentClass, sender, messageTitle, message);
    }

    @Override
    public void err(String errorMessage) {
        e(errorMessage);
    }

    @Override
    public void err(String errorTitle, String errorMessage) {
        e(errorTitle, errorMessage);
    }

    @Override
    public void err(Object sender, String errorMessage) {
        e(sender, errorMessage);
    }

    @Override
    public void err(Object sender, String errorTitle, String errorMessage) {
        e(sender, errorTitle, errorMessage);
    }

    @Override
    public void err(Class currentClass, String errorMessage) {
        e(currentClass, errorMessage);
    }

    @Override
    public void err(Class currentClass, String errorTitle, String errorMessage) {
        e(currentClass, errorTitle, errorMessage);
    }

    @Override
    public void err(Class currentClass, Object sender, String errorMessage) {
        e(currentClass, sender, errorMessage);
    }

    @Override
    public void err(Class currentClass, Object sender, String errorTitle, String errorMessage) {
        e(currentClass, sender, errorTitle, errorMessage);
    }

    @Override
    public void err(Exception e) {
        e(e, e.getMessage());
    }

    @Override
    public void err(Exception e, String errorMessage) {
        e(e, errorMessage);
    }

    @Override
    public void err(Exception e, String errorTitle, String errorMessage) {
        e(e, errorTitle, errorMessage);
    }

    @Override
    public void err(Exception e, Object sender, String errorMessage) {
        e(e, sender, errorMessage);
    }

    @Override
    public void err(Exception e, Object sender, String errorTitle, String errorMessage) {
        e(e, sender, errorTitle, errorMessage);
    }

    @Override
    public void err(Exception e, Class currentClass, String errorMessage) {
        e(e, currentClass, errorMessage);
    }

    @Override
    public void err(Exception e, Class currentClass, String errorTitle, String errorMessage) {
        e(e, currentClass, errorTitle, errorMessage);
    }

    @Override
    public void err(Exception e, Class currentClass, Object sender, String errorMessage) {
        e(e, currentClass, sender, errorMessage);
    }

    @Override
    public void err(Exception e, Class currentClass, Object sender, String errorTitle, String errorMessage) {
        e(e, currentClass, sender, errorTitle, errorMessage);
    }

    @Override
    public void err(Error e) {
        e(e, e.getMessage());
    }

    @Override
    public void err(Error e, String errorMessage) {
        e(e, errorMessage);
    }

    @Override
    public void err(Error e, String errorTitle, String errorMessage) {
        e(e, errorTitle, errorMessage);
    }

    @Override
    public void err(Error e, Object sender, String errorMessage) {
        e(e, sender, errorMessage);
    }

    @Override
    public void err(Error e, Object sender, String errorTitle, String errorMessage) {
        e(e, sender, errorTitle, errorMessage);
    }

    @Override
    public void err(Error e, Class currentClass, String errorMessage) {
        e(e, currentClass, errorMessage);
    }

    @Override
    public void err(Error e, Class currentClass, String errorTitle, String errorMessage) {
        e(e, currentClass, errorTitle, errorMessage);
    }

    @Override
    public void err(Error e, Class currentClass, Object sender, String errorMessage) {
        e(e, currentClass, sender, errorMessage);
    }

    @Override
    public void err(Error e, Class currentClass, Object sender, String errorTitle, String errorMessage) {
        e(e, currentClass, sender, errorTitle, errorMessage);
    }
}
