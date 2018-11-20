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
        } catch (Exception e) {
//            System.out.println("Error: " + e);
        }
    }

    public void sendMessage(String message) {
        try {
            System.out.println("Sending message: " + message);
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage() throws IOException, ClassNotFoundException {
        String message = (String) in.readObject();
        System.out.println("Receiving message: " + message);
        return message;
    }

    public Integer getPort() { return socket.getPort(); }

    public Socket getSocket() {
        return socket;
    }

}
