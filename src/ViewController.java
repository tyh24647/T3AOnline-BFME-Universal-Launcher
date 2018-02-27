import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

/**
 * ViewController object acts as a delegate between the model and view objects
 *
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class ViewController implements ActionListener, SharedApplicationObjects {
    private boolean isDebug;
    private T3ALauncherModel model;
    private MainGUI view;
    private JComponent[] viewComponents;
    private String previousCmd;
    private JRadioButton previousBtn, currentBtn;

    /**
     * Default constructor
     */
    public ViewController() {

    }

    /**
     * Constructs view controller using custom debug settings
     *
     * @param isDebug
     */
    public ViewController(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     *
     * @param model
     * @param view
     */
    public ViewController(@NotNull T3ALauncherModel model, @NotNull MainGUI view) {
        this.model = model;
        this.view = view;
    }

    /**
     *
     * @param isDebug
     * @param model
     * @param view
     */
    public ViewController(boolean isDebug, @NotNull T3ALauncherModel model, @NotNull MainGUI view) {
        this.isDebug = isDebug;
        this.model = model;
        this.view = view;
        this.previousCmd = "BFME1";
        this.previousBtn = view.getB1Btn();
        this.currentBtn = view.getB1Btn();

        initUI();
        addActionListeners();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = dimension.getWidth();
        Double y = dimension.getHeight();
        JOptionPane.showMessageDialog(view, ("Your computer's " +
                "native resolution has been detected to be: \"").concat(String.valueOf(x)).concat("x")
                .concat(String.valueOf(y)).concat("\"\nSetting value as default."));

        for (int i = 0; i < view.getResChooser().getItemCount() - 1; i++) {
            if (((String) view.getResChooser().getItemAt(i)).substring(0, 2).contains(x.toString().substring(0, 2))) {
                view.getResChooser().setSelectedItem(view.getResChooser().getItemAt(i));
                view.getResChooser().setSelectedIndex(i);
            }
        }


    }

    public void initUI() {
        initUI(true);
    }

    public void initUI(boolean isDebug) {
        this.isDebug = isDebug;

        try {
            view.showUI();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void addActionListeners() {
        view.getB1Btn().addActionListener(this);
        view.getB2Btn().addActionListener(this);
        view.getRotwkBtn().addActionListener(this);
        view.getResChooser().addActionListener(this);
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public T3ALauncherModel getModel() {
        return model;
    }

    public void setModel(T3ALauncherModel model) {
        this.model = model;
    }

    public MainGUI getView() {
        return view;
    }

    public void setView(MainGUI view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() instanceof JComponent) {
                JComponent component = (JComponent) e.getSource();

                if (component instanceof JRadioButton) {
                    JRadioButton btn = (JRadioButton) component;
                    JRadioButton otherBtn = previousBtn;
                    currentBtn = btn;

                    if (!btn.getActionCommand().equals(previousCmd) && !btn.getActionCommand().equals(model.getSelectedGame().getName())) {

                        String btmTxt = "Lord of the Rings - The Battle for Middle-Earth";


                        //if (btn.getActionCommand().equals(view.getB1Btn().getActionCommand())) {
                        //if (btn.getActionCommand().equals(currentBtn : currentBtn.getActionCommand())) {
                            //otherBtn = view.getB2Btn();
                            //otherBtn = view.getCurrentBtn();

                            /*
                            //
                            if (!view.getB1Btn().isSelected()) {
                                view.getB1Btn().setSelected(true);
                                view.getB2Btn().setSelected(false);
                                model.setSelectedGame(Game.BFME1);
                            }
                            /

                            if (!previousBtn.isSelected()) {
                                currentBtn.setSelected(true);
                                previousBtn.setSelected(false);
                                model.setSelectedGame(currentBtn.getActionCommand().equals("BFME2") ? Game.BFME2 : currentBtn.getActionCommand().equals("BFME1") ? Game.BFME1 : Game.ROTWK);
                            }

                            */
                            view.getLotrTitleTxt1().setText(btmTxt);
                        //} else {
                            //otherBtn = view.getB1Btn();

                            otherBtn = previousBtn == null ? view.getB1Btn() : previousBtn; //view.getB1Btn();

                            /*
                            if (!view.getB2Btn().isSelected()) {
                                view.getB2Btn().setSelected(true);
                                view.getB1Btn().setSelected(false);
                                model.setSelectedGame(Game.BFME2);
                            }
                            */
                            if (!currentBtn.isSelected()) {
                                currentBtn.setSelected(true);
                                otherBtn.setSelected(false);
                                model.setSelectedGame(currentBtn.getActionCommand().equals("BFME2") ? Game.BFME2 : currentBtn.getActionCommand().equals("BFME1") ? Game.BFME1 : Game.ROTWK);
                            }

                            String newTxt = btmTxt.concat(" II + Rise of the Witch King");
                            view.getLotrTitleTxt1().setText(newTxt);
                        //}

                        if (!btn.isSelected()) {
                            btn.setSelected(true);
                            otherBtn.setSelected(false);
                        }

                        //currentBtn = btn;

                        otherBtn = previousBtn == null ? view.getB1Btn() : previousBtn;


                        /*
                        try {
                            //view.getBkgdImgPanel().fireImgShouldChange();
                            previousBtn = previousBtn == null ? view.getB1Btn() : previousBtn;
                            String img1Path = "assets/" + previousBtn.getActionCommand() + ".png";
                            String img2Path = "assets/" + currentBtn.getActionCommand() + ".png";
                            Image img1 = ImageIO.read(getClass().getResource(img1Path));//new ImageIcon(img1Path).getImage();
                            Image img2 = ImageIO.read(getClass().getResource(img2Path));//new ImageIcon(img2Path).getImage();
                            //JOptionPane.showMessageDialog(null, "Image 1 path: \"" + img1Path + "\"\nImage 2 path: \"" + img2Path + "\"");
                            view.getBkgdImgPanel().chgImgSelections(img1, img2);
                            view.getBkgdImgPanel().fireImgShouldChange();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                        }
                        */


                        previousBtn = btn;
                    } else {
                        //otherBtn = previousBtn == null ? view.getB1Btn() : previousBtn;

                        if (!btn.isSelected()) {
                            if (btn.getActionCommand().equals(view.getB1Btn().getActionCommand())) {
                                view.getB2Btn().setSelected(false);
                                view.getRotwkBtn().setSelected(false);
                            } else if (btn.getActionCommand().equals(view.getB2Btn().getActionCommand())) {
                                view.getB1Btn().setSelected(false);
                                view.getRotwkBtn().setSelected(false);
                            } else if (btn.getActionCommand().equals(view.getRotwkBtn().getActionCommand())) {
                                view.getB1Btn().setSelected(false);
                                view.getB2Btn().setSelected(false);
                            }

                            btn.setSelected(true);
                        }
                    }
                    ///*
                    otherBtn = previousBtn == null ? view.getB1Btn() : previousBtn;

                    /*
                    String img1Path = "assets/" + otherBtn.getActionCommand() + ".png";
                    String img2Path = "assets/" + btn.getActionCommand() + ".png";
                    Image img1 = ImageIO.read(getClass().getResource(img1Path));//new ImageIcon(img1Path).getImage();
                    Image img2 = ImageIO.read(getClass().getResource(img2Path));
                    */
                    //view.getBkgdImgPanel().chgImgSelections(img1, img2);

                    view.getBkgdImgPanel().chgImgSelectionsForActionCommand(otherBtn.getActionCommand(), btn.getActionCommand());

                    //view.getBkgdImgPanel().fireImgShouldChange();
                    //*/

                    previousBtn = btn;
                    //currentBtn = null;

                    System.out.println("Previous command: \"".concat(previousCmd).concat("\""));
                    System.out.println("Current command: \"".concat(e.getActionCommand()).concat("\""));
                } else if (component instanceof JComboBox) {
                    String parsedResolution = ((String)((JComboBox) component).getSelectedItem());
                    parsedResolution = parsedResolution == null ? "" : parsedResolution;
                    String xRes, yRes;
                    String t2[] = parsedResolution.split("x");
                    xRes = t2[0];
                    yRes = t2[1];
                    Integer xVal = null;
                    Integer yVal = null;
                    ResObject resolution = null;

                    try {
                        xVal = Integer.parseInt(xRes);
                        yVal = Integer.parseInt(yRes);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, ex.getMessage());
                    }

                    if (xRes != null && yRes != null) {
                        resolution = new ResObject(xVal, yVal);
                    }

                    /*
                    if (SysInfo.DEBUG_MODE) {
                        if (resolution != null) {
                            JOptionPane.showMessageDialog(view, "Resolution: \"".concat(resolution.getDescription()).concat("\""));
                        }
                    }
                    */

                    // TODO write to user info
                }
            }


        } catch (Exception ex) {
            System.out.println("\n\nERROR: " + ex.getMessage());
            JOptionPane.showMessageDialog(view.getContentPane(), ex.getMessage());
            //ex.printStackTrace();
        }

        if (e.getSource() instanceof JRadioButton) {
            previousCmd = e.getActionCommand();
        }


    }
}
