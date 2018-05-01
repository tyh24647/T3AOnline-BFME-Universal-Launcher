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
public class ViewController implements ActionListener, ISharedApplicationObjects {
    private T3ALauncherModel model;
    private MainGUI view;
    private String previousCmd;
    private JRadioButton previousBtn, currentBtn;
    private boolean isDebug;
    private boolean hasSeenResPrompt;

    public static ViewController CURRENT_VC;
    public static MainGUI CURRENT_UI;
    public static T3ALauncherModel CURRENT_MODEL;

    //region - CONSTRUCTORS
    /**
     * Default constructor
     *
     * Only use this if you need static access to something or aboslutely must create a
     * new empty instance of a controller. Otherwise, eternal sadness and other bad things
     * will occur.
     */
    public ViewController() {
        /* Do nothing... woohoo! */
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

        ViewController.CURRENT_UI = this.view;
        ViewController.CURRENT_VC = this;
        ViewController.CURRENT_MODEL = this.model;
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

        ViewController.CURRENT_MODEL = this.model;
        ViewController.CURRENT_VC = this;
        ViewController.CURRENT_UI = this.view;

        initUI();
        addActionListeners();
    }
    //endregion

    /**
     *
     */
    public void initUI() {
        initUI(true);
    }

    /**
     *
     * @param isDebug
     */
    public void initUI(boolean isDebug) {
        ViewController.CURRENT_VC = this;
        this.isDebug = isDebug;
        try {
            view.initUI();
            Runnable prompt = this::promptResolutionDetectionDialog;
            Thread promptThread = new Thread(prompt);
            promptThread.start();
            while (true) { if (!promptThread.isAlive()) { view.launchMainWindow(); break; } }
        } catch (Exception e) {
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

    /**  */
    public void setModel(T3ALauncherModel model) {
        this.model = model;
    }

    public MainGUI getView() {
        return view;
    }

    /**  */
    public void setView(MainGUI view) {
        this.view = view;
    }

    /**  */
    private void promptResolutionDetectionDialog() {
        try {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            Double x = dimension.getWidth();
            Double y = dimension.getHeight();
            Integer xInt = Math.round(x.intValue());
            Integer yInt = Math.round(y.intValue());

            if (!hasSeenResPrompt) {
                Runnable dialogTask = () -> JOptionPane.showMessageDialog(
                        new JFrame() {
                            @Override
                            public int getDefaultCloseOperation() {
                                view.repaint();
                                return DISPOSE_ON_CLOSE;
                            }
                        },

                        "RESOLUTION AUTO-DETECTION:\n\nDetected Resolution: \""
                                .concat(String.valueOf(xInt))
                                .concat("x")
                                .concat(String.valueOf(yInt))
                                .concat("\"\nSetting value as default...")
                );

                hasSeenResPrompt = true;
                Thread dThread = new Thread(dialogTask);
                dThread.start();
            }

            Runnable detectResolution;
            if (view != null && view.getResChooser() != null && view.getResChooser().getItemCount() > 0) {
                detectResolution = () -> {
                    if (view.getResChooser().getItemCount() > 0) {
                        for (int i = 0; i < view.getResChooser().getItemCount() - 1; i++) {
                            if (((String) view.getResChooser().getItemAt(i)).substring(0, 2).contains(x.toString().substring(0, 2))) {
                                view.getResChooser().setSelectedItem(view.getResChooser().getItemAt(i));
                                view.getResChooser().setSelectedIndex(i);
                            }
                        }
                    }
                };

                detectResolution.run();
                view.repaint();
            } else {
                view.initUI();
                promptResolutionDetectionDialog();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
            e.printStackTrace();
        }
    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() instanceof JComponent) {
                JComponent component = (JComponent) e.getSource();
                if (component instanceof JRadioButton) {
                    JRadioButton btn = (JRadioButton) component;
                    JRadioButton otherBtn = previousBtn == null ? view.getB1Btn() : previousBtn;
                    currentBtn = btn;
                    if (!btn.getActionCommand().equals(previousCmd) && !btn.getActionCommand().equals(model.getSelectedGame().getName())) {
                        String btmTxt = "Lord of the Rings - The Battle for Middle-Earth";
                        view.getLotrTitleTxt1().setText(btmTxt);
                        if (!currentBtn.isSelected()) {
                            currentBtn.setSelected(true);
                            model.setSelectedGame(
                                    currentBtn.getActionCommand().equals("BFME2") ? Game.BFME2 : (
                                            currentBtn.getActionCommand().equals("BFME1") ? Game.BFME1 : Game.ROTWK
                                    )
                            );
                        }

                        String selectedGame = model.getSelectedGame().getName();
                        String newTxt = btmTxt.concat(
                                selectedGame.contains("ROTWK") ? " Rise of the Witch King" : (
                                        selectedGame.contains("2") ? " II" : ""
                                )
                        );

                        view.getLotrTitleTxt1().setText(newTxt);
                    } else {
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

                    String cmd1 = otherBtn.getActionCommand();
                    String cmd2 = btn.getActionCommand();
                    view.getBkgdImgPanel().chgImgSelectionsForActionCommands(cmd1, cmd2);
                    previousBtn = btn;
                    currentBtn = null;
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
                }
            }
        } catch (Exception ex) {
            System.out.println("\n\nERROR: " + ex.getMessage());
            JOptionPane.showMessageDialog(view.getContentPane(), ex.getMessage());
        }

        if (e.getSource() instanceof JRadioButton) {
            previousCmd = e.getActionCommand();
        }
    }
}
