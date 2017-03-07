import java.io.*;
import java.net.Socket;

/**
 * Created by p1307887 on 06/03/2017.
 */
public class ServeurCommunication {
    private Socket conn_cli;
    private int ID;

        public ServeurCommunication(Socket conn_cli, int ID) {
            this.conn_cli = conn_cli;
            this.ID = ID;
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

            try {
                request = in.readLine();
                System.out.println(request);
            } catch (IOException var15) {
                System.out.println("3");
            }
            if(request.contains("USER")) {
                System.out.println(request.substring(request.indexOf(" ")));
            }

            /*System.out.println(request);
            Object b = null;
            if(request.contains("GET")) {
                String file = "index.html";
                if(request.length() > 14) {
                    file = request.substring(request.indexOf("/") + 1, request.indexOf(" ", request.indexOf("/")));
                }

                String path = new String(file);
                File fic = new File(path);
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                DataInputStream dis = null;
                byte[] buffer = new byte[512];

                try {
                    fis = new FileInputStream(fic);
                    out.write("HTTP/1.0 200 OK\r\n".getBytes());
                } catch (FileNotFoundException var14) {
                    path = "404.html";
                    fic = new File(path);
                    fis = new FileInputStream(fic);
                    out.write("HTTP/1.0 404 Not Found\r\n".getBytes());
                }

                bis = new BufferedInputStream(fis);
                dis = new DataInputStream(bis);
                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n".getBytes());
                out.write("Server: Apache/0.8.4\r\n".getBytes());
                if(path.contains(".html")) {
                    out.write("Content-Type: text.html\r\n".getBytes());
                } else {
                    out.write("Content-Type: image\r\n".getBytes());
                }

                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n".getBytes());
                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n".getBytes());
                out.write("\r\n".getBytes());

                for(int nbByte = dis.read(buffer, 0, 512); nbByte >= 0; nbByte = dis.read(buffer, 0, 512)) {
                    out.write(buffer, 0, nbByte);
                }
            } else {
                out.write("HTTP/1.0 200 OK\r\n".getBytes());
            }*/

            out.close();
            in.close();
            this.conn_cli.close();
        }
    }

