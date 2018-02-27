/**
 * <p></p>
 *
 * @author Tyler Hostager
 * @version ${build.number}
 * @since 2/26/18
 */
public interface SharedApplicationObjects {

    Log LOG = new Log();

    static void LOG(Object...args) {
        Log.d(args);
    }

    static void ERR(Object...args) {
        Log.e(args);
    }
}
