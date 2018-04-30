
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
 *
 * This project was created as a joint effort between myself and the admins at Revora/T3AOnline in effort to
 * create a smooth and cohesive way to install the correct versions of the games as well as have a shared version
 * that can be played on all platforms.
 *
 *
 *
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class T3ALauncher implements ISharedApplicationObjects {
    private static ViewController vc;
    private static T3ALauncherModel model;
    private static MainGUI ui;
    private static boolean isDebug = true, needsJavaUpdate = true;
    private static Thread javaVersionCheckTask;
    private static Thread graphicsThread;
    private static Runnable showUITask;
    private static DetailsView glassPane;
    private static JOptionPane javaUpdateAlert;
    private static JFrame javaInstallLoadingFrame;
    private static Process proc;
    private static JTextArea cmdOutput;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            if (!isDebug) {

                javaInstallLoadingFrame = new JFrame("T3AOnline Helper");
                //javaInstallLoadingFrame.setPreferredSize(new Dimension(200, 120));
                javaInstallLoadingFrame.setPreferredSize(new Dimension(300, 200));
                javaInstallLoadingFrame.setFocusable(false);

                JPanel instSpinnerPanel = new JPanel(new BorderLayout());
                instSpinnerPanel.setPreferredSize(new Dimension(200, 120));
                var t = new JLabel("Checking current Java version...");
                t.setForeground(Color.WHITE);
                //instSpinnerPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("assets/revoraCustomLogo.png"))));
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
                //var lsURL = ClassLoader.getSystemClassLoader().getResource("assets/LoadingSpinner.gif");
                if (lsURL != null) {
                    var lsIcon = new ImageIcon(lsURL);
                    i.setIcon(new ImageIcon(lsIcon.getImage()));
                    //i.setIcon(new ImageIcon(lsIcon.getImage().getScaledInstance(90, 220, Image.SCALE_SMOOTH)));
                    i.setFocusable(false);
                    javaInstallLoadingFrame.validate();
                    javaInstallLoadingFrame.repaint();
                    //i.setIcon(lsIcon);
                }


                a.setOpaque(false);
                b.setOpaque(false);
                c.setOpaque(false);
                d.setOpaque(false);
                i.setOpaque(false);

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/_fonts/RINGM___.TTF")));

                var titleFont = T3ALauncher.class.getResourceAsStream("src/assets/_fonts/RINGM___.TTF");

                //d.setForeground(new Color(200, 175, 120));
                //d.setForeground(new Color(250, 225, 200));
                d.setForeground(new Color(215, 186, 147));
                //d.setFont(new Font("RINGM___", Font.PLAIN, 28));

                for (String fontName : ge.getAvailableFontFamilyNames()) {
                    if (fontName.toLowerCase().contains("ringm")) {
                        d.setFont(new Font(fontName, Font.PLAIN, 24));
                        break;
                    }
                }

                d.setBackground(Color.BLACK);
                d.setFont(Font.getFont("RINGM___"));

                var myStream = new BufferedInputStream(
                        new FileInputStream("src/assets/_fonts/RINGM___.TTF"));
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

                var cDialgFr = new JFrame();
                cDialgFr.setResizable(false);
                var result = JOptionPane.showConfirmDialog(cDialgFr, "A Java update is required in order to launch this application.\n"
                                + "\n\t\t\t\t\t- Current Java Version: \"" + getJavaVersion() + "\""
                                + "\n\t\t\t\t\t- Required Java Version: " + "\"10.0\""
                                + "\n\nThe Battle for Middle-Earth Launcher will open automatically when\nthe update is finished."
                                + "\n\nClick \"OK\" to perform the installation, \"Cancel\" to exit.",
                        "The Lord of the Rings: The Battle for Middle-Earth Launcher", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//                    JOptionPane.showMessageDialog(null, "A Java update is required in order to launch this application.\n"
//                            + "\nApplication requirement: " + "\"10\"" + "\nInstalled version: \"" + getJavaVersion()
//                            + "\"\n\nPerforming Java update...");

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
//                cmdOutput.append(e.getMessage());
                    appendCmdOutput(e.getMessage(), false, Color.RED);
                    e.printStackTrace();
                }
            }
    }

    private static void initApplication() {
        SwingUtilities.invokeLater(T3ALauncher::initObjs);
    }

    private static void initObjs() {
        model = new T3ALauncherModel();
        ui = new MainGUI();
        vc = new ViewController(isDebug, model, ui);
        vc.initUI(isDebug);
    }

    private static Runnable checkJavaInstallation() throws InterruptedException, IOException {
        return () -> {
            Runnable javaVersionChk = T3ALauncher::performJavaUpdateBeforeLaunch;
            appendCmdOutput("T3ALauncher:~ Launching process [T3ALauncher:JavaVersionCheckTask] on a new thread", true);
            javaVersionCheckTask = new Thread(javaVersionChk);
            javaVersionCheckTask.start();
        };
    }

    private static void performJavaUpdateBeforeLaunch() {

            try {
                if (
                        getJavaVersion()
                        //< 10 /*Integer.valueOf("9.0")*/) {//.substring(0, 4).replaceAll(".", ""))) {
                                            < 11
                                            //< 10
                        ) {







                    //Thread.sleep(300l);
                    String cdCmd = null;
                    var javaUpdateDownloadCmd = "/bin/sh curl -j -k -L -H " +
                            "\"Cookie: oraclelicense=accept-securebackup-cookie" +
                            "\" -O http://download.oracle.com/otn-pub/java/jdk/10+46/76eac37278c24557a3c4199677f19b62/" +
                            "jre-10_osx-x64_bin.dmg > jre-10_osx-x64_bin.dmg";
                    String autoJavaInstallCmd = null;
                    String deleteInstallerCmd = null;
                    String downloadFolder = null;

                    ///*
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

                    /*
                    try {
                        if (UserInfo.isMac()) {
                            //  Launching via command-line
                            Process p = Runtime.getRuntime().exec(new String[]{
                                    "/bin/sh", "-c", "cd ~/Downloads/ &&" +

                                    //"curl -C - -j -L -H \"Cookie: oraclelicense=accept-securebackup-cookie\" -k http://download.oracle.com/otn-pub/java/jdk/9.0.4+11/c2514751926b4512b076cc82f959763f/jre-9.0.4_osx-x64_bin.dmg > jre-9.0.4_osx-x64_bin.dmg; " +
//                                    "curl -C - -LR#OH \"Cookie: oraclelicense=accept-securebackup-cookie\" " +
//                                              //"http://download.oracle.com/otn-pub/java/jdk/9.0.4+11/c2514751926b4512b076cc82f959763f/jre-9.0.4_osx-x64_bin.dmg > jre-9.0.4_osx-x64_bin.dmg; " +
//                                    "http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html" +

                                    //"curl -C - -LR#OH \"Cookie: oraclelicense=accept-securebackup-cookie\"" +
                                    "curl -L -C - -b \"oraclelicense=accept-securebackup-cookie\" -O " +
                                    "http://download.oracle.com/otn-pub/java/jdk/10+46/76eac37278c24557a3c4199677f19b62/jre-10_osx-x64_bin.dmg > jre-10_osx-x64_bin.dmg";
                                    //"http://download.oracle.com/otn-pub/java/jdk/10+46/76eac37278c24557a3c4199677f19b62/jre-10_osx-x64_bin.dmg > jre-10_osx-x64_bin.dmg";

                                    "wait; " +
                                    "open jre-10_osx-x64_bin.dmg && " +
                                    "sudo installer -pkg /Volumes/Java\\ 10/Java\\ 10.app/Contents/Resources/JavaAppletPlugin.pkg -target /; " +
                                    "wait; " +
                                    "diskutil unmount /Volumes/Java\\ 10/ && " +
                                    "rm -rf jre-10_osx-x64_bin.dmg && " +
                                    "wait; "
                            });
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                        e.printStackTrace();
                    }
                    */


                    if (UserInfo.isMac()) {
                        File javaDmgInstaller = new File(T3ALauncher.class.getResource("assets/_JavaUpdater/jre-10_osx-x64_bin.dmg").getPath());
                        var poStr = javaDmgInstaller.getPath().toString();
                        var fmtdDmgPath = javaDmgInstaller.getPath().replaceAll("%20", "\\ ");
                        fmtdDmgPath = fmtdDmgPath.replaceAll(" ", "\\ ");
                        File dmg = new File(fmtdDmgPath);

                        String dmgPath = downloadFolder + "jre-10_osx-x64_bin.dmg";
                        //fmtdDmgPath = dmgPath.replaceAll("%20", "\\ ");

                        appendCmdOutput("T3ALauncher:~ Searching for disk image file named: " +
                                "\"jre-10_osx-x64_bin.dmg\" at path: \"" + fmtdDmgPath + "\"", true);

                        if (dmg.exists()) {


                            //Desktop.getDesktop().open(dmg);
                            //Runtime.getRuntime().exec(new String[]{"open", "-j", "-g", dmgPath});
                            appendCmdOutput("T3ALauncher:~ " + "Attempting to mount disk image", true);
                            appendCmdOutput("T3ALauncher:~ open -j -g " + fmtdDmgPath + " &");
                            proc = Runtime.getRuntime().exec(new String[]{
                                    "osascript", "-e", "do shell script \"hdiutil attach -debug -verbose '" + fmtdDmgPath + "'\""
                            });

                            //proc = Runtime.getRuntime().exec(new String[]{"open", "-j", "-g", dmg.getAbsolutePath() + "/assets/_javaUpdater/jre-10_osx-x64_bin.dmg", "&" });
                            appendCmdOutput("\nT3ALauncher:~ " + "osascript -e 'do shell script \"" +
                                    "hdiutil attach -debug -verbose '" + fmtdDmgPath + "''");

                            /*
                            proc = Runtime.getRuntime().exec(new String[]{
                                    "osascript", "-e", "do shell script \"hdiutil attach -debug -verbose '" + fmtdDmgPath + "'\""
                            });
                            */

                            appendCmdOutput("T3ALauncher:~ " + "Initializing disk...");

                            BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                            BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));


                            // read the output from the command
                            String s = null;
                            JFrame tmp = new JFrame("T3ALauncher Console Output (Debug)") {
                                @Override
                                public boolean isOpaque() {
                                    return false;
                                }

                                @Override
                                public void paint(Graphics g) {
                                    super.paint(g);
                                    Graphics2D g2d = (Graphics2D) g.create();
                                    //g2d.setComposite(AlphaComposite.SrcOver.derive(0.85f));
                                    //g2d.setColor(new Color(1, 1, 1, 1));
                                    //g2d.setComposite(AlphaComposite.SrcIn.derive(0.01f));
                                    g2d.setComposite(AlphaComposite.SrcOver.derive(0.90f));
                                    //g2d.setComposite(AlphaComposite.SrcOver.derive(0f));
                                    //g2d.setColor(Color.WHITE);
                                    g2d.setColor(Color.BLACK);

                                    g2d.fillRect(0, 0, getWidth(), getHeight());
                                }

                                @Override
                                public void setVisible(boolean b) {
                                    if (b) {
                                        super.requestFocus();
                                    }

                                    super.setVisible(b);
                                }

                                @Override
                                public String getTitle() {
                                    return "BFME T3AOnline Launcher - Debug Console";
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

                                /*
                            public void fadeIn() {
                                createBlur();

                                setVisible(true);
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        Animator animator = PropertySetter.createAnimator(
                                                400, this, "alpha", 1.0f);
                                        animator.setAcceleration(0.2f);
                                        animator.setDeceleration(0.3f);
                                        animator.addTarget(
                                                new PropertySetter(this, "alpha", 1.0f));
                                        animator.start();
                                    }
                                });
                            }
                            */

                                @Override
                                public synchronized void addFocusListener(FocusListener l) {
                                    super.addFocusListener(new FocusListener() {
                                        @Override
                                        public void focusGained(FocusEvent e) {
                                            setEnabled(true);
                                            requestFocus();
                                        }

                                        @Override
                                        public void focusLost(FocusEvent e) {
                                            setEnabled(true);
                                            requestFocus();
                                        }
                                    });
                                }

                                @Override
                                public void requestFocus() {
                                    super.requestFocus();
                                    setEnabled(true);
                                    setCaretPosition(getDocument().getLength());
                                    repaint();
                                }

                                @Override
                                public void scrollRectToVisible(Rectangle aRect) {
                                    super.scrollRectToVisible(aRect);
                                    setEnabled(true);
                                    setCaretPosition(getDocument().getLength());
                                    repaint();
                                }

                                @Override
                                public boolean requestFocusInWindow() {
                                    setEnabled(true);
                                    setCaretPosition(getDocument().getLength());
                                    repaint();
                                    return true;
                                }

                                @Override
                                protected void processFocusEvent(FocusEvent e) {
                                    super.processFocusEvent(e);
                                    setEnabled(true);
                                    setCaretPosition(getDocument().getLength());
                                    repaint();
                                }
                            };

                            cmdOutput.setBackground(new Color(0, 0, 0, 0.5f));
                            cmdOutput.setForeground(Color.WHITE);
                            cmdOutput.setUI(new BasicTextAreaUI() {

                                @Override
                                protected void paintBackground(Graphics g) {
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
                            /*
                            private BufferedImage blurBuffer;
                            private BufferedImage backBuffer = ImageIO.read(getClass().getResource("assets/BFME1.png"));
                            private float alpha = 0.8f;
                            private final JXScrollPane SCROLL_PANE = this;
                            private TDetailPanel detailPanel = new TDetailPanel();
                            private DetailsView detailsView = new DetailsView(detailPanel);

                            @Override
                            protected void paintComponent(Graphics g) {
                                if (isVisible() && blurBuffer != null) {
                                    Graphics2D g2 = (Graphics2D) g.create();

                                    g2.setRenderingHint(
                                            RenderingHints.KEY_INTERPOLATION,
                                            RenderingHints.VALUE_INTERPOLATION_BILINEAR
                                    );

                                    g2.drawImage(backBuffer, 0, 0, null);

                                    alpha = 0.8f;
                                    g2.setComposite(AlphaComposite.SrcAtop.derive(alpha));
                                    g2.drawImage(backBuffer, 0, 0, 600, 400, null);
                                    g2.dispose();
                                    //return;
                                }

                                super.paintComponent(g);
                            }

                            @Override
                            public void setEnabled(boolean enabled) {

                                this.detailPanel.setAlpha(0.7f);
                                add(detailPanel, new GridBagConstraints());
                                super.setEnabled(true);
                            }

                            public void fadeIn() {
                                createBlur();

                                setVisible(true);
                                SwingUtilities.invokeLater(() -> {

                                    Animator animator = PropertySetter.createAnimator(
                                            400, detailPanel, "alpha", 1.0f);
                                    animator.setAcceleration(0.2f);
                                    animator.setDeceleration(0.3f);
                                    animator.addTarget(
                                            new PropertySetter(SCROLL_PANE, "alpha", 1.0f));
                                    animator.start();
                                });
                            }

                            private BufferedImage createBlur() {
                                JRootPane root = SwingUtilities.getRootPane(this);
                                blurBuffer = GraphicsUtilities.createCompatibleImage(800, 600);
                                Graphics2D g2 = blurBuffer.createGraphics();
                                root.paint(g2);
                                g2.dispose();

                                backBuffer = blurBuffer;
                                blurBuffer = GraphicsUtilities.createThumbnail(blurBuffer, 600);
                                blurBuffer = new GaussianBlurFilter(20).filter(blurBuffer, null);
                                return blurBuffer;
                            }

                            @Override
                            public void addAncestorListener(AncestorListener listener) {
                                //fadeIn();
                                backBuffer = createBlur();
                                super.addAncestorListener(listener);
                            }
                            */
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
                            //scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0f));
                            scrollPane.setBackground(new Color(0, 0, 0, 0.3f));
                            scrollPane.setForeground(new Color(0, 0, 0, 0f));
                            //scrollPane.setForeground(Color.GREEN);

                            scrollPane.setViewportView(cmdOutput);
                            mainPanel.setOpaque(true);
                            //mainPanel.setOpaque(false);


                            TDetailPanel popup = new TDetailPanel();
                            popup.setPreferredSize(new Dimension(20, 20));

                            //scrollPane.add(popup);
                            glassPane = new DetailsView(popup);

                            //glassPane = new DetailsView(popup);

                            //scrollPane.setViewportViewGlassPane(glassPane);
                            //scrollPane.setViewportViewGlassed(true);
                            //glassPane.setRoot(tmp.getRootPane());
                            //glassPane.setSize(20, 20);
                            glassPane.setPreferredSize(new Dimension(tmp.getWidth(), tmp.getHeight()));
                            //scrollPane.setViewportViewGlassPane(glassPane);
                            //tmp.getRootPane().setGlassPane(glassPane);

                            mainPanel.add(scrollPane, BorderLayout.CENTER);

                            tmp.add(mainPanel);
                            tmp.setContentPane(mainPanel);
                            tmp.validate();
                            tmp.setVisible(true);
                            tmp.pack();


                            glassPane.setVisible(true);
                            scrollPane.setViewportViewGlassPane(glassPane);
                            //tmp.setComponentZOrder(glassPane, 0);
                            //tmp.setComponentZOrder(glassPane, 0);
                            //tmp.setGlassPane(glassPane);
                            //tmp.setComponentZOrder(glassPane, 0);

                            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                            //SwingUtilities.updateComponentTreeUI(tmp);

                            Thread.sleep(100L);

                            var diskImgMountSuccess = false;
                            while ((s = stdIn.readLine()) != null) {
                                diskImgMountSuccess = true;
                                appendCmdOutput("\nhdutil:~ " + s);

                                if (proc.exitValue() == 0) {
                                    //appendCmdOutput("\nhdutil:~ Process exited with code 0");
                                }
                            }

                            // read any errors from the attempted command
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

                            // read the output from the command
                            s = null;


                            while ((s = stdInput.readLine()) != null) {
                                Thread.sleep(100L);
                            }

                            // read any errors from the attempted command
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
                                //int exitVal = proc.waitFor();

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
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                e.printStackTrace();
            }
    }

    private static void appendCmdOutput(String msg) {
        try {
            appendCmdOutput(msg, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void appendCmdOutput(String msg, boolean hasEllipse) {
        appendCmdOutput(msg, hasEllipse, Color.WHITE);
    }

    private static void appendCmdOutput(String msg, boolean hasEllipse, Color color) {
        try {
            //appendToPane(msg, color);
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

    /*
    public static void executeMacTerminalCmd (String...cmd){
        if (cmd == null) {
            return;
        }

        try {
            String[] tmp1 = new String[]{"/bin/sh", "-c"};

            ArrayList<String> combinedCmdLst = new ArrayList<>(tmp1.length + cmd.length);
            combinedCmdLst.addAll(Arrays.asList(tmp1));
            combinedCmdLst.addAll(Arrays.asList(cmd));

            String[] cmdObjs = new String[combinedCmdLst.size()];
            for (int i = 0; i < combinedCmdLst.size(); i++) {
                cmdObjs[i] = combinedCmdLst.get(i);
            }

            //Runtime.getRuntime().exec(cmdObjs);     // Execute the command
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }
    */

    private static void appendToPane(String msg, Color c) {
        try {
            cmdOutput.append(msg);
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
            aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

            //int len = cmdOutput.getStyledDocument().getLength();
            // cmdOutput.setCaretPosition(len);
            //cmdOutput.setCharacterAttributes(aset, false);
            cmdOutput.replaceSelection(msg);
        } catch (Exception e) {
            if (isDebug) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

            e.printStackTrace();
        }
    }

    private static boolean verifyJavaVersion () {
        return needsJavaUpdate = getJavaVersion() < Double.valueOf("11"); //< Integer.valueOf("10");
    }

    private static @NotNull Double getJavaVersion () {
        String version = System.getProperty("java.version");

        var tmp = "";
        return Double.parseDouble(version);

        /*
        int pos = version.indexOf('.');
        pos = version.indexOf('.', pos + 1);
        return Double.parseDouble(version.substring(0, pos));
        */
    }


}

