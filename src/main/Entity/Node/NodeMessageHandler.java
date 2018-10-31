package main.Entity.Node;

import main.Sockets.Linker;

import java.io.IOException;
import java.net.Socket;

public class NodeMessageHandler extends Thread {

    private Linker linker;
    private Node node;
    private String message;
    private String response;

    public NodeMessageHandler(Socket socket, Node node) {
        this.node = node;
        linker = new Linker(socket);
    }

    @Override
    public void run() {
        while (true) {
            try {
                message = linker.readMessage();
                response = processMessage(message);
                node.readInput(message);
            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }

    private String processMessage(String message) {
        return message;
    }

    public Linker getLinker() {
        return linker;
    }

}
