package Serveur;

import java.io.*;

/**
 * Created by p1307887 on 07/03/2017.
 */
public class Util {

        public static void readFile(String file){

            String chaine="";
            String fichier =file;

            //lecture du fichier texte
            try{
                InputStream ips=new FileInputStream(fichier);
                InputStreamReader ipsr=new InputStreamReader(ips);
                BufferedReader br=new BufferedReader(ipsr);
                String ligne;
                while ((ligne=br.readLine())!=null){
                    System.out.println(ligne);
                    chaine+=ligne+"\n";
                }
                br.close();
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
}

    public static String[] listerRepertoire(File repertoire){

        String [] listefichiers;
        listefichiers=repertoire.list();
        return listefichiers;
    }
}
