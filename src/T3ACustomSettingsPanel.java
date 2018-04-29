import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 4/28/18
 */
public class T3ACustomSettingsPanel extends JPanel implements ActionListener, ISharedApplicationObjects {
    private Settings settings;
    public JRadioButton advancedBtn;
    public JButton resetBtn, launchAppBtn, applyChangesBtn, cancelBtn;

    /**
     *
     */
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

    private void initSettingsView() throws Exception {
        var mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        //setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        setLayout(new BorderLayout(10, 10));


        mainPanel.add(advancedBtn);
        mainPanel.add(new JLabel());
        mainPanel.add(resetBtn);
        mainPanel.setOpaque(false);
        mainPanel.setForeground(Color.WHITE);

        var southPanel = new JPanel(new FlowLayout());

        URL url = new URL("https://info.server.cnc-online.net/");
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
        Object root =  parser.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        var object = (JSONObject) root;

        var bfme1Data = (JSONObject) ((JSONObject) object.get("bfme")).get("users");
        var bfme2Data = (JSONObject) ((JSONObject) object.get("bfme2")).get("users");
        var rotwkData = (JSONObject) ((JSONObject) object.get("rotwk")).get("users");

        var b1Ct = bfme1Data.size();
        var b2Ct = bfme2Data.size();
        var rkCt = rotwkData.size();

        String content = object.toString();
            /*
            TODO use the "content" variable above to view the raw JSON if necessary
             */

        Log.d("Data downloaded successfully.");
        var totalUsers = (b1Ct + b2Ct + rkCt);

        southPanel.add(new JLabel("TOTAL USERS: " + totalUsers));
        Log.d();
        Log.d("SERVER INFO:\nTotal users online: " + totalUsers);

        southPanel.add(new JLabel("BFME1: " + b1Ct));
        Log.d("BFME1: " + b1Ct);

        southPanel.add(new JLabel("BFME2: " + b2Ct));
        Log.d("BFME2: " + b2Ct);

        southPanel.add(new JLabel("ROTWK: " + rkCt));
        Log.d("ROTWK: " + rkCt);
        Log.d();

        southPanel.setForeground(Color.WHITE);
        southPanel.setOpaque(false);

        add(mainPanel, BorderLayout.CENTER);

        setOpaque(false);
        requestFocusInWindow();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }

        return sb.toString();
    }


    public boolean hasSavedSettings() {
        return new File(""
                + /* TODO SAVE SETTINGS TO FILE ON USER MACHINE TO PRESERVE DATA; USE PATH HERE!!! */ ""
        ).exists();
    }

    public Settings loadSavedSettings() {

        var savedSettings = new Settings();

        /*

        TODO        LOAD DATA FROM SAVED SETTINGS OBJECT AND ASSIGN TO A SETTINGS OBJECT!!!!!!


         */


        return savedSettings;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
                radioBtn.setSelected(!radioBtn.isSelected());
                radioBtn.setText(radioBtn.isSelected() ? "Show Advanced Settings" : "Hide Advanced Settings");
                validate();
                repaint();
            }
        } catch (Exception ex) {
            System.out.println("\n\nERROR: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public Settings getCurrentSettings() {
        return settings == null ? new DefaultSettings() : this.settings;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
