package ServerPackage;

import OperationSerializable.Operation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static void main() {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente la connexion d'un client ");

            Socket socket = serverSocket.accept();
            System.out.println("un client est connecte");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            try {
                while (true) {
                    Operation operation=(Operation) in.readObject();
                    if (operation == null) {
                        System.out.println("fin de la communication");
                        break;
                    }

                    double a = operation.getO1();
                    char op = operation.getOp();
                    double b = operation.getO2();

                    Object reponse;
                    switch (op){
                        case'+': reponse=a+b;break;
                        case'-': reponse=a-b;break;
                        case'*': reponse=a*b;break;
                        case'/':
                            if (b==0) reponse="division par 0";
                            else reponse=a/b;
                            break;
                        default:reponse="operateur invalide :"+op;
                    }
                    System.out.println("donnees :"+a+op+b+"=>reponse :"+reponse);
                    out.writeObject(reponse);
                    out.flush();
                }
            } catch (EOFException e) {
                System.out.println("client deconnecte");
            } catch (ClassNotFoundException e){
                e.printStackTrace();
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