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
import org.softsmithy.lib.swing.JXScrollPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.Date;

/**
 * The T3AOnline universal cross-platform launcher application provides mods installer/manager, settings modifications,
 * multiplatform online games, game downloads (with current patches), and automatic updates and patching.
 * <p>
 * This project was created as a joint effort between myself and the admins at Revora/T3AOnline in effort to
 * create a smooth and cohesive way to install the correct versions of the games as well as have a shared version
 * that can be played on all platforms.
 *
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class T3ALauncher implements ISharedApplicationObjects {
    private static ViewController vc;
    private static T3ALauncherModel model;
    private static MainGUI ui;
    private static boolean isDebug = true, needsJavaUpdate = false;
    private static Thread javaVersionCheckTask;
    private static Thread graphicsThread;
    private static Runnable showUITask;
    private static DetailsView glassPane;
    private static JOptionPane javaUpdateAlert;
    private static JFrame javaInstallLoadingFrame;
    private static Process proc;
    private static JTextArea cmdOutput;

    /**
     * The application driver which runs the program.
     * @param args      The arguments taken in by the program upon runtime.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            graphicsThread.checkAccess();

            if (!isDebug) {
                javaInstallLoadingFrame = new JFrame("T3AOnline Helper");
                javaInstallLoadingFrame.setPreferredSize(new Dimension(300, 200));
                javaInstallLoadingFrame.setFocusable(false);

                JPanel instSpinnerPanel = new JPanel(new BorderLayout());
                instSpinnerPanel.setPreferredSize(new Dimension(200, 120));

                var t = new JLabel("Checking current Java version...");
                t.setForeground(Color.WHITE);
                instSpinnerPanel.add(t, BorderLayout.NORTH);
                instSpinnerPanel.setBackground(Color.BLACK);
                instSpinnerPanel.setForeground(Color.WHITE);

                var i = new JLabel();
                var a = new JLabel();
                var b = new JLabel();
                var c = new JLabel();
                var d = new JLabel("Configuring...");

                var lsURL = ClassLoader.getSystemClassLoader().getResource("assets/wheel.gif");
                javaInstallLoadingFrame.getRootPane().validate();
                javaInstallLoadingFrame.getRootPane().repaint();

                if (lsURL != null) {
                    var lsIcon = new ImageIcon(lsURL);
                    i.setIcon(new ImageIcon(lsIcon.getImage()));
                    i.setFocusable(false);
                    javaInstallLoadingFrame.validate();
                    javaInstallLoadingFrame.repaint();
                }

                a.setOpaque(false);
                b.setOpaque(false);
                c.setOpaque(false);
                d.setOpaque(false);
                i.setOpaque(false);

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/_fonts/RINGM___.TTF")));

                var titleFont = T3ALauncher.class.getResourceAsStream("src/assets/_fonts/RINGM___.TTF");
                d.setForeground(new Color(215, 186, 147));

                for (String fontName : ge.getAvailableFontFamilyNames()) {
                    if (fontName.toLowerCase().contains("ringm")) {
                        d.setFont(new Font(fontName, Font.PLAIN, 24));
                        break;
                    }
                }

                d.setBackground(Color.BLACK);
                d.setFont(Font.getFont("RINGM___"));

                var myStream = new BufferedInputStream(new FileInputStream("src/assets/_fonts/RINGM___.TTF"));
                var ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
                var newFont = ttfBase.deriveFont(Font.PLAIN, 28);

                d.setFont(newFont);
                a.setPreferredSize(new Dimension(80, 120));
                b.setPreferredSize(new Dimension(200, 30));
                c.setPreferredSize(new Dimension(80, 120));
                d.setPreferredSize(new Dimension(200, 75));

                i.setHorizontalAlignment(SwingConstants.CENTER);
                i.setVerticalAlignment(SwingConstants.CENTER);
                d.setHorizontalAlignment(SwingConstants.CENTER);

                instSpinnerPanel.add(a, BorderLayout.WEST);
                instSpinnerPanel.add(b, BorderLayout.SOUTH);
                instSpinnerPanel.add(c, BorderLayout.EAST);
                instSpinnerPanel.add(d, BorderLayout.NORTH);
                instSpinnerPanel.add(i, BorderLayout.CENTER);

                var cDialgFr = new JFrame() { @Override public boolean isResizable() { return false; }};
                var result = JOptionPane.showConfirmDialog(cDialgFr, "A Java update is required in order to launch this application.\n"
                                + "\n\t\t\t\t\t- Current Java Version: \"" + getJavaVersion() + "\""
                                + "\n\t\t\t\t\t- Required Java Version: " + "\"10.0\""
                                + "\n\nThe Battle for Middle-Earth Launcher will open automatically when\nthe update is finished."
                                + "\n\nClick \"OK\" to perform the installation, \"Cancel\" to exit.",
                        "The Lord of the Rings: The Battle for Middle-Earth Launcher", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    cDialgFr.dispose();

                    var bkdImgLabel = new JLabel(new ImageIcon(ImageIO.read(lsURL)));
                    javaInstallLoadingFrame.add(bkdImgLabel);
                    javaInstallLoadingFrame.add(instSpinnerPanel);
                    javaInstallLoadingFrame.setLocationByPlatform(true);
                    javaInstallLoadingFrame.validate();
                    javaInstallLoadingFrame.repaint();
                    javaInstallLoadingFrame.setVisible(true);
                    javaInstallLoadingFrame.pack();
                    javaInstallLoadingFrame.requestFocus();
                    javaInstallLoadingFrame.setAlwaysOnTop(true);

                    var javaChkTask = checkJavaInstallation();
                    var javaThread = new Thread(javaChkTask);
                    javaThread.start();
                }
            } else {
                Runnable initApp = T3ALauncher::initApplication;
                Thread initTask = new Thread(initApp);
                initTask.start();
            }
        } catch(Exception e){
            if (isDebug) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            } else {
                appendCmdOutput(e.getMessage(), false, Color.RED);
                e.printStackTrace();
            }
        }
    }

    /**  */
    private static void initApplication() {
        SwingUtilities.invokeLater(T3ALauncher::initObjs);
    }

    /**  */
    private static void initObjs() {
        model = new T3ALauncherModel();
        ui = new MainGUI();
        vc = new ViewController(isDebug, model, ui);
        vc.initUI(isDebug);
        initSharedApplicationObjs(model, ui, vc);
    }

    /**
     *
     * @param model
     * @param ui
     * @param vc
     */
    private static void initSharedApplicationObjs(T3ALauncherModel model, MainGUI ui, ViewController vc) {
        /* TODO */
    }

    /**
     *
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    private static Runnable checkJavaInstallation() throws InterruptedException, IOException {
        return () -> {
            Runnable javaVersionChk = T3ALauncher::performJavaUpdateBeforeLaunch;
            appendCmdOutput("T3ALauncher:~ Launching process [T3ALauncher:JavaVersionCheckTask] on a new thread", true);
            javaVersionCheckTask = new Thread(javaVersionChk);
            javaVersionCheckTask.start();
        };
    }

    /**  */
    private static void performJavaUpdateBeforeLaunch() {
        try {
            if (getJavaVersion() < 11 && needsJavaUpdate) {    // 10 ) {
                String cdCmd = null;
                String autoJavaInstallCmd = null;
                String deleteInstallerCmd = null;
                String downloadFolder = null;

                var javaUpdateDownloadCmd = "/bin/sh curl -j -k -L -H " +
                        "\"Cookie: oraclelicense=accept-securebackup-cookie" +
                        "\" -O http://download.oracle.com/otn-pub/java/jdk/10+46/76eac37278c24557a3c4199677f19b62/" +
                        "jre-10_osx-x64_bin.dmg > jre-10_osx-x64_bin.dmg";
                if (UserInfo.isMac()) {
                    downloadFolder = "~/Downloads/";
                    cdCmd = "/bin/sh -c " + downloadFolder;
                    javaUpdateDownloadCmd += "jre-10_osx-x64_bin.dmg > jre-10_osx-x64_bin.dmg;";
                    autoJavaInstallCmd = "hdiutil attach jre-10_osx-x64_bin.dmg; " +
                            "installer -pkg /Volumes/Java\\ 10/Java\\ 10.app/Contents/Resources/JavaAppletPlugin.pkg -target /;";
                    deleteInstallerCmd = "diskutil unmount /Volumes/Java\\ 10/; rm -rf jre-10_osx-x64_bin.dmg;";
                } else if (UserInfo.isWindows()) {
                    downloadFolder = "%USERPROFILE%\\Downloads";
                    cdCmd = "cd " + downloadFolder;
                    javaUpdateDownloadCmd += "jre-10_windows-x64_bin.exe > jre-10_windows-x64_bin.exe";
                    autoJavaInstallCmd = "start /WAIT %~dp0jre-10_windows-x64_bin.exe /s /w";
                    deleteInstallerCmd = "del /f \"jre-10_windows-x64_bin.exe\"";
                } else if (UserInfo.isSolaris()) {
                    downloadFolder = "$(xdg-user-dir DOWNLOAD)";
                    cdCmd = "cd " + downloadFolder;
                    javaUpdateDownloadCmd += "jre-10_solaris-sparcv9_bin.tar.gz > jre-10_solaris-sparcv9_bin.tar.gz";
                    autoJavaInstallCmd = "tar zxf jre-10_solaris-sparcv9_bin.tar.gz";
                    deleteInstallerCmd = "rm jre-10_solaris-sparcv9_bin.tar.gz";
                } else if (UserInfo.isLinux()) {
                    downloadFolder = "$(xdg-user-dir DOWNLOAD)";
                    cdCmd = "cd " + downloadFolder;
                    javaUpdateDownloadCmd += "jre-10_linux-x64_bin.tar.gz > jre-10_linux-x64_bin.tar.gz";
                    autoJavaInstallCmd = "tar zxf jre-10_linux-x64_bin.tar.gz";
                    deleteInstallerCmd = "rm jre-10_linux-x64_bin.tar.gz";
                }

                var isMac = UserInfo.isMac();
                var isWindows = UserInfo.isWindows();
                var isSolaris = UserInfo.isSolaris();
                var isLinux = UserInfo.isLinux();

                if (UserInfo.isMac()) {
                    File javaDmgInstaller = new File(T3ALauncher.class.getResource("assets/_JavaUpdater/jre-10_osx-x64_bin.dmg").getPath());
                    var poStr = javaDmgInstaller.getPath();
                    var fmtdDmgPath = javaDmgInstaller.getPath().replaceAll("%20", "\\ ");

                    fmtdDmgPath = fmtdDmgPath.replaceAll(" ", "\\ ");
                    File dmg = new File(fmtdDmgPath);
                    String dmgPath = downloadFolder + "jre-10_osx-x64_bin.dmg";
                    appendCmdOutput("T3ALauncher:~ Searching for disk image file named: " +
                            "\"jre-10_osx-x64_bin.dmg\" at path: \"" + fmtdDmgPath + "\"", true);

                    if (dmg.exists()) {
                        appendCmdOutput("T3ALauncher:~ " + "Attempting to mount disk image", true);
                        appendCmdOutput("T3ALauncher:~ open -j -g " + fmtdDmgPath + " &");
                        proc = Runtime.getRuntime().exec(new String[]{
                                "osascript", "-e", "do shell script \"hdiutil attach -debug -verbose '" + fmtdDmgPath + "'\""
                        });

                        appendCmdOutput("\nT3ALauncher:~ " + "osascript -e 'do shell script \"" +
                                "hdiutil attach -debug -verbose '" + fmtdDmgPath + "''");
                        appendCmdOutput("T3ALauncher:~ " + "Initializing disk...");
                        BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

                        String s;
                        JFrame tmp = new JFrame("T3ALauncher Console Output (Debug)") {
                            @Override public boolean isOpaque() { return false; }
                            @Override public void setVisible(boolean b) { if (b) { super.requestFocus(); } super.setVisible(b); }
                            @Override public String getTitle() { return "BFME T3AOnline Launcher - Debug Console"; }
                            @Override public void paint(Graphics g) {
                                super.paint(g);
                                Graphics2D g2d = (Graphics2D) g.create();
                                g2d.setComposite(AlphaComposite.SrcOver.derive(0.90f));
                                g2d.setColor(Color.BLACK);
                                g2d.fillRect(0, 0, getWidth(), getHeight());
                            }
                        };

                        tmp.setVisible(true);
                        tmp.setPreferredSize(new Dimension(600, 350));
                        tmp.setLocationRelativeTo(null);
                        tmp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        TDetailPanel mainPanel = new TDetailPanel(new BorderLayout());
                        mainPanel.setPreferredSize(new Dimension(tmp.getWidth(), tmp.getHeight()));

                        cmdOutput = new JTextArea() {
                            public void paintComponent(Graphics g) {
                                super.paintComponent(g);

                                var g2d = (Graphics2D) g;

                                g2d.setFont(new Font("Verdana", Font.ITALIC, 20));
                                g2d.setComposite(AlphaComposite.SrcOut.derive(0.1f));

                                var rect = getVisibleRect();
                                var tmp = new Rectangle(
                                        rect.x + 5,
                                        rect.y + 5,
                                        Double.valueOf(rect.getWidth() - 5).intValue(),
                                        Double.valueOf(rect.getHeight() - 5).intValue()
                                );

                                rect = tmp;

                                int x = rect.width + rect.x - 14 * getText().length();
                                int y = rect.y + 20;

                                g2d.drawString(getText(), x, y);
                                setEnabled(true);
                                setCaretPosition(getDocument().getLength());
                            }

                            @Override public synchronized void addFocusListener(FocusListener l) {
                                super.addFocusListener(new FocusListener() {
                                    @Override public void focusGained(FocusEvent e) { setEnabled(true); requestFocus(); }
                                    @Override public void focusLost(FocusEvent e) { setEnabled(true); requestFocus(); }
                                });
                            }

                            @Override public void requestFocus() {
                                super.requestFocus();
                                setEnabled(true);
                                setCaretPosition(getDocument().getLength());
                                repaint();
                            }

                            @Override public void scrollRectToVisible(Rectangle aRect) {
                                super.scrollRectToVisible(aRect);
                                setEnabled(true);
                                setCaretPosition(getDocument().getLength());
                                repaint();
                            }

                            @Override public boolean requestFocusInWindow() {
                                setEnabled(true);
                                setCaretPosition(getDocument().getLength());
                                repaint();
                                return true;
                            }

                            @Override protected void processFocusEvent(FocusEvent e) {
                                super.processFocusEvent(e);
                                setEnabled(true);
                                setCaretPosition(getDocument().getLength());
                                repaint();
                            }
                        };
                        cmdOutput.setBackground(new Color(0, 0, 0, 0.5f));
                        cmdOutput.setForeground(Color.WHITE);
                        cmdOutput.setUI(new BasicTextAreaUI() {
                            @Override protected void paintBackground(Graphics g) {
                                super.paintBackground(g);
                                var g2d = (Graphics2D) g;
                                Image img = null;

                                try {
                                    img = ImageIO.read(getClass().getResource("assets/dbgCmdWindowImg.jpg")).getScaledInstance(600, 350, Image.SCALE_AREA_AVERAGING);
                                } catch (Exception e) {
                                    appendCmdOutput("T3ALauncher:~ [JTextArea] ERROR: " + e.getMessage());
                                    e.printStackTrace();
                                }

                                var rect = cmdOutput.getVisibleRect();
                                var tmp = new Rectangle(
                                        rect.x + 5,
                                        rect.y + 5,
                                        Double.valueOf(rect.getWidth() - 5).intValue(),
                                        Double.valueOf(rect.getHeight() - 5).intValue()
                                );

                                rect = tmp;

                                int x = rect.width + rect.x - 14 * cmdOutput.getText().length();
                                int y = rect.y + 20;

                                g2d.setComposite(AlphaComposite.SrcOver.derive(0.4f));
                                g2d.drawImage(img, 0, 0, null);
                                g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
                                g2d.drawString(cmdOutput.getText(), x, y);
                            }
                        });

                        cmdOutput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                        cmdOutput.setOpaque(true);
                        cmdOutput.addFocusListener(null);
                        cmdOutput.setLineWrap(true);
                        cmdOutput.setAutoscrolls(true);
                        cmdOutput.setWrapStyleWord(true);
                        cmdOutput.setEditable(true);
                        cmdOutput.setMargin(new Insets(1, 5, 1, 5));

                        DefaultCaret caret = (DefaultCaret) cmdOutput.getCaret();
                        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                        caret.setBlinkRate(1);
                        cmdOutput.setCaret(caret);

                        JXScrollPane scrollPane = new JXScrollPane(cmdOutput) {
                            //
                        };
                        scrollPane.setPreferredSize(new Dimension(
                                Double.valueOf(cmdOutput.getWidth() + 5).intValue(),
                                Double.valueOf(cmdOutput.getHeight() + 5).intValue()
                        ));

                        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        scrollPane.setBorder(BorderFactory.createEmptyBorder());
                        scrollPane.setWheelScrollingEnabled(true);
                        scrollPane.setFocusTraversalKeysEnabled(true);
                        scrollPane.setBackground(new Color(0, 0, 0, 0.3f));
                        scrollPane.setForeground(new Color(0, 0, 0, 0f));
                        scrollPane.setViewportView(cmdOutput);

                        mainPanel.setOpaque(true);
                        TDetailPanel popup = new TDetailPanel();
                        popup.setPreferredSize(new Dimension(20, 20));

                        glassPane = new DetailsView(popup);
                        glassPane.setPreferredSize(new Dimension(tmp.getWidth(), tmp.getHeight()));
                        mainPanel.add(scrollPane, BorderLayout.CENTER);
                        tmp.add(mainPanel);
                        tmp.setContentPane(mainPanel);
                        tmp.validate();
                        tmp.setVisible(true);
                        tmp.pack();

                        glassPane.setVisible(true);
                        scrollPane.setViewportViewGlassPane(glassPane);
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        Thread.sleep(100L);

                        var diskImgMountSuccess = false;
                        while ((s = stdIn.readLine()) != null) {
                            diskImgMountSuccess = true;
                            appendCmdOutput("\nhdutil:~ " + s);

                            if (proc.exitValue() == 0) {
                                //appendCmdOutput("\nhdutil:~ Process exited with code 0");
                            }
                        }

                        while ((s = stdErr.readLine()) != null) {
                            appendCmdOutput("\nhdutil:~ ERROR: " + s);
                            if (proc.exitValue() == 0) {
                                proc.destroy();
                                appendCmdOutput("\nhdutil:~ Process exited with code 0");
                            }

                            cmdOutput.setForeground(Color.WHITE);
                        }

                        if (diskImgMountSuccess) {
                            appendCmdOutput("\nT3ALauncher:~ " + "Disk image mounted successfully!");
                            appendCmdOutput("\nT3ALauncher:~ " + "Running installer package", true);
                        } else {
                            appendCmdOutput("\nT3ALauncher:~ Disk image mount failed...");
                            appendCmdOutput("\nT3ALauncher:~ Attempting to run installer package", true);
                        }

                        proc.destroy();
                        appendCmdOutput("\nT3ALauncher:~ " + "osascript -e 'do shell script \"sudo " +
                                "installer -verbose -pkg '/Volumes/Java 10/Java 10.app/" +
                                "Contents/Resources/JavaAppletPlugin.pkg' -target /\" " +
                                "with administrator privileges'");
                        proc = Runtime.getRuntime().exec(new String[]{
                                "osascript", "-e", "do shell script \"sudo installer -verbose -pkg " +
                                "'/Volumes/Java 10/Java 10.app/Contents/Resources/" +
                                "JavaAppletPlugin.pkg' -target /; \" with prompt \"T3AOnline Launcher would like to make changes\" with administrator privileges"
                        });

                        appendCmdOutput("", true);
                        cmdOutput.requestFocus();

                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

                        s = null;
                        while ((s = stdInput.readLine()) != null) { Thread.sleep(100L); }
                        while ((s = stdError.readLine()) != null) {
                            if (s.toLowerCase().contains("0:159".toLowerCase())) {
                                appendCmdOutput("\nJavaAppletPluginInstaller:~ User canceled installation");
                                appendCmdOutput("\nJavaAppletPluginInstaller:~ Exiting Java v10.0 installer");
                                appendCmdOutput("\nT3ALauncher:~ Attempting to launch program", true);
                                appendCmdOutput("\nT3ALauncher:~ ERROR: Unable to run launcher");
                                appendCmdOutput("\nT3ALauncher:~ Terminating application", true);
                                appendCmdOutput("\nT3ALauncher:~ Process finished with exit code 0");
                                System.exit(0);
                            } else {
                                appendCmdOutput("\nJavaAppletPluginInstaller:~ " + s);
                            }
                        }

                        appendCmdOutput("\nT3ALauncher:~ Java update installed successfully!");
                        while (true) {
                            if (proc.exitValue() == 0) {
                                proc.destroy();
                                Thread.sleep(1200L);
                                tmp.dispose();
                                Thread.sleep(500L);
                                appendCmdOutput("\nT3ALauncher:~ Initializing application", true);
                                Runnable initApp = T3ALauncher::initApplication;
                                Thread initTask = new Thread(initApp);

                                if (javaInstallLoadingFrame != null && javaInstallLoadingFrame.isVisible() && proc.exitValue() == 0) {
                                    javaInstallLoadingFrame.dispose();
                                }

                                initTask.run();
                                appendCmdOutput("\nT3ALauncher:~ Launcher initialized successfully!");
                                break;
                            } else {

                            }

                            cmdOutput.requestFocus();
                        }

                        appendCmdOutput("\nT3ALauncher:~ " + "diskutil unmount /Volumes/Java 10/; wait;");
                        appendCmdOutput("\nT3ALauncher:~ " + "Unmounting disk", true);
                        proc = Runtime.getRuntime().exec("diskutil unmount /Volumes/Java 10/; wait;");
                        appendCmdOutput("\nT3ALauncher:~ " + "Disk image \"jre-10_osx-x64_bin.dmg\" unmounted successfully!");
                        appendCmdOutput("\nT3ALauncher:~ ");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Your version of Java is up to date!\nJava version: "
                                + System.getProperty("java.version"));
                needsJavaUpdate = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param msg
     */
    private static void appendCmdOutput(String msg) {
        try { appendCmdOutput(msg, false); }
        catch (Exception e) { e.printStackTrace(); }
    }

    /**
     *
     * @param msg
     * @param hasEllipse
     */
    private static void appendCmdOutput(String msg, boolean hasEllipse) {
        appendCmdOutput(msg, hasEllipse, Color.WHITE);
    }

    /**
     *
     * @param msg
     * @param hasEllipse
     * @param color
     */
    private static void appendCmdOutput(String msg, boolean hasEllipse, Color color) {
        try {
            cmdOutput.append(msg);
            cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
            cmdOutput.requestFocus();

            if (hasEllipse) {
                if (proc.exitValue() != 0) {
                    var startTime = new Date().getTime();
                    int secondsElapsed = 0;
                    while (proc.exitValue() != 0 && secondsElapsed < 15) {
                        secondsElapsed = (int)((new Date().getTime() - startTime) / 1000);
                        appendCmdOutput(".");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                        Thread.sleep(1000L);
                    }
                } else {
                    for (var i = 0; i < 5; i++) {
                        appendCmdOutput(".");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                        Thread.sleep(1000L);
                    }
                }
            }

            Thread.sleep(100L);
        } catch (Exception e) {
            if (isDebug) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param msg
     * @param c
     */
    private static void appendToPane(String msg, Color c) {
        try {
            cmdOutput.append(msg);
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
            aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
            cmdOutput.replaceSelection(msg);
        } catch (Exception e) {
            if (isDebug) { JOptionPane.showMessageDialog(null, e.getMessage()); }
            e.printStackTrace();
        }
    }

    private static boolean verifyJavaVersion () { return needsJavaUpdate = getJavaVersion() < Double.valueOf("11"); }

    /**
     *
     * @return
     */
    private static @NotNull Double getJavaVersion () {
        String version = System.getProperty("java.version");
        return Double.parseDouble(version);
    }
}

