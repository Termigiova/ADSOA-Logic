package main.Sockets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class example extends Thread {

    public static void main(String... args) throws IOException {
        ServerListener server = new ServerListener();
        server.start();

        Socket socketToServer = new Socket("localhost", 15000);
        ObjectOutputStream outStream = new ObjectOutputStream(socketToServer.getOutputStream());

        for (int i=1; i<10; i++) {
            try {
                Thread.sleep((long) (Math.random()*3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Sending object to server ...");
            outStream.writeObject("test message #"+i);
        }
        System.exit(0);

    }

}
