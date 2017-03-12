package Serveur;

import java.io.*;
import java.net.Socket;


/**
 * Created by p1307887 on 06/03/2017.
 */
public class ServeurCommunication extends Thread{
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

        @Override
        public void start(){
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


            try {
                out.write("+OK POP3 serveur ready\r\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(connexion) {
                try {
                    request = in.readLine();
                    System.out.println(request);
                } catch (IOException var15) {
                    System.out.println("request null");
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
                                try {
                                    out.write("+OK user exist\r\n".getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }else if (request.contains("APOP")){
                            System.out.println(request.substring(request.indexOf(" "),request.lastIndexOf(" ")));
                            System.out.println(request.substring(request.lastIndexOf(" ")));
                            this.user = request.substring(request.indexOf(" ")+1,request.lastIndexOf(" "));
                            this.pass = request.substring(request.indexOf(" "));
                            try {
                                out.write(("+OK " +Util.countMessage("Message/"+user+".txt")+" "+Util.getFileSize("Message/"+user+".txt") + "\r\n").getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            etat = etatPossible.TRANSACTION;
                        }
                        break;
                    case AUTHENTIFICATION:
                        if (request.contains("PASS") && authentificationFlag == 1) {
                            System.out.println(request.substring(request.indexOf(" "),request.lastIndexOf(" ")));
                            this.pass = request.substring(request.lastIndexOf(" "));
                            authentificationFlag = 2;
                            try {
                                out.write(("+OK" +Util.countMessage("Message/"+user+".txt")+" "+Util.getFileSize("Message/"+user+".txt")+"\r\n").getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            etat = etatPossible.TRANSACTION;
                        }
                        break;
                    case TRANSACTION:
                        if (request.contains("STAT")){
                            try {
                                out.write(("+OK "+Util.countMessage("Message/"+user+".txt")+" "+Util.getFileSize("Message/"+user+".txt")+"\r\n").getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else if(request.contains("QUIT")){
                            miseajour();
                            etat=etatPossible.FERME;
                        }
                        break;
                }
            }


            try {
                out.close();

            in.close();
            this.conn_cli.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void getUserInfo(String user) {
        Util.countMessage("Message/"+user);
    }


    private void miseajour() {
        this.connexion=false;
    }
}

