import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Authenticator;
import org.json.HTTP;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.parser.JSONParser;

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

    public T3ACustomSettingsPanel() {
        this.settings = hasSavedSettings() ? loadSavedSettings() : new DefaultSettings();

        try {
            initComponents();
            initSettingsView();

            setVisible(true);
            this.setPreferredSize(
                    new Dimension(
                            800,
                            600
                    )
            );
        } catch (Exception e) {
            System.out.println(e);
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

        /*
        try {
            var serverUrl = new URL("https://info.server.cnc-online.net");
            Object obj = new JSONParser().parse(new FileReader(new File(serverUrl.toURI())));
            var jsonObj = (JSONObject) obj;

            var bfme1Data = (Map) jsonObj.get("bfme");
            var bfme2Data = (Map) jsonObj.get("bfme2");
            var rotwkData = (Map) jsonObj.get("rotwk");

            Iterator<Map.Entry> itr = bfme1Data.entrySet().iterator();
            Iterator<Map.Entry> itr2 = bfme2Data.entrySet().iterator();
            Iterator<Map.Entry> itr3 = rotwkData.entrySet().iterator();

            while (itr.hasNext()) {
                Map.Entry pair = itr.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
                if (pair.getKey().toString().equals("numRealPlayers")) {
                    southPanel.add(new JLabel("<< Number of online players >>"));
                    southPanel.add(new JLabel("BFME1: " + pair.getValue().toString()));
                }
            }

            while (itr2.hasNext()) {
                Map.Entry pair = itr2.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
                if (pair.getKey().toString().equals("numRealPlayers")) {
                    southPanel.add(new JLabel("BFME2: " + pair.getValue().toString()));
                }
            }

            while (itr3.hasNext()) {
                Map.Entry pair = itr2.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
                if (pair.getKey().toString().equals("lobbies")) {
                    var tmp = (JSONArray) pair.getValue();

                    int i = 0;
                    for (Object o : tmp) {
                        if (o.toString().equals("playing")) {
                            southPanel.add(new JLabel("ROTWK: " + tmp.toArray()[i].toString()));
                        }
                    }

                    southPanel.add(new JLabel("ROTWK: " + pair.getValue().toString()));
                }
            }

        } catch (URISyntaxException | IOException | ParseException e) {
            LOG.err(e);
*/

            URL url = new URL("https://info.server.cnc-online.net/");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.setRequestProperty("Accept-Encoding", " br, gzip, deflate");
            request.setRequestProperty("Connection", "keep-alive");
            request.connect();


            // Convert to a JSON object to print data
            JSONParser parser = new JSONParser(); //from gson
            Object root =  parser.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JSONObject object = (JSONObject) root;

            String content = object.toJSONString();

                var bfme1Data = (Map) object.get("bfme");
                var bfme2Data = (Map) object.get("bfme2");
                var rotwkData = (Map) object.get("rotwk");

                Iterator<Map.Entry> itr = bfme1Data.entrySet().iterator();
                Iterator<Map.Entry> itr2 = bfme2Data.entrySet().iterator();
                Iterator<Map.Entry> itr3 = rotwkData.entrySet().iterator();

                while (itr.hasNext()) {
                    Map.Entry pair = itr.next();
                    System.out.println(pair.getKey() + " : " + pair.getValue());
                    if (pair.getKey().toString().equals("numplayers")) {
                        southPanel.add(new JLabel("<< Number of online players >>"));
                        southPanel.add(new JLabel("BFME1: " + pair.getValue().toString()));
                        break;
                    }
                }

                while (itr2.hasNext()) {
                    Map.Entry pair = itr2.next();
                    System.out.println(pair.getKey() + " : " + pair.getValue());
                    if (pair.getKey().toString().equals("numplayers")) {
                        southPanel.add(new JLabel("BFME2: " + pair.getValue().toString()));
                        break;
                    }
                }

                while (itr3.hasNext()) {
                    Map.Entry pair = itr2.next();
                    System.out.println(pair.getKey() + " : " + pair.getValue());
                    if (pair.getKey().toString().equals("lobbies")) {
                        var tmp = (JSONArray) pair.getValue();

                        int i = 0;
                        for (Object o : tmp) {
                            if (o.toString().equals("playing")) {
                                southPanel.add(new JLabel("ROTWK: " + tmp.toArray()[i].toString()));
                                break;
                            }
                        }

                        southPanel.add(new JLabel("ROTWK: " + pair.getValue().toString()));
                    }
            }

            southPanel.setForeground(Color.WHITE);
                southPanel.setOpaque(false);

//        }

        add(mainPanel, BorderLayout.CENTER);

        setOpaque(false);
        requestFocusInWindow();
    }

    /*
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    */

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
