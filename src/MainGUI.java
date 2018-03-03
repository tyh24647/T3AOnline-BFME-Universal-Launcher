

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.WEST;
import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.JLayeredPane.FRAME_CONTENT_LAYER;


/**
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class MainGUI extends JFrame implements WindowFocusListener, ISharedApplicationObjects {
    public static final String DEFAULT_LOTR_TITLE_TXT = "Lord of the Rings - The Battle for Middle-Earth";
    public static final String ROTWK_TXT = " II + Rise of the Witch King";

    private static final String[] DEFAULT_RES_OPTIONS = {
            "Select...", "800x600", "1280x800", "1440x900", "1680x1050", "1920x1080"
    };

    private JLayeredPane layeredPane;
    private JPanel mainPanel, gameSelectionPanel, backgroundPanel, centerPanel, titleTxtPanel, resChoosingPanel, btnPanel; //, centerContents;
    private GlassPanel centerContents;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, viewMenu, windowMenu, devMenu, helpMenu;
    private JMenuItem saveMI, exitMI, undoMI, redoMI, showDebugMI;
    private JRadioButton b1Btn, b2Btn, rotwkBtn;
    private JLabel lotrTitleTxt1, bkgdImgLabel, imgLbl2;
    private FadeInPanel bkgdImgPanel;
    private JComboBox<String> resChooser;
    private String selectedGame;
    private ButtonGroup radioBtns;
    private String[] resOptions;

    /**
     * Create a new instance of the UI
     */
    public MainGUI() {
        this.resOptions = DEFAULT_RES_OPTIONS;
    }

    public JLayeredPane generateMainPanel() {
        JPanel empty = new JPanel(new BorderLayout());
        layeredPane = new JLayeredPane();
        backgroundPanel = new JPanel(new BorderLayout());

        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
        }

        b1Btn = new JRadioButton("BFME1");
        b2Btn = new JRadioButton("BFME2");
        rotwkBtn = new JRadioButton("ROTWK");

        gameSelectionPanel = new JPanel(new BorderLayout());
        gameSelectionPanel.setOpaque(false);
        gameSelectionPanel.setBackground(Color.BLACK);
        gameSelectionPanel.setForeground(Color.WHITE);
        btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(b1Btn);
        b1Btn.setOpaque(false);
        b1Btn.setContentAreaFilled(true);
        b1Btn.setSelected(true);
        b1Btn.setBackground(Color.BLACK);
        b1Btn.setForeground(Color.WHITE);
        btnPanel.add(b2Btn);
        b2Btn.setOpaque(false);
        b2Btn.setContentAreaFilled(true);
        b2Btn.setSelected(false);
        b2Btn.setBackground(Color.BLACK);
        b2Btn.setForeground(Color.WHITE);
        btnPanel.add(rotwkBtn);
        rotwkBtn.setOpaque(false);
        rotwkBtn.setContentAreaFilled(true);
        rotwkBtn.setSelected(false);
        rotwkBtn.setBackground(Color.BLACK);
        rotwkBtn.setForeground(Color.WHITE);
        radioBtns = new ButtonGroup();
        radioBtns.add(b1Btn);
        radioBtns.add(b2Btn);
        radioBtns.add(rotwkBtn);

        //btnPanel.setBackground(Color.BLACK);
        btnPanel.setAlignmentX(CENTER_ALIGNMENT);
        btnPanel.setAlignmentY(CENTER_ALIGNMENT);
        gameSelectionPanel.add(btnPanel);
        btnPanel.setOpaque(true);
        btnPanel.setBackground(Color.BLACK);
        gameSelectionPanel.setOpaque(false);
        //gameSelectionPanel.setBounds(0,0, 800, 40);
        gameSelectionPanel.setPreferredSize(new Dimension(800, 40));


        centerPanel = new JPanel(new BorderLayout(30, 60));
        //centerContents = new JPanel(new BorderLayout(60, 30));
        centerContents = new GlassPanel(new BorderLayout(60, 30));

        resChoosingPanel = new JPanel(new FlowLayout()) {
            @Override
            public boolean isOpaque() {
                return false;
            }
        };

        centerContents.setOpaque(true);

        centerContents.setPreferredSize(new Dimension(400, 300));

        resChooser = new JComboBox<>(resOptions) {
            @Override
            public boolean isOpaque() {
                return false;
            }

            @Override
            protected void selectedItemChanged() {
                if (getSelectedIndex() > 0) {
                    super.selectedItemChanged();
                }

                layeredPane.repaint();
            }

            @Override
            protected void fireActionEvent() {
                super.fireActionEvent();
                layeredPane.repaint();
            }

            @Override
            public void firePopupMenuCanceled() {
                super.firePopupMenuCanceled();
                layeredPane.repaint();
            }
        };

        resChooser.setOpaque(true);
        resChooser.setSize(new Dimension(400, (int)super.getPreferredSize().getHeight() + 4));

        JLabel resolutionLbl = new JLabel("Display Resolution:", FlowLayout.LEFT);
        resolutionLbl.setForeground(Color.WHITE);
        resolutionLbl.setFont(resolutionLbl.getFont().deriveFont(Font.PLAIN, 14f));
        resChoosingPanel.add(resolutionLbl);
        resChoosingPanel.add(resChooser, BorderLayout.CENTER);

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(150, 60));
        resChoosingPanel.setOpaque(false);
        resChoosingPanel.add(label);
        centerContents.add(resChoosingPanel, BorderLayout.NORTH);
        resChoosingPanel.setPreferredSize(new Dimension(800, 200));
        centerPanel.add(centerContents, BorderLayout.CENTER);

        label = new JLabel("");
        label.setPreferredSize(new Dimension(60, getContentPane().getHeight()));
        centerPanel.add(label, EAST);
        label = new JLabel("");
        label.setPreferredSize(new Dimension(60, getContentPane().getHeight()));
        centerPanel.add(label, WEST);
        label = new JLabel("");
        label.setPreferredSize(new Dimension(super.getWidth(), 40));

        centerPanel.add(label, BorderLayout.SOUTH);
        centerPanel.add(new JLabel(""), BorderLayout.NORTH);
        centerPanel.setOpaque(false);
        centerPanel.setPreferredSize(new Dimension(800, 600));


        mainPanel.add(centerPanel, BorderLayout.CENTER);



        bkgdImgPanel = new FadeInPanel();
        bkgdImgPanel.setOpaque(true);

        lotrTitleTxt1 = new JLabel(DEFAULT_LOTR_TITLE_TXT);
        lotrTitleTxt1.setHorizontalAlignment(SwingConstants.CENTER);
        lotrTitleTxt1.setForeground(Color.WHITE);
        lotrTitleTxt1.setFont(lotrTitleTxt1.getFont().deriveFont(Font.PLAIN, 36));

        titleTxtPanel = new JPanel(new FlowLayout());
        titleTxtPanel.add(lotrTitleTxt1);
        //titleTxtPanel.setBounds(0, 40, 800, 600);  <-- TODO fix this
        titleTxtPanel.setPreferredSize(new Dimension(800, 600));
        titleTxtPanel.setOpaque(true);

        mainPanel.add(gameSelectionPanel, BorderLayout.NORTH);
        gameSelectionPanel.setOpaque(true);
        gameSelectionPanel.setPreferredSize(new Dimension(800, 40));

        bkgdImgPanel.setBounds(0, 40, 800, 600);

        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, 800, 640);

        layeredPane.add(bkgdImgPanel, BorderLayout.CENTER, DEFAULT_LAYER);
        layeredPane.add(mainPanel, BorderLayout.CENTER, DEFAULT_LAYER);
        layeredPane.setLayer(bkgdImgPanel, FRAME_CONTENT_LAYER);
        layeredPane.setLayer(mainPanel, DEFAULT_LAYER);
        layeredPane.setBounds(0, 0, 800, 640);
        layeredPane.setOpaque(true);

        return layeredPane;
    }


    public JMenuBar generateMenuBar() {
        menuBar = new JMenuBar();

        if (UserInfo.isMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "BFME Resolution Changer");
            System.setProperty("com.apple.macos.use-file-dialog-packages", "true");
            System.setProperty("apple.awt.application.name", "BFME Resolution Changer");
            System.setProperty("com.apple.smallTabs", "true");
        }

        saveMI = new JMenuItem("Save");
        exitMI = new JMenuItem("Exit");

        fileMenu = new JMenu("File");
        fileMenu.add(saveMI);
        fileMenu.add(exitMI);

        editMenu = new JMenu("Edit");
        viewMenu = new JMenu("View");
        windowMenu = new JMenu("Window");
        devMenu = new JMenu("Developer");
        helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(windowMenu);
        menuBar.add(devMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    public void initUI() throws NullPointerException {
        setJMenuBar(generateMenuBar());
        //add(generateMainPanel());
        setLayeredPane(generateMainPanel());
        //setContentPane(mainPanel);

        configureMainWindow();
        pack();
        setVisible(false);
        //launchMainWindow();
    }

    private void configureMainWindow() {
        setPreferredSize(new Dimension(800,640));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setResizable(false);
        setTitle(StringConstants.Titles.MAIN_PANEL);
        setLocationRelativeTo(null);
    }

    public void launchMainWindow() {
        setVisible(true);
    }

    //region INHERITED METHODS
    @Override
    public void setVisible(boolean b) {
        if (b) {
            super.requestFocus();
            repaint();
        }

        super.setVisible(b);
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        setAlwaysOnTop(true);
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        setAlwaysOnTop(true);
    }

    //endregion


    //region SETTERS AND GETTERS


    public void setResOptions(String[] resOptions) {
        this.resOptions = resOptions;
    }

    public JComboBox<String> getResChooser() {
        return resChooser;
    }

    public JPanel getResChoosingPanel() {
        return resChoosingPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getBtnPanel() {
        return btnPanel;
    }

    public ButtonGroup getRadioBtns() {
        return radioBtns;
    }

    public JPanel getCenterContents() {
        return centerContents;
    }

    public void setImgLbl2(JLabel imgLbl2) {
        this.imgLbl2 = imgLbl2;
    }

    public JLabel getImgLbl2() {
        return imgLbl2;
    }

    public void setBkgdImgPanel(FadeInPanel bkgdImgPanel) {
        this.bkgdImgPanel = bkgdImgPanel;
    }

    public FadeInPanel getBkgdImgPanel() {
        return bkgdImgPanel;
    }

    public void setBkgdImgLabel(JLabel bkgdImgLabel) {
        this.bkgdImgLabel = bkgdImgLabel;
    }

    public JLabel getBkgdImgLabel() {
        return bkgdImgLabel;
    }

    public void setTitleTxtPanel(JPanel titleTxtPanel) {
        this.titleTxtPanel = titleTxtPanel;
    }

    public JPanel getTitleTxtPanel() {
        return titleTxtPanel;
    }

    public JPanel getBackgroundPanel() {
        return backgroundPanel;
    }

    public void setBackgroundPanel(JPanel backgroundPanel) {
        this.backgroundPanel = backgroundPanel;
    }

    public void setLotrTitleTxt1(JLabel lotrTitleTxt1) {
        this.lotrTitleTxt1 = lotrTitleTxt1;
    }

    public JLabel getLotrTitleTxt1() {
        return lotrTitleTxt1;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getGameSelectionPanel() {
        return gameSelectionPanel;
    }

    public void setGameSelectionPanel(JPanel gameSelectionPanel) {
        this.gameSelectionPanel = gameSelectionPanel;
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public void setFileMenu(JMenu fileMenu) {
        this.fileMenu = fileMenu;
    }

    public JMenu getEditMenu() {
        return editMenu;
    }

    public void setEditMenu(JMenu editMenu) {
        this.editMenu = editMenu;
    }

    public JMenu getViewMenu() {
        return viewMenu;
    }

    public void setViewMenu(JMenu viewMenu) {
        this.viewMenu = viewMenu;
    }

    public JMenu getWindowMenu() {
        return windowMenu;
    }

    public void setWindowMenu(JMenu windowMenu) {
        this.windowMenu = windowMenu;
    }

    public JMenu getDevMenu() {
        return devMenu;
    }

    public void setDevMenu(JMenu devMenu) {
        this.devMenu = devMenu;
    }

    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public void setHelpMenu(JMenu helpMenu) {
        this.helpMenu = helpMenu;
    }

    public JMenuItem getSaveMI() {
        return saveMI;
    }

    public void setSaveMI(JMenuItem saveMI) {
        this.saveMI = saveMI;
    }

    public JMenuItem getExitMI() {
        return exitMI;
    }

    public void setExitMI(JMenuItem exitMI) {
        this.exitMI = exitMI;
    }

    public JMenuItem getUndoMI() {
        return undoMI;
    }

    public void setUndoMI(JMenuItem undoMI) {
        this.undoMI = undoMI;
    }

    public JMenuItem getRedoMI() {
        return redoMI;
    }

    public void setRedoMI(JMenuItem redoMI) {
        this.redoMI = redoMI;
    }

    public JMenuItem getShowDebugMI() {
        return showDebugMI;
    }

    public void setShowDebugMI(JMenuItem showDebugMI) {
        this.showDebugMI = showDebugMI;
    }

    public JRadioButton getB1Btn() {
        return b1Btn;
    }

    public void setB1Btn(JRadioButton b1Btn) {
        this.b1Btn = b1Btn;
    }

    public JRadioButton getB2Btn() {
        return b2Btn;
    }

    public JRadioButton getRotwkBtn() {
        return rotwkBtn;
    }

    public void setB2Btn(JRadioButton b2Btn) {
        this.b2Btn = b2Btn;
    }
    //endregion
}
