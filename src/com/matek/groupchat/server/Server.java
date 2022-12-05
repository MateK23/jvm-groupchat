package com.matek.groupchat.server;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    int port = 9011;
    ServerSocket serverSocket;
    Socket socket = null;
    ObjectInputStream objectInputStream;
    String str;
    int clientId = 0;
    Thread serverThread = null;


    public Server(int port){
        this.port = port;

        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Server on!");

            while(true){
                socket = serverSocket.accept();
                ServerRunnable serverRunnable = new ServerRunnable(socket, clientId);
                serverThread = new Thread(serverRunnable);
                serverThread.start();
                System.out.println("Connected!");
                clientId++;
            }
        }catch(Exception e) {
            System.out.println("Error: " + e);
        }

        if (serverThread != null) serverThread.start();
    }

    public void ServerReceive(){
        
    }
}
