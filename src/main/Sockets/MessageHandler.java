package main.Sockets;

import main.Entity.Node.Node;

import java.io.IOException;
import java.net.Socket;

public class MessageHandler extends Thread{

    private Linker linker;
    private Node node;

    public MessageHandler(Socket socket, Node node) throws IOException {
        this.node = node;
        linker = new Linker(socket);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object o = linker.readMessage();
                node.readInput("Read object: " + o);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Linker getLinker() {
        return linker;
    }
}