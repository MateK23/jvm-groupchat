package com.matek.groupchat.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    int port = 9011;

    public static void main(String[] args) {

    }

    public Client(int port){
        this.port = port;
        Socket socket; // კლიენტი
        ObjectOutputStream objectOutputStream = null; // ნაკადი
        ObjectInputStream objectInputStream = null;
        boolean isStart = true;

        try {
            // კლიენტის შექმნა და კავშირის დამყარება
            socket = new Socket("localhost", port);
            System.out.println("Connecting...");

            while (isStart) {
                // შეტყობინების გაგზავნა
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                System.out.print("Write In chat: ");
                Scanner scan = new Scanner(System.in);
                String msg = scan.next();
                objectOutputStream.writeObject(msg);
                // System.out.println("Message Sent");
                objectInputStream = new ObjectInputStream(socket.getInputStream());

                String str = (String) objectInputStream.readObject();
                System.out.println(str);


            }
            // ნაკადის და სოკეტის დახურვა
            objectOutputStream.close();
            socket.close();
            System.out.println("Client exit");

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
