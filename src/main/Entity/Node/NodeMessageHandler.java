package main.Entity.Node;

import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

import java.io.IOException;
import java.net.Socket;

public class NodeMessageHandler extends Thread {

    private Linker linker;
    private Node node;
    private String message;
    private String response;
    private JSONMessage jsonMessage;

    NodeMessageHandler(Socket socket, Node node) {
        this.node = node;
        linker = new Linker(socket);
        jsonMessage = new JSONMessage();
    }

    @Override
    public void run() {
        while (true) {
            try {
                message = linker.readMessage();
                processMessage(message);
            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }

    private void processMessage(String message) throws IOException {
        if(message.contains("portNumber")) {
            Integer portNumber = jsonMessage.readJSONPortMessage(message);
            node.connectToPort(portNumber);
        }
        System.out.println(message);
    }

    public Linker getLinker() {
        return linker;
    }

}
