import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.WEST;
import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.JLayeredPane.FRAME_CONTENT_LAYER;
import static javax.swing.JLayeredPane.MODAL_LAYER;


/**
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class MainGUI extends JFrame implements WindowFocusListener {
    public static final String DEFAULT_LOTR_TITLE_TXT = "Lord of the Rings - The Battle for Middle-Earth";
    public static final String ROTWK_TXT = " II + Rise of the Witch King";

    private JLayeredPane layeredPane;
    private JPanel mainPanel, gameSelectionPanel, backgroundPanel, titleTxtPanel, centerPanel, centerContents, resChoosingPanel, btnPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, viewMenu, windowMenu, devMenu, helpMenu;
    private JMenuItem saveMI, exitMI, undoMI, redoMI, showDebugMI;
    private JRadioButton b1Btn, b2Btn;
    private JLabel lotrTitleTxt1, bkgdImgLabel, imgLbl2;
    private FadeInPanel bkgdImgPanel;
    private JComboBox<String> resChooser;
    private String selectedGame;
    private ButtonGroup radioBtns;

    public MainGUI() {

    }



    public void generateViewComponents() {

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
        radioBtns = new ButtonGroup();
        radioBtns.add(b1Btn);
        radioBtns.add(b2Btn);

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
        centerContents = new JPanel(new BorderLayout(60, 30));
        resChoosingPanel = new JPanel(new FlowLayout());


        centerContents.setOpaque(true);
        centerContents.setBackground(new Color(1, 1, 1, 0.20f));
        centerContents.setPreferredSize(new Dimension(400, 300));

        resChooser = new JComboBox<>(new String[] {
                "Select...", "800x600", "1280x800", "1440x900", "1680x1050", "1920x1080"
        }) {
            @Nullable
            @Override
            public Object getSelectedItem() {
                return super.getSelectedItem();
            }

            @Override
            public void setSelectedIndex(int anIndex) {
                //super.setSelectedIndex(anIndex == 0 ? 1 : anIndex);
                super.setSelectedIndex(anIndex);
            }

            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
            }

            @Override
            protected void selectedItemChanged() {
                if (getSelectedIndex() > 0) {
                    super.selectedItemChanged();
                }
            }
        };

        resChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        });

        JLabel resolutionLbl = new JLabel("Select a resolution...");
        resolutionLbl.setForeground(Color.WHITE);
        resolutionLbl.setFont(resolutionLbl.getFont().deriveFont(Font.PLAIN, 14f));
        resChoosingPanel.add(resolutionLbl);

        resChooser.setOpaque(false);
        resChooser.setSize(new Dimension(300, (int)super.getPreferredSize().getHeight() + 4));
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

        try {
            Image bkgd1 = ImageIO.read(getClass().getResource("assets/b1.png"));
            Image bkgd2 = ImageIO.read(getClass().getResource("assets/b2.png"));

            bkgdImgPanel = new FadeInPanel(bkgd1, bkgd2);
            bkgdImgPanel.setOpaque(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            bkgdImgPanel = new FadeInPanel();
        }

        lotrTitleTxt1 = new JLabel(DEFAULT_LOTR_TITLE_TXT);
        lotrTitleTxt1.setHorizontalAlignment(SwingConstants.CENTER);
        lotrTitleTxt1.setForeground(Color.WHITE);
        lotrTitleTxt1.setFont(lotrTitleTxt1.getFont().deriveFont(Font.PLAIN, 36));

        titleTxtPanel = new JPanel(new FlowLayout());
        titleTxtPanel.add(lotrTitleTxt1);
        //titleTxtPanel.setBounds(0, 40, 800, 600);
        titleTxtPanel.setPreferredSize(new Dimension(800, 600));
        titleTxtPanel.setOpaque(true);


        mainPanel.add(gameSelectionPanel, BorderLayout.NORTH);
        gameSelectionPanel.setOpaque(true);
        gameSelectionPanel.setPreferredSize(new Dimension(800, 40));


        //mainPanel.setPreferredSize(new Dimension(800, 600));

        mainPanel.setOpaque(false);

        layeredPane.add(bkgdImgPanel, BorderLayout.CENTER, DEFAULT_LAYER);
        layeredPane.add(mainPanel, BorderLayout.CENTER, DEFAULT_LAYER);


        bkgdImgPanel.setBounds(0, 40, 800, 600);
        mainPanel.setBounds(0, 0, 800, 640);


        layeredPane.setLayer(bkgdImgPanel, FRAME_CONTENT_LAYER);
        layeredPane.setLayer(mainPanel, DEFAULT_LAYER);


        //layeredPane.setBackground(Color.BLACK.brighter());
        //layeredPane.setPreferredSize(new Dimension(800, 640));
        layeredPane.setBounds(0, 0, 800, 640);

        /*
        empty.add(layeredPane, BorderLayout.CENTER);
        layeredPane.setOpaque(true);
        empty.setPreferredSize(new Dimension(800, 640));
        empty.setBorder(null);

        setContentPane(layeredPane);

        return empty;
        */
        //layeredPane.add(centerPanel, BorderLayout.CENTER, DEFAULT_LAYER);

        layeredPane.setOpaque(true);
        return layeredPane;
    }

    private ImageIcon getImgResource(String imgPath) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(getClass().getResource(imgPath));
        ImageIcon imgIcon = new ImageIcon(bufferedImage);
        Image formattedImg = imgIcon.getImage();
        imgIcon.setImage(formattedImg);
        return imgIcon;
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

    public void showUI() throws NullPointerException {
        setJMenuBar(generateMenuBar());
        //add(generateMainPanel());
        setLayeredPane(generateMainPanel());
        //setContentPane(mainPanel);

        configureMainWindow();
        launchMainWindow();



    }

    private void configureMainWindow() {
        setPreferredSize(new Dimension(800,640));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setResizable(false);
        setTitle(StringConstants.Titles.MAIN_PANEL);
        setLocationRelativeTo(null);
    }

    private void launchMainWindow() {
        pack();
        setVisible(true);
    }

    //region SETTERS AND GETTERS


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

    public void setB2Btn(JRadioButton b2Btn) {
        this.b2Btn = b2Btn;
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
}
