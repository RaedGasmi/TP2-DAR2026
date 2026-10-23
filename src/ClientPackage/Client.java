package ClientPackage;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static void main() {
        try {
            String host = "localhost";
            int port = 1234;
            Socket socket = new Socket();
            System.out.println("Je suis un client pas encore connecte…");

            socket.connect(new InetSocketAddress(host, port));
            System.out.println("je suis un client connecte");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("Entrez une operation ou 0 pour quitter: ");
                String line = clavier.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.equals("0")) {
                    System.out.println("client deconnecte");
                    out.newLine();
                    out.flush();
                    break;
                }
                String[] parts = line.replace(" ","").splitWithDelimiters("[-+*/]",0);

                if(parts.length!=3){
                    System.out.println("syntaxe incorrect");
                    continue;
                }
                try{
                    Double.parseDouble(parts[0]);
                    Double.parseDouble(parts[2]);
                } catch (NumberFormatException e) {
                    System.out.println("n'est pas un nombre");
                    continue;
                }
                for(String p:parts){
                    out.write(p);
                    out.newLine();
                }
                out.flush();

                String resultat = in.readLine();
                if(resultat == null){
                    System.out.println("serveur deconnecte");
                    break;
                }
                System.out.println("resultat :"+parts[0]+parts[1]+parts[2]+"="+resultat);
            }
            clavier.close();
            in.close();
            out.close();
            socket.close();
            System.out.println("Socket se ferme");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
