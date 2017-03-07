import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by p1307887 on 06/03/2017.
 */
public class ServeurPOP3 {

    public int etat;

    public static void main(String[] args) throws IOException {
        ServeurConnexion cnx = new ServeurConnexion();
        cnx.connexion();

    }
}
