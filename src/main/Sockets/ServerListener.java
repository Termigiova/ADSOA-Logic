package main.Sockets;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {

    private ServerSocket serverSocket;

    ServerListener() throws IOException {
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(15000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Socket socketToClient = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socketToClient);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
