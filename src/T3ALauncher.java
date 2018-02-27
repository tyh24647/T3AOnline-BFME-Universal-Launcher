import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Tyler Hostager
 * @version 1.0.0
 * @since 2/18/18
 */
public class T3ALauncher {
    private static ViewController vc;
    private static T3ALauncherModel model;
    private static MainGUI ui;
    private static boolean isDebug;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {

        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {
            // TODO: Add err messages
        } catch (UnsupportedLookAndFeelException e) {

        }


        initApplication();
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
}
