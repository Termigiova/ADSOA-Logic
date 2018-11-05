package main.MessageHandler;

import main.Entity.Entity;
import main.Entity.Node.Node;
import main.Enum.EnumContentCode;
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
        Integer contentCode = jsonMessage.getContentCode(message);
        Integer type;

        switch(contentCode) {
            case EnumContentCode.INITIAL_NODE_CONF:
                Integer portNumber = jsonMessage.getJSONPortMessage(message);
                type = jsonMessage.getJSONTypeMessage(message);
                node.createAndAddNodeFromPort(portNumber, type);
                System.out.println(message);
                break;
            case EnumContentCode.INITIAL_INTERFACE_CONF:
                System.out.println("Adding interface from: " + message);
                type = jsonMessage.getJSONTypeMessage(message);
                entity.setType(type);
                break;
            case EnumContentCode.SUM:
            case EnumContentCode.SUBSTRACTION:
            case EnumContentCode.MULTIPLICATION:
            case EnumContentCode.DIVISION:
                Integer origin = jsonMessage.getOrigin(message);
                System.out.println("Entity before: " + message);
                message = jsonMessage.updateOriginMessage(EnumType.NODE, message);
                if (origin == EnumType.NODE) {
                    System.out.println("Node: " + message);
                    node.sendMessageToNonNodeLinkers(message);
                } else {
                    System.out.println("Entity after: " + message);
                    node.sendMessageToConnectedLinkers(message);
                }
                break;
        }


//        if(message.contains("\"entity\":\"node\"")) {
//            Integer portNumber = jsonMessage.getJSONPortMessage(message);
//            Integer type = jsonMessage.getJSONTypeMessage(message);
//            node.createAndAddNodeFromPort(portNumber, type);
//            System.out.println(message);
//        } else if (message.contains("\"entity\":\"interface\"")) {
//            System.out.println("Adding interface from: " + message);
//            Integer type = jsonMessage.getJSONTypeMessage(message);
//            entity.setType(type);
//        } else if (message.contains("interface")) {
//            message = message.replace("interface", "node");
//            System.out.println("Interface: " + message);
//            node.sendMessageToConnectedLinkers(message);
//        } else if (message.contains("node")){
//            message = message.replace("node", "");
//            System.out.println("Node: " + message);
//            node.sendMessageToNonNodeLinkers(message);
//        }
    }

}

