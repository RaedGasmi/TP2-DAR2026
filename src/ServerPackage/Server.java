package ServerPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static String calculer(String a, String op, String b){
       double i,j;
       try {
           i=Double.parseDouble(a);
           j=Double.parseDouble(b);
       } catch (NumberFormatException e) {
           return "operation invalide";
       }
       double r;
       switch(op){
           case"+":r=i+j;break;
           case"-":r=i-j;break;
           case"*":r=i*j;break;
           case"/":
               if (j==0) return "division par 0";
               r=i/j;
               break;
           default:
               return "operation invalide";
       }
       return String.valueOf(r);
    }
    static void main() {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente la connexion d'un client ");
            Socket socket = serverSocket.accept();
            System.out.println("un client est connecte");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(true) {
                String a = in.readLine();
                if (a == null) {
                    System.out.println("client deconnecte");
                    break;
                }
                if (a.equals("0")) {
                    System.out.println("fin");
                    break;
                }
                String op=in.readLine();
                String b= in.readLine();
                if (op==null||b==null){
                    System.out.println("operation invalide");
                    break;
                }
                String resultat= calculer(a,op,b);
                System.out.println("donnes :"+a+op+b+" =>resultat :"+resultat);

                out.write(resultat);
                out.newLine();
                out.flush();
            }
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
            System.out.println("Sockets fermes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}