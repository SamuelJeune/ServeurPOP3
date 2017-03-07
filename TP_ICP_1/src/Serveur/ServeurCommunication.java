package Serveur;

import java.io.*;
import java.net.Socket;


/**
 * Created by p1307887 on 06/03/2017.
 */
public class ServeurCommunication {
    private Socket conn_cli;
    private int ID;
    private enum etatPossible {FERME, AUTORISATION, AUTHENTIFICATION, TRANSACTION};
    private etatPossible etat;
    private int authentificationFlag;
    private String user;
    private String pass;
    private boolean connexion;
    private int messageNb;

        public ServeurCommunication(Socket conn_cli, int ID) {
            this.conn_cli = conn_cli;
            this.ID = ID;
            this.authentificationFlag=0;
            this.etat=etatPossible.AUTORISATION;
            this.connexion=true;
        }

        void start() throws IOException {
            BufferedReader in = null;

            try {
                in = new BufferedReader(new InputStreamReader(this.conn_cli.getInputStream()));
            } catch (IOException var17) {
                System.out.println("1");
            }

            OutputStream out = null;

            try {
                out = this.conn_cli.getOutputStream();
            } catch (IOException var16) {
                System.out.println("2");
            }

            String request = null;

            out.write("+OK POP3 serveur ready\r\n".getBytes());

            while(connexion) {
                try {
                    request = in.readLine();
                    System.out.println(request);
                } catch (IOException var15) {
                }
                switch (etat) {
                    case FERME:
                        break;
                    case AUTORISATION:
                        if (request.contains("USER") && authentificationFlag == 0) {
                            System.out.println(request.substring(request.indexOf(" ")));
                            if(ServeurPOP3.username.contains(request.substring(request.indexOf(" ")+1)+".txt")){
                                this.user = request.substring(request.indexOf(" "));
                                authentificationFlag = 1;
                                etat = etatPossible.AUTHENTIFICATION;
                                out.write("+OK user exist\r\n".getBytes());
                            }

                        }else if (request.contains("APOP")){
                            System.out.println(request.substring(request.indexOf(" "),request.lastIndexOf(" ")));
                            System.out.println(request.substring(request.lastIndexOf(" ")));
                            this.user = request.substring(request.indexOf(" "),request.lastIndexOf(" "));
                            this.pass = request.substring(request.indexOf(" "));
                            out.write(("+OK "+user+" has 2 messages\r\n").getBytes());
                            etat = etatPossible.TRANSACTION;
                        }
                        break;
                    case AUTHENTIFICATION:
                        if (request.contains("PASS") && authentificationFlag == 1) {
                            System.out.println(request.substring(request.indexOf(" "),request.lastIndexOf(" ")));
                            this.pass = request.substring(request.lastIndexOf(" "));
                            authentificationFlag = 2;
                            out.write("+OK 1 320\r\n".getBytes());
                            etat = etatPossible.TRANSACTION;
                        }
                        break;
                    case TRANSACTION:
                        if (request.contains("STAT")){
                            out.write("+OK 2 320\r\n".getBytes());
                        }else if(request.contains("QUIT")){
                            miseajour();
                            etat=etatPossible.FERME;
                        }
                        break;
                }
            }


            out.close();
            in.close();
            this.conn_cli.close();
        }

    private void miseajour() {
        this.connexion=false;
    }
}

