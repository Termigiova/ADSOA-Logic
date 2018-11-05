package main.Entity.Node;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.Entity.Entity;
import main.Enum.EnumType;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Node {

    private final Integer MIN_PORT_NUMBER = 5000;
    private final Integer MAX_PORT_NUMBER = 5010;
    private ArrayList<Entity> arrayListOfEntities;
    private NodeServerListener nodeServerListener;
    private Entity entity;
    private JSONMessage jsonMessage;
    private String message;

    private Node() throws IOException {
        arrayListOfEntities = new ArrayList<>();
        jsonMessage = new JSONMessage();
        entity = new Entity();

        createNodeListener();
        initializeNodeEntityValues();
        connectToOtherNodes();

        sendThisPortToOtherNodes();

        sendOutput();
    }

    private void createNodeListener() throws IOException {
        nodeServerListener = new NodeServerListener(this);
        nodeServerListener.start();
    }

    private void initializeNodeEntityValues() throws IOException {
        Linker linker = getNodeLinker();
        this.entity.setLinker(linker);
        this.entity.setType(EnumType.NODE);
        this.entity.generateFootprint();
    }

    private Linker getNodeLinker() throws IOException {
        Socket socketToThisServer = new Socket(nodeServerListener.getHostname(), nodeServerListener.getLocalPort());
        return new Linker(socketToThisServer);
    }

    private void connectToOtherNodes() {
        for (int portNumber = MIN_PORT_NUMBER; portNumber <= MAX_PORT_NUMBER; portNumber++) {
            try {
                if (portNumber == nodeServerListener.getLocalPort())
                    continue;
                createAndAddNodeFromPort(portNumber, EnumType.NODE);
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }

    public void createAndAddNodeFromPort(Integer portNumber, Integer type) throws IOException {
        Socket socketToThisServer = new Socket(nodeServerListener.getHostname(), portNumber);
        Linker linker = new Linker(socketToThisServer);
        Entity entity = new Entity();

        entity.setLinker(linker);
        entity.setType(type);
        addIncomingLinker(entity);
    }

    void addIncomingLinker(Entity entity) {
        arrayListOfEntities.add(entity);
    }

    private void sendThisPortToOtherNodes() throws JsonProcessingException {
        for (Entity destinationEntity : arrayListOfEntities) {
            Linker destinationLinker = destinationEntity.getLinker();
            String jsonPortMessage = jsonMessage.createJSONConnectNodeMessage(nodeServerListener.getLocalPort(), entity.getType());
            destinationLinker.sendMessage(jsonPortMessage);
        }
    }

    private void sendOutput() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String message;

        while(true) {
            message = stdIn.readLine();
            if (message != null)
                sendMessageToConnectedLinkers(message);
        }
    }

    public void sendMessageToConnectedLinkers(String message) {
        for (Entity destinationEntity : arrayListOfEntities) {
            Linker destinationLinker = destinationEntity.getLinker();
            destinationLinker.sendMessage(message);
        }
    }

    public void sendMessageToNonNodeLinkers(String message) {
        for (Entity destinationEntity : arrayListOfEntities) {
            if (destinationEntity.getType() != EnumType.NODE) {
                Linker destinationLinker = destinationEntity.getLinker();
                destinationLinker.sendMessage(message);
            }
        }
    }

    public static void main(String args[]) throws IOException {
        Node node = new Node();
    }

}