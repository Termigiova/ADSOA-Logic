package main.MessageHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    private JSONMessage jsonMessage;

    public NodeMessageHandler(Socket socket, Node node) throws JsonProcessingException {
        this.node = node;
        entity = new Entity();
        jsonMessage = new JSONMessage();

        initializeEntityValues(socket);
        sendFirstMessage();
        this.start();
    }

    private void initializeEntityValues(Socket socket) {
        Linker linker = new Linker(socket);
        entity.setLinker(linker);
    }

    private void sendFirstMessage() throws JsonProcessingException {
        Linker linker = entity.getLinker();
        message = node.getConnectNodeMessage();
        linker.sendMessage(message);
    }

    public void processMessage(String message) throws IOException {
        Integer contentCode = jsonMessage.getInteger("contentCode", message);

        System.out.println("Processing message");
        switch(contentCode) {
            case EnumContentCode.INITIAL_NODE_CONF:
                System.out.println("Entering INITIAL_NODE_CONF");
                initialNodeConfHandler(message);
                break;
            case EnumContentCode.INITIAL_INTERFACE_CONF:
                System.out.println("Entering INITIAL_INTERFACE_CONF");
                initialInterfaceConfHandler(message);
                break;
            case EnumContentCode.SUM:
            case EnumContentCode.SUBSTRACTION:
            case EnumContentCode.MULTIPLICATION:
            case EnumContentCode.DIVISION:
                System.out.println("Entering Business logic message handler");
                businessLogicMessageHandler(message);
                break;
        }
    }

    private void initialNodeConfHandler(String message) throws IOException {
        Integer type = jsonMessage.getInteger("type", message);
        String footprint = jsonMessage.getString("footprint", message);
        entity.setType(type);
        entity.setFootprint(footprint);
        System.out.println("Adding incoming node");
        node.addIncomingLinker(entity);
    }

    private void initialInterfaceConfHandler(String message) throws IOException {
        Integer type = jsonMessage.getInteger("type", message);
        String footprint = jsonMessage.getString("footprint", message);
        entity.setType(type);
        entity.setFootprint(footprint);
        System.out.println("Adding Incoming interface");
        node.addIncomingLinker(entity);
    }

    private void businessLogicMessageHandler(String message) throws IOException {
        Integer origin = jsonMessage.getInteger("origin", message);
        System.out.println("Entity before: " + message);
        // TODO change updateOriginMessage for general cases
        message = jsonMessage.updateOriginMessage(EnumType.NODE, message);
        System.out.println("Entity after : " + message);
        if (origin == EnumType.NODE) {
            System.out.println("Sending to non node linkers");
            node.sendMessageToNonNodeLinkers(message);
        } else {
            System.out.println("Sending to all linkers");
            node.sendMessageToConnectedLinkers(message);
        }
    }

}

