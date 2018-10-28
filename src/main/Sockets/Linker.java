package main.Sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Linker {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Linker(Socket socket) {
        try {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to port: " + socket.getLocalPort());
        } catch (Exception e) {
//            System.out.println("Error: " + e);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object readMessage() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

}
