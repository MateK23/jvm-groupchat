package com.matek.groupchat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

public class Server {
    int port = 9011;
    ServerSocket serverSocket;
    Socket socket = null;
    ObjectInputStream objectInputStream;
    String str;
    int clientId = 0;
    Thread serverThread = null;
    Socket[] clientPool = null;
    HashMap<Integer, Socket> clientHashMap = new HashMap<Integer, Socket>();

    public Server(int port) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Server on!");

            while (true) {
                socket = serverSocket.accept();
                ServerRunnable serverRunnable = new ServerRunnable(socket, clientId, this);
                serverThread = new Thread(serverRunnable);
                serverThread.start();
                System.out.println("Connected!");
                clientId++;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        if (serverThread != null) serverThread.start();
    }

    public void addClientToHashMap(Socket clientSocket, int clientId) {
        clientHashMap.put(clientId, clientSocket);
    }

    public void removeClientFromHashMap(Socket clientSocket, int clientId) {
        clientHashMap.remove(clientId, clientSocket);
    }

    public void broadcast(String message) throws IOException {
        for (int i = 0; i < clientHashMap.size(); i++) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientHashMap.get(i).getOutputStream());
            objectOutputStream.writeObject(message);
        }
    }
}

