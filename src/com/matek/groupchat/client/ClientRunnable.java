package com.matek.groupchat.client;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientRunnable implements Runnable{
    ObjectInputStream objectInputStream;
    Socket socket;
    ClientRunnable(Socket socket){

    }

    @Override
    public void run() {
        try{
            while(true){
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                String message = (String) objectInputStream.readObject();
                System.out.println(message);
            }
        }
        catch (SocketException e){
            System.out.println("Disconnected");
        }catch (Exception e){
            System.out.println("Error");
        }

    }
}
