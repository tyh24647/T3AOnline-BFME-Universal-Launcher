import javafx.concurrent.Task;
import javafx.scene.control.ScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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

                        Runtime.getRuntime().exec(new String[] {
                                "/bin/sh", "-c", "hdiutil attach \"" + fmtdDmgPath + "\"; wait;",
                        });

                        JFrame tmp = new JFrame("Java 9.0.4 Installer");
                        tmp.setVisible(false);
                        tmp.setPreferredSize(new Dimension(600, 350));
                        tmp.setLocationRelativeTo(null);
                        tmp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                        JPanel mainPanel = new JPanel(new BorderLayout());
                        mainPanel.setPreferredSize(new Dimension(tmp.getWidth(), tmp.getHeight()));
                        JTextArea cmdOutput = new JTextArea();
                        cmdOutput.setBackground(Color.BLACK);
                        cmdOutput.setForeground(Color.WHITE);
                        cmdOutput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
                        cmdOutput.setOpaque(true);
                        cmdOutput.setLineWrap(true);
                        JScrollPane scrollPane = new JScrollPane(cmdOutput);
                        scrollPane.setPreferredSize(cmdOutput.getSize());
                        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                        scrollPane.setAutoscrolls(true);
                        mainPanel.add(scrollPane, BorderLayout.CENTER);
                        tmp.add(mainPanel);
                        tmp.validate();
                        tmp.setVisible(true);
                        tmp.pack();

                        proc = Runtime.getRuntime().exec(new String[] {
                                "osascript", "-e", "do shell script \"sudo installer -verbose -pkg '/Volumes/Java 9.0.4/Java 9.0.4.app/Contents/Resources/JavaAppletPlugin.pkg' -target /; echo; echo;\" with administrator privileges"
                        });

                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

                        // read the output from the command
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                            cmdOutput.append("\n" + s);
                            Thread.sleep(100L);
                            cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                        }

                        // read any errors from the attempted command
                        while ((s = stdError.readLine()) != null) {
                            System.out.println(s);
                            cmdOutput.append("\n" + s);
                            Thread.sleep(100L);
                            cmdOutput.setCaretPosition(cmdOutput.getDocument().getLength());
                            tmp.repaint();
                        }

                        while (true) {
                            int exitVal = proc.waitFor();

                            if (exitVal == 0) {
                                proc.destroy();
                                Thread.sleep(1200L);
                                tmp.dispose();
                                Thread.sleep(500L);
                                initApplication();
                                break;
                            }
                        }

                        Runtime.getRuntime().exec("diskutil unmount /Volumes/Java\\ 9.0.4/; wait;");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Your version of Java is up to date!\nJava version: " + System.getProperty("java.version"));
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

    private static @NotNull Double getJavaVersion() {
        String version = System.getProperty("java.version");
        int pos = version.indexOf('.');
        pos = version.indexOf('.', pos + 1);
        return Double.parseDouble(version.substring(0, pos));
    }
}
