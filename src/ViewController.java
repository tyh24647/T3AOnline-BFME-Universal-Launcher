import com.sun.istack.internal.NotNull;

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
public class ViewController implements ActionListener {
    private boolean isDebug;
    private T3ALauncherModel model;
    private MainGUI ui;
    private JComponent[] viewComponents;
    private String previousCmd;

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
     * @param ui
     */
    public ViewController(@NotNull T3ALauncherModel model, @NotNull MainGUI ui) {
        this.model = model;
        this.ui = ui;
    }

    /**
     *
     * @param isDebug
     * @param model
     * @param ui
     */
    public ViewController(boolean isDebug, @NotNull T3ALauncherModel model, @NotNull MainGUI ui) {
        this.isDebug = isDebug;
        this.model = model;
        this.ui = ui;
        this.previousCmd = "BFME1";
        initUI();
        addActionListeners();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = dimension.getWidth();
        Double y = dimension.getHeight();
        JOptionPane.showMessageDialog(ui, ("Your computer's " +
                "native resolution has been detected to be: \"").concat(String.valueOf(x)).concat("x")
                .concat(String.valueOf(y)).concat("\"\nSetting value as default."));
        for (int i = 0; i < ui.getResChooser().getItemCount() - 1; i++) {
            if (((String)ui.getResChooser().getItemAt(i)).substring(0, 2).contains(x.toString().substring(0, 2))) {
                ui.getResChooser().setSelectedItem(ui.getResChooser().getItemAt(i));
                ui.getResChooser().setSelectedIndex(i);
            }
        }


    }

    public void initUI() {
        initUI(true);
    }

    public void initUI(boolean isDebug) {
        this.isDebug = isDebug;

        try {
            ui.showUI();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void addActionListeners() {
        ui.getB1Btn().addActionListener(this);
        ui.getB2Btn().addActionListener(this);
        ui.getResChooser().addActionListener(this);
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

    public MainGUI getUi() {
        return ui;
    }

    public void setUi(MainGUI ui) {
        this.ui = ui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() instanceof JComponent) {
                JComponent component = (JComponent) e.getSource();

                if (component instanceof JRadioButton) {
                    JRadioButton btn = (JRadioButton) component;
                    JRadioButton otherBtn;

                    if (!btn.getActionCommand().equals(previousCmd) && !btn.getActionCommand().equals(model.getSelectedGame().getName())) {

                        String btmTxt = "Lord of the Rings - The Battle for Middle-Earth";


                        if (btn.getActionCommand().equals(ui.getB1Btn().getActionCommand())) {
                            otherBtn = ui.getB2Btn();

                            if (!ui.getB1Btn().isSelected()) {
                                ui.getB1Btn().setSelected(true);
                                ui.getB2Btn().setSelected(false);
                                model.setSelectedGame(Game.BFME1);
                            }

                            ui.getLotrTitleTxt1().setText(btmTxt);
                        } else {
                            otherBtn = ui.getB1Btn();

                            if (!ui.getB2Btn().isSelected()) {
                                ui.getB2Btn().setSelected(true);
                                ui.getB1Btn().setSelected(false);
                                model.setSelectedGame(Game.BFME2);
                            }

                            String newTxt = btmTxt.concat(" II + Rise of the Witch King");
                            ui.getLotrTitleTxt1().setText(newTxt);
                        }

                        if (!btn.isSelected()) {
                            btn.setSelected(true);
                            otherBtn.setSelected(false);
                        }

                        try {
                            ui.getBkgdImgPanel().fireImageShouldChange();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ui, ex.getMessage());
                        }


                        System.out.println("Previous command: \"".concat(previousCmd).concat("\""));
                        System.out.println("Current command: \"".concat(e.getActionCommand()).concat("\""));
                    } else {
                        if (!btn.isSelected()) {

                            if (btn.getActionCommand().equals(ui.getB1Btn().getActionCommand())) {
                                ui.getB2Btn().setSelected(false);
                            } else {
                                ui.getB1Btn().setSelected(false);
                            }

                            btn.setSelected(true);
                        }
                    }
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
                        JOptionPane.showMessageDialog(ui, ex.getMessage());
                    }

                    if (xRes != null && yRes != null) {
                        resolution = new ResObject(xVal, yVal);
                    }

                    if (resolution != null) {
                        JOptionPane.showMessageDialog(ui, "Resolution: \"".concat(resolution.getDescription()).concat("\""));
                    }

                    // TODO write to user info
                }
            }


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ui.getContentPane(), ex.getMessage());
        }

        previousCmd = e.getActionCommand();
    }
}
