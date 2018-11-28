package main.MessageHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.Entity.Entity;
import main.Entity.Node.Node;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

import java.io.IOException;
import java.net.Socket;

import static main.Enum.EnumContentCode.*;

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
            case INITIAL_ENTITY_CONF:
                System.out.println("Entering INITIAL_ENTITY_CONF");
                initialEntityConfHandler(message);
                break;
            case SUM:
            case SUBSTRACTION:
            case MULTIPLICATION:
            case DIVISION:
            case RESULT:
                System.out.println("Entering Business logic message handler");
                businessLogicMessageHandler(message);
                break;
        }
    }

    private void initialEntityConfHandler(String message) throws IOException {
        Integer type = jsonMessage.getInteger("type", message);
        String footprint = jsonMessage.getString("footprint", message);
        entity.setType(type);
        entity.setFootprint(footprint);
        System.out.println("Adding incoming entity");
        node.addIncomingLinker(entity);
    }

    private void businessLogicMessageHandler(String message) throws IOException {
        Integer origin = jsonMessage.getInteger("origin", message);
        if (origin == NODE) {
            System.out.println("Sending to non node linkers");
            node.sendMessageToNonNodeLinkers(message);
        } else {
            message = jsonMessage.updateOriginMessage(NODE, message);
            System.out.println("Sending to all linkers");
            node.sendMessageToConnectedLinkers(message);
        }
    }

}

