import com.sun.istack.internal.NotNull;
import javafx.concurrent.Task;
import javafx.scene.control.ScrollPane;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.image.GaussianBlurFilter;
import org.softsmithy.lib.swing.JXScrollPane;
import org.w3c.dom.css.Rect;

import javax.annotation.processing.ProcessingEnvironment;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Window;

//import static com.sun.awt.AWTUtilities.setWindowOpacity;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

/**
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class T3ALauncher implements SharedApplicationObjects {
    private static ViewController vc;
    private static T3ALauncherModel model;
    private static MainGUI ui;
    private static boolean isDebug, needsJavaUpdate = true;
    private static Thread javaVersionCheckTask;
    private static Thread graphicsThread;
    private static Runnable showUITask;
    private static Console console;
    private static DetailsView glassPane;


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Runnable javaChkTask = checkJavaInstallation();
            Thread javaThread = new Thread(javaChkTask);
            javaThread.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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
            Runnable javaVersionChk = T3ALauncher::performJava9UpdateBeforeLaunch;
            javaVersionCheckTask = new Thread(javaVersionChk);
            javaVersionCheckTask.start();
        };
    }

    private static void performJava9UpdateBeforeLaunch() {
        try {
            if (getJavaVersion() < 9.04 /*Integer.valueOf("9.0")*/ ) {//.substring(0, 4).replaceAll(".", ""))) {
                JOptionPane.showMessageDialog(null, "A Java update is required in order to launch this application.\n"
                        + "\nApplication requirement: " + "\"9.0.4\"" + "\nInstalled version: \"" + getJavaVersion()
                        + "\"\n\nPerforming Java update...");

                /*
                //Thread.sleep(300l);
                String cdCmd = null;
                String javaUpdateDownloadCmd = "/bin/sh curl -j -k -L -H \"Cookie: oraclelicense=accept-securebackup-cookie" +
                        "\" http://download.oracle.com/otn-pub/java/jdk/9.0.4+11/" +
                        "c2514751926b4512b076cc82f959763f/";
                String autoJavaInstallCmd = null;
                String deleteInstallerCmd = null;

                ///*
                if (UserInfo.isMac()) {
                    cdCmd = "/bin/sh -c ~/Downloads/";
                    javaUpdateDownloadCmd += "jre-9.0.4_osx-x64_bin.dmg > jre-9.0.4_osx-x64_bin.dmg;";
                    autoJavaInstallCmd = "hdiutil attach jre-9.0.4_osx-x64_bin.dmg; installer -pkg /Volumes/Java\\ " +
                            "9.0.4/Java\\ 9.0.4.app/Contents/Resources/JavaAppletPlugin.pkg -target /;";
                    deleteInstallerCmd = "diskutil unmount /Volumes/Java\\ 9.0.4/; rm -rf jre-9.0.4_osx-x64_bin.dmg;";
                } else if (UserInfo.isWindows()) {
                    cdCmd = "%USERPROFILE%\\Downloads";
                    javaUpdateDownloadCmd += "jre-9.0.4_windows-x64_bin.exe > jre-9.0.4_windows-x64_bin.exe";
                    autoJavaInstallCmd = "start /WAIT %~dp0jre-9.0.4_windows-x64_bin.exe /s /w";
                    deleteInstallerCmd = "del /f \"jre-9.0.4_windows-x64_bin.exe\"";
                } else if (UserInfo.isSolaris()) {
                    cdCmd = "cd $(xdg-user-dir DOWNLOAD)";
                    javaUpdateDownloadCmd += "jre-9.0.4_solaris-sparcv9_bin.tar.gz > jre-9.0.4_solaris-sparcv9_bin.tar.gz";
                    autoJavaInstallCmd = "tar zxf jre-9.0.4_solaris-sparcv9_bin.tar.gz";
                    deleteInstallerCmd = "rm jre-9.0.4_solaris-sparcv9_bin.tar.gz";
                } else if (UserInfo.isLinux()) {
                    cdCmd = "cd $(xdg-user-dir DOWNLOAD)";
                    javaUpdateDownloadCmd += "jre-9.0.4_linux-x64_bin.tar.gz > jre-9.0.4_linux-x64_bin.tar.gz";
                    autoJavaInstallCmd = "tar zxf jre-9.0.4_linux-x64_bin.tar.gz";
                    deleteInstallerCmd = "rm jre-9.0.4_linux-x64_bin.tar.gz";
                }

                boolean isMac = UserInfo.isMac();
                boolean isWindows = UserInfo.isWindows();
                boolean isSolaris = UserInfo.isSolaris();
                boolean isLinux = UserInfo.isLinux();
                */

                try {
                    if (UserInfo.isMac()) {
                        /*  Launching via command-line
                        Process p = Runtime.getRuntime().exec(new String[] {
                                "/bin/sh", "-c", "cd ~/Downloads/ &&" +
                                //"curl -C - -j -L -H \"Cookie: oraclelicense=accept-securebackup-cookie\" -k http://download.oracle.com/otn-pub/java/jdk/9.0.4+11/c2514751926b4512b076cc82f959763f/jre-9.0.4_osx-x64_bin.dmg > jre-9.0.4_osx-x64_bin.dmg; " +
                                "curl -C - -LR#OH \"Cookie: oraclelicense=accept-securebackup-cookie\" " +
                                "http://download.oracle.com/otn-pub/java/jdk/9.0.4+11/c2514751926b4512b076cc82f959763f/jre-9.0.4_osx-x64_bin.dmg > jre-9.0.4_osx-x64_bin.dmg; " +
                                "wait; " +
                                "open jre-9.0.4_osx-x64_bin.dmg && " +
                                "sudo installer -pkg /Volumes/Java\\ 9.0.4/Java\\ 9.0.4.app/Contents/Resources/JavaAppletPlugin.pkg -target /; " +
                                "wait; " +
                                "diskutil unmount /Volumes/Java\\ 9.0.4/ && " +
                                "rm -rf jre-9.0.4_osx-x64_bin.dmg && " +
                                "wait; "
                        });
                        */
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    e.printStackTrace();
                }

                if (UserInfo.isMac()) {
                    Process proc = null;
                    File javaDmgInstaller = new File(T3ALauncher.class.getResource("assets/_JavaUpdater/jre-9.0.4_osx-x64_bin.dmg").getPath());
                    String fmtdDmgPath = javaDmgInstaller.getPath().replaceAll("%20", "\\ ");
                    File dmg = new File(fmtdDmgPath);

                    if (dmg.exists()) {
                        //Desktop.getDesktop().open(dmg);
                        //Runtime.getRuntime().exec(new String[] { "open", "-j", "-g", fmtdDmgPath });

                        //"/bin/sh", "-c", "open", "-a", "Terminal", "-n",  //"/usr/bin/sh",

                        proc = Runtime.getRuntime().exec(new String[] {
                                "osascript", "-e", "do shell script \"hdiutil attach -debug -verbose '" + fmtdDmgPath + "'\""
                        });

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
                        };

                        tmp.setVisible(false);
                        tmp.setPreferredSize(new Dimension(600, 350));
                        tmp.setLocationRelativeTo(null);
                        tmp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                        DetailPanel mainPanel = new DetailPanel(new BorderLayout());
                        mainPanel.setPreferredSize(new Dimension(tmp.getWidth(), tmp.getHeight()));

                        JTextArea cmdOutput = new JTextArea() {
                            public void paintComponent (Graphics g) {
                                super.paintComponent(g);

                                Graphics2D g2d = (Graphics2D) g;
                                g2d.setFont(new Font("Verdana", Font.ITALIC,20));
                                g2d.setColor(Color.BLACK);
                                g2d.setComposite(AlphaComposite.SrcOut.derive(0.1f));

                                Rectangle rect = getVisibleRect();

                                Rectangle tmp = new Rectangle(
                                        rect.x + 5,
                                        rect.y + 5,
                                        Double.valueOf(rect.getWidth() - 5).intValue(),
                                        Double.valueOf(rect.getHeight() - 5).intValue()
                                );

                                rect = tmp;


                                int x = rect.width + rect.x - 14*getText().length();

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
                                        //setCaretPosition(getDocument().getLength());
                                        requestFocus();
                                        repaint();
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {
                                        setEnabled(true);
                                        setCaretPosition(getDocument().getLength());
                                        requestFocus();
                                        repaint();
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
                        cmdOutput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                        cmdOutput.setOpaque(true);
                        cmdOutput.addFocusListener(null);
                        cmdOutput.setLineWrap(true);
                        cmdOutput.setAutoscrolls(true);
                        cmdOutput.setWrapStyleWord(true);
                        cmdOutput.setEditable(true);
                        cmdOutput.setMargin(new Insets(0, 5, 0, 5));

                        DefaultCaret caret = (DefaultCaret) cmdOutput.getCaret();
                        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                        caret.setBlinkRate(1);
                        cmdOutput.setCaret(caret);

                        JXScrollPane scrollPane = new JXScrollPane(cmdOutput) {
                            /*
                            private BufferedImage blurBuffer;
                            private BufferedImage backBuffer = ImageIO.read(getClass().getResource("assets/WHITE_BLANK.png"));
                            private float alpha = 0.5f;
                            private final JXScrollPane SCROLL_PANE = this;
                            private DetailPanel detailPanel = new DetailPanel();
                            private DetailsView detailsView = new DetailsView(detailPanel);

                            @Override
                            protected void paintComponent(Graphics g) {
                                if (isVisible() && blurBuffer != null) {
                                    Graphics2D g2 = (Graphics2D) g.create();

                                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                                    g2.drawImage(backBuffer, 0, 0, null);

                                    alpha = 0.9f;
                                    g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
                                    g2.drawImage(blurBuffer, 0, 0, 350, 175, null);
                                    g2.dispose();
                                    super.paintComponent(g2);
                                    //return;
                                }

                                super.paintComponent(g);
                            }

                            @Override
                            public void setEnabled(boolean enabled) {
                                super.setEnabled(enabled);
                                setLayout(new GridBagLayout());

                                this.detailPanel.setAlpha(0.0f);
                                add(detailPanel, new GridBagConstraints());

                                // Should also disable key events...
                                addMouseListener(new MouseAdapter() { });
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

                            private void createBlur() {
                                JRootPane root = SwingUtilities.getRootPane(this);
                                blurBuffer = GraphicsUtilities.createCompatibleImage(
                                        800, 600);
                                Graphics2D g2 = blurBuffer.createGraphics();
                                root.paint(g2);
                                g2.dispose();


                                backBuffer = blurBuffer;
                                blurBuffer = GraphicsUtilities.createThumbnailFast(
                                        blurBuffer, 600);
                                blurBuffer = new GaussianBlurFilter(5).filter(blurBuffer, backBuffer);
                            }

                            @Override
                            public void addAncestorListener(AncestorListener listener) {
                                fadeIn();
                                super.addAncestorListener(listener);
                            }
                            */
                        };

                        scrollPane.setPreferredSize(new Dimension (
                                Double.valueOf(cmdOutput.getWidth() + 5).intValue(),
                                Double.valueOf(cmdOutput.getHeight() + 5).intValue()
                        ));


                        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        scrollPane.setBorder(BorderFactory.createEmptyBorder());
                        scrollPane.setWheelScrollingEnabled(true);
                        //scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0f));
                        scrollPane.setBackground(new Color(0, 0, 0, 0.3f));
                        scrollPane.setForeground(new Color(0, 0, 0, 0f));
                        //scrollPane.setForeground(Color.GREEN);


                        scrollPane.setViewportView(cmdOutput);
                        mainPanel.setOpaque(true);
                        //mainPanel.setOpaque(false);



                        DetailPanel popup = new DetailPanel();
                        popup.setPreferredSize(new Dimension(20, 20));

                        //cmdOutput.add(popup, BorderLayout.CENTER);
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

                        //scrollPane.setViewportViewGlassPane(glassPane);
                        //tmp.setComponentZOrder(glassPane, 0);
                        tmp.setGlassPane(glassPane);


                        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        //SwingUtilities.updateComponentTreeUI(tmp);

                        Thread.sleep(100L);
                        cmdOutput.append("T3ALauncher:~ " + "Attempting to mount disk image...");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        Thread.sleep(100L);
                        cmdOutput.append("\nT3ALauncher:~ " + "osascript -e 'do shell script \"hdiutil attach -debug -verbose '" + fmtdDmgPath + "''");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        Thread.sleep(100L);

                        while ((s = stdIn.readLine()) != null) {
                            //System.out.println(s);
                            //console.writer().println(s);
                            cmdOutput.append("\nhdutil:~ " + s);
                            cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                            cmdOutput.requestFocus();
                            Thread.sleep(102L);
                        }

                        // read any errors from the attempted command
                        while ((s = stdErr.readLine()) != null) {
                            //System.out.println(s);
                            //console.writer().println(s);
                            cmdOutput.append("\nhdutil:~ " + s);
                            cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                            cmdOutput.requestFocus();
                            Thread.sleep(105L);
                        }

                        cmdOutput.append("\nT3ALauncher:~ " + "Disk image mounted successfully!");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                        Thread.sleep(100L);
                        cmdOutput.append("\nT3ALauncher:~ " + "Running installer package...");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                        Thread.sleep(100L);
                        proc.destroy();

                        cmdOutput.append("\nT3ALauncher:~ " + "osascript -e 'do shell script \"sudo " +
                                "installer -verbose -pkg '/Volumes/Java 9.0.4/Java 9.0.4.app/" +
                                "Contents/Resources/JavaAppletPlugin.pkg' -target /\" " +
                                "with administrator privileges'");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        Thread.sleep(100L);
                        proc = Runtime.getRuntime().exec(new String[] {
                                "osascript", "-e", "do shell script \"sudo installer -verbose -pkg " +
                                "'/Volumes/Java 9.0.4/Java 9.0.4.app/Contents/Resources/" +
                                "JavaAppletPlugin.pkg' -target /; \" with administrator privileges"
                        });

                        cmdOutput.requestFocus();

                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

                        // read the output from the command
                        //String
                                s = null;
                        while ((s = stdInput.readLine()) != null) {
                            //System.out.println(s);
                            cmdOutput.append("\nJavaAppletPlugin:~ " + s);
                            cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                            cmdOutput.requestFocus();
                            Thread.sleep(100L);
                        }

                        // read any errors from the attempted command
                        while ((s = stdError.readLine()) != null) {
                            //System.out.println(s);
                            cmdOutput.append("\nJavaAppletPluginInstaller:~ " + s);
                            cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                            cmdOutput.requestFocus();
                            Thread.sleep(103L);
                        }

                        cmdOutput.append("\nT3ALauncher:~ Java applet plugin updated successfully!");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                        Thread.sleep(100L);

                        while (true) {
                            //int exitVal = proc.waitFor();

                            if (proc.exitValue() == 0) {
                                proc.destroy();
                                //Thread.sleep(1200L);
                                //tmp.dispose();
                                //Thread.sleep(500L);
                                cmdOutput.append("\nT3ALauncher:~ Initializing application...");
                                cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                                cmdOutput.requestFocus();
                                Thread.sleep(100L);

                                Runnable initApp = T3ALauncher::initApplication;
                                Thread initTask = new Thread(initApp);
                                initTask.run();

                                cmdOutput.append("\nT3ALauncher:~ Launcher initialized successfully!");
                                cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                                cmdOutput.requestFocus();
                                Thread.sleep(100L);
                                break;
                            }

                            cmdOutput.requestFocus();
                        }

                        cmdOutput.append("\nT3ALauncher:~ " + "diskutil unmount /Volumes/Java 9.0.4/; wait;");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                        Thread.sleep(100L);
                        cmdOutput.append("\nT3ALauncher:~ " + "Unmounting disk...");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                        Thread.sleep(100L);
                        Runtime.getRuntime().exec("diskutil unmount /Volumes/Java\\ 9.0.4/; wait;");
                        cmdOutput.append("\nT3ALauncher:~ " + "Disk unmounted successfully!");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                        Thread.sleep(100L);
                        cmdOutput.append("\nT3ALauncher:~ ");
                        cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        cmdOutput.requestFocus();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Your version of Java is up to date!\nJava version: " + System.getProperty("java.version"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }

    public static void executeMacTerminalCmd(String...cmd) {
        if (cmd == null) { return; }

        try {
            String[] tmp1 = new String[] { "/bin/sh", "-c" };
            String[] tmp2 = cmd;

            ArrayList<String> combinedCmdLst = new ArrayList<>(tmp1.length + tmp2.length);
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

    private static boolean verifyJavaVersion() {
        return needsJavaUpdate = getJavaVersion() < Integer.valueOf("904");
    }

    @NotNull
    private static Double getJavaVersion() {
        String version = System.getProperty("java.version");
        int pos = version.indexOf('.');
        pos = version.indexOf('.', pos + 1);
        return Double.parseDouble(version.substring(0, pos));
    }

}
