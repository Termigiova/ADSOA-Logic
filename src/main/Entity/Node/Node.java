package main.Entity.Node;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.Entity.Entity;
import main.JSONMessage.JSONMessage;
import main.MessageHandler.NodeMessageHandler;
import main.Sockets.Linker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import static main.Enum.EnumContentCode.NODE;

public class Node {

    private ArrayList<Entity> arrayListOfEntities;
    private NodeServerListener nodeServerListener;
    private Entity entity;
    private JSONMessage jsonMessage;

    private Node() throws IOException {
        arrayListOfEntities = new ArrayList<>();
        jsonMessage = new JSONMessage();
        entity = new Entity();

        createNodeListener();
        initializeNodeEntityValues();
        connectToOtherNodes();
        sendThisPortToOtherNodes();
    }

    private void createNodeListener() {
        nodeServerListener = new NodeServerListener(this);
        nodeServerListener.start();
    }

    private void initializeNodeEntityValues() throws IOException {
        Linker linker = getNodeLinker();
        this.entity.setLinker(linker);
        this.entity.setType(NODE);
        this.entity.generateFootprint();
    }

    private Linker getNodeLinker() throws IOException {
        Socket socketToThisServer = new Socket(nodeServerListener.getHostname(), nodeServerListener.getLocalPort());
        return new Linker(socketToThisServer);
    }

    private void connectToOtherNodes() {
        Integer MIN_PORT_NUMBER = 5000;
        Integer MAX_PORT_NUMBER = 5010;
        for (int portNumber = MIN_PORT_NUMBER; portNumber <= MAX_PORT_NUMBER; portNumber++) {
            try {
                if (portNumber == nodeServerListener.getLocalPort())
                    continue;
                Socket socketToNode = new Socket(nodeServerListener.getHostname(), portNumber);
                NodeMessageHandler nodeMessageHandler = new NodeMessageHandler(socketToNode, this);
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }

    public void addIncomingLinker(Entity entity) {
        arrayListOfEntities.add(entity);
    }

    private void sendThisPortToOtherNodes() throws JsonProcessingException {
        String jsonPortMessage = getConnectNodeMessage();

        for (Entity destinationEntity : arrayListOfEntities) {
            Linker destinationLinker = destinationEntity.getLinker();
            destinationLinker.sendMessage(jsonPortMessage);
        }
    }

    public String getConnectNodeMessage() throws JsonProcessingException {
        return jsonMessage.createConnectNodeMessage(nodeServerListener.getLocalPort(), entity);
    }

    public void sendMessageToConnectedLinkers(String message) throws IOException {
        System.out.println("Number of connected linkers: " + arrayListOfEntities.size());
        Linker destinationLinker;
        for (Entity destinationEntity : arrayListOfEntities) {
            if (messageCanBeSentToLinkers(destinationEntity, message)) {
                destinationLinker = destinationEntity.getLinker();
                destinationLinker.sendMessage(message);
            }
        }
    }

    public void sendMessageToNonNodeLinkers(String message) throws IOException {
        System.out.println("Number of connected linkers: " + arrayListOfEntities.size());
        Linker destinationLinker;
        for (Entity destinationEntity : arrayListOfEntities) {
            if (messageCanBeSendToNonNodeLinkers(destinationEntity, message)) {
                destinationLinker = destinationEntity.getLinker();
                destinationLinker.sendMessage(message);
            }
        }
    }

    private Boolean messageCanBeSentToLinkers(Entity destinationEntity, String message) throws IOException {
        String originMessageFootprint = jsonMessage.getString("originFootprint", message);
        return !destinationEntity.getFootprint().equals(originMessageFootprint);
    }

    private Boolean messageCanBeSendToNonNodeLinkers(Entity destinationEntity, String message) throws IOException {
        String originMessageFootprint = jsonMessage.getString("originFootprint", message);
        return      destinationEntity.getType() != NODE
                && !destinationEntity.getFootprint().equals(originMessageFootprint);
    }

    public Entity getEntity() { return entity; }

    public static void main(String args[]) throws IOException {
        Node node = new Node();
    }

}