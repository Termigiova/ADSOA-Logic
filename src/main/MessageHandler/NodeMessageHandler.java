package main.MessageHandler;

import main.Entity.Entity;
import main.Entity.Node.Node;
import main.Enum.EnumType;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

import java.io.IOException;
import java.net.Socket;

public class NodeMessageHandler extends AbstractMessageHandler {

    private Node node;
    private String message;
    private String response;
    private JSONMessage jsonMessage;

    public NodeMessageHandler(Socket socket, Node node) {
        this.node = node;
        entity = new Entity();
        jsonMessage = new JSONMessage();

        initializeEntityValues(socket);
        this.start();
    }

    private void initializeEntityValues(Socket socket) {
        Linker linker = new Linker(socket);
        entity.setLinker(linker);
        entity.setType(EnumType.NODE);
    }

    public void processMessage(String message) throws IOException {
        if(message.contains("\"entity\":\"node\"")) {
            Integer portNumber = jsonMessage.readJSONPortMessage(message);
            Integer type = jsonMessage.readJSONTypeMessage(message);
            node.createAndAddNodeFromPort(portNumber, type);
            System.out.println(message);
        } else if (message.contains("\"entity\":\"interface\"")) {
            System.out.println("Adding interface from: " + message);
            Integer type = jsonMessage.readJSONTypeMessage(message);
            entity.setType(type);
        } else if (message.contains("interface")) {
            message = message.replace("interface", "node");
            System.out.println("Interface: " + message);
            node.sendMessageToConnectedLinkers(message);
        } else if (message.contains("node")){
            message = message.replace("node", "");
            System.out.println("Node: " + message);
            node.sendMessageToNonNodeLinkers(message);
        }
    }

}

