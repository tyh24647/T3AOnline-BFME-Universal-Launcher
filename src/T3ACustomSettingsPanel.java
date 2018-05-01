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



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * <p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 4/28/18
 */
public class T3ACustomSettingsPanel extends JPanel implements ActionListener, ISharedApplicationObjects {
    private Settings settings;
    public JRadioButton advancedBtn;
    public JButton resetBtn, launchAppBtn, applyChangesBtn, cancelBtn;

    /**  */
    public T3ACustomSettingsPanel() {
        this.settings = hasSavedSettings() ? loadSavedSettings() : new DefaultSettings();

        try {
            initComponents();
            initSettingsView();
            setPreferredSize(new Dimension(800, 600));
            setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**  */
    private void initComponents() {
        this.advancedBtn = new JRadioButton("Show Advanced Settings");
        this.advancedBtn.setForeground(Color.WHITE);
        this.advancedBtn.setOpaque(false);
        this.advancedBtn.addActionListener(this);

        this.resetBtn = new JButton("Reset to defaults...");
        this.resetBtn.setOpaque(false);
        this.resetBtn.addActionListener(this);

        this.applyChangesBtn = new JButton("Apply");
        this.applyChangesBtn.addActionListener(this);
        this.cancelBtn = new JButton("Cancel");
        this.cancelBtn.addActionListener(this);
        this.launchAppBtn = new JButton("Launch Application");
        this.launchAppBtn.addActionListener(this);
    }

    /**
     *
     * @throws Exception
     */
    private void initSettingsView() throws Exception {
        //var tmpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        var topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        //setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        setLayout(new BorderLayout(10, 10));

        topPanel.add(advancedBtn);
        topPanel.add(new JLabel());
        topPanel.add(resetBtn);
        topPanel.setOpaque(false);
        topPanel.setForeground(Color.WHITE);


        try {
            URL url = new URL(StringConstants.T3AOnline.SERVER_JSON_DATA_URL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.setRequestProperty("Accept-Encoding", "br, gzip, deflate");
            request.setRequestProperty("Connection", "keep-alive");

            Log.d("Testing internet connectivity...");

            int responseCode = request.getResponseCode();
            if (responseCode == 200) {
                request.connect();
                Log.d("Successfully connected to the server.");
            } else {
                Log.d("No internet connection available.\nSkipping procedure.");
            }

            Log.d("Downloading current server data...");

            // Parse the JSON
            JSONParser parser = new JSONParser(); // from gson
            Object root = parser.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element

            var object = (JSONObject) root;

            var bfme1Data = (JSONObject) ((JSONObject) object.get("bfme")).get("users");
            var bfme2Data = (JSONObject) ((JSONObject) object.get("bfme2")).get("users");
            var rotwkData = (JSONObject) ((JSONObject) object.get("rotwk")).get("users");

            var b1Ct = bfme1Data.size();
            var b2Ct = bfme2Data.size();
            var rkCt = rotwkData.size();

            String content = object.toString();


            // TODO use the "content" variable above to view the raw JSON if necessary


            Log.d("Data downloaded successfully.");
            var totalUsers = (b1Ct + b2Ct + rkCt);


            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/_fonts/anirm___.ttf")));

            //var southPanel = new JPanel(new GridLayout(1, 6));
            var southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 5));
            //southPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 40));

            for (String fontName : ge.getAvailableFontFamilyNames()) {
                if (fontName.toLowerCase().contains("anirm")) {
                    southPanel.setFont(new Font(fontName, Font.BOLD, 16));
                    break;
                }
            }

            var fontStream = new BufferedInputStream(new FileInputStream("src/assets/_fonts/anirm___.ttf"));
            var ttfBase = Font.createFont(Font.TRUETYPE_FONT, fontStream);

            final Font lotrFont = ttfBase.deriveFont(Font.PLAIN, 16);

            southPanel.setForeground(Color.WHITE);
            southPanel.setBackground(Color.BLACK);
            southPanel.setFont(lotrFont);
            southPanel.add(new JLabel("Players Online: " + totalUsers) {
                @Override
                public Color getForeground() {
                    return new Color(215, 186, 147); //return Color.WHITE;
                }

                @Override
                public Color getBackground() {
                    return Color.BLACK;
                }

                @Override
                public Font getFont() {
                    return lotrFont.deriveFont(Font.PLAIN, 12);
                }
            });

            southPanel.add(new JLabel("           "));
            southPanel.add(new JLabel("           "));
            southPanel.add(new JLabel("bfme1: " + b1Ct) {
                @Override
                public Color getForeground() {
                    return new Color(215, 186, 147); //return Color.WHITE;
                }

                @Override
                public Color getBackground() {
                    return Color.BLACK;
                }

                @Override
                public Font getFont() {
                    return lotrFont.deriveFont(Font.PLAIN, 13);
                }
            });
            Log.d("\tBFME1: " + b1Ct);

            southPanel.add(new JLabel("bfme2: " + b2Ct) {
                @Override
                public Color getForeground() {
                    return new Color(215, 186, 147); //return Color.WHITE;
                }

                @Override
                public Color getBackground() {
                    return Color.BLACK;
                }

                @Override
                public Font getFont() {
                    return lotrFont.deriveFont(Font.PLAIN, 13);
                }
            });
            Log.d("\tBFME2: " + b2Ct);

            southPanel.add(new JLabel("rotwk: " + rkCt) {
                @Override
                public Color getForeground() {
                    return new Color(215, 186, 147); //return Color.WHITE;
                }

                @Override
                public Font getFont() {
                    return lotrFont.deriveFont(Font.PLAIN, 13);
                }
            });

            southPanel.setLocation(southPanel.getLocation().x, southPanel.getLocation().y + 20);

            Log.d();
            Log.d("SERVER INFO:");
            Log.d("\tTotal users online: " + totalUsers);
            Log.d("\tROTWK: " + rkCt);
            Log.d();
            Log.d();
            Log.d("CURRENT USERS:");
            Log.d("\tBFME1:");
            var b1Arr = bfme1Data.values().toArray();
            for (var b1Usr : b1Arr) Log.d("\t" + b1Usr + ", ");
            Log.d();
            Log.d("\tBFME2:");
            var b2Arr = bfme2Data.values().toArray();
            for (var b2Usr : b2Arr) Log.d("\t" + b2Usr + ", ");
            Log.d();
            Log.d("\tROTWK:");
            var rkArr = rotwkData.values().toArray();
            for (var rkUsr : rkArr) Log.d("\t" + rkUsr);
            Log.d();

            //southPanel.setPreferredSize(new Dimension(mainPanel.getWidth(),mainPanel.getHeight() / 3));
            southPanel.setOpaque(false);

            add(topPanel, BorderLayout.CENTER);
            //add(southPanel, BorderLayout.SOUTH);
            add(southPanel, BorderLayout.AFTER_LAST_LINE);

            setOpaque(false);
            requestFocusInWindow();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(e.getMessage());
        }
    }

    /**
     *
     *
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }

        return sb.toString();
    }




    /*
    TODO MOVE THIS TO ITS OWN FILE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */

    /**
     *
     * @return
     */
    public boolean hasSavedSettings() {
        return new File(""
                        + /* TODO SAVE SETTINGS TO FILE ON USER MACHINE TO PRESERVE DATA; USE PATH HERE!!! */ ""
        ).exists();
    }

    /**
     *
     * @return
     */
    public Settings loadSavedSettings() {

        var savedSettings = new Settings();

                    /*
                    TODO        LOAD DATA FROM SAVED SETTINGS OBJECT AND ASSIGN TO A SETTINGS OBJECT!!!!!!
                     */

        return savedSettings;
    }

    /*
    TODO MOVE THIS TO ITS OWN FILE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */


    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Runnable thingy = () -> {
            try {
                rest();
                URL url = this.getClass().getResource("assets/hd_1122.gif");
                Image imgFromUrl = ImageIO.read(url).getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
                imgFromUrl.setAccelerationPriority(1);
                Icon gifIcon = new ImageIcon(imgFromUrl);
                JLabel imageLbl = new JLabel(gifIcon);

                imageLbl.setPreferredSize(getSize());
                getRootPane().getContentPane().add(imageLbl, BorderLayout.CENTER);
                var gif = imgFromUrl.getScaledInstance(800, 640, Image.SCALE_REPLICATE);

                imgFromUrl.flush();
                gif.flush();
                getRootPane().getTopLevelAncestor().repaint();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        Thread testThread = new Thread(thingy);
        rest();
        testThread.start();

//        fr.pack();
//        fr.setVisible(true);

        try {
            if (e.getSource() instanceof JButton) {
                var btn = (JButton) e.getSource();

                if (btn.equals(cancelBtn)) {

                    /*
                    TODO Change me!!!!
                     */

                    System.exit(0);
                }
            } else if (e.getSource() instanceof JRadioButton) {
                var radioBtn = (JRadioButton) e.getSource();
                radioBtn.setText(radioBtn.isSelected() ? "Show Advanced Settings" : "Hide Advanced Settings");
                radioBtn.setSelected(!radioBtn.isSelected());

                validate();
                repaint();

                getRootPane().getTopLevelAncestor().repaint();
            }
        } catch (Exception ex) {
            System.out.println("\n\nERROR: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /** Sleepy time!!! */
    private void rest() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     *
     * @return
     */
    public Settings getCurrentSettings() {
        return settings == null ? new DefaultSettings() : this.settings;
    }


    //region - INHERITED METHODS
    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    //endregion
}


