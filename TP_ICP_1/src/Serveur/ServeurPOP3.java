package Serveur;

import Serveur.ServeurConnexion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by p1307887 on 06/03/2017.
 */
public class ServeurPOP3 {

    public static List<User> userlist = new ArrayList<>();
    public static List<String> username = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        userlist.add(new User("rom","123"));
        username.addAll(userlist.stream().map(User::getName).collect(Collectors.toList()));


        ServeurConnexion cnx = new ServeurConnexion();
        cnx.connexion();

    }
}
