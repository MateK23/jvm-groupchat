package com.matek.groupchat.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerRunnable implements Runnable {
    private Socket client;
    private int clientID;
    private Server server = null;
    private final boolean running = true;

    ServerRunnable(Socket client, int clientID, Server server) {
        this.client = client;
        this.clientID = clientID;
        this.server = server;
    }

    @Override
    public void run() {

        System.out.println("ID - " + clientID + " Add - " + client.getInetAddress().

                getHostName());

        try {
            while (true) {
                System.out.println("Reading data ...");
                ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                String str = (String) objectInputStream.readObject();
                // System.out.println("Client " + clientID + " says : " + str);
                // ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                server.addClientToHashMap(client, clientID);
                server.broadcast(str);
            }
        }
        catch (SocketException e){
            server.removeClientFromHashMap(client, clientID);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }


    }
}
