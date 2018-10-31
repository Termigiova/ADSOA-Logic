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
        initializeEntityValues();
        connectToOtherNodes();

        sendThisPortToOtherNodes();

        sendOutput();
    }

    private void createNodeListener() throws IOException {
        nodeServerListener = new NodeServerListener(this);
        nodeServerListener.start();
    }

    private void initializeEntityValues() throws IOException {
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
                connectToPort(portNumber);
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }

    void connectToPort(Integer portNumber) throws IOException {
        Socket socketToThisServer = new Socket(nodeServerListener.getHostname(), portNumber);
        Linker linker = new Linker(socketToThisServer);
        addIncomingLinker(linker);
    }

    void addIncomingLinker(Linker incomingLinker) {
        Entity entity = new Entity();
        entity.setLinker(incomingLinker);
        arrayListOfEntities.add(entity);
    }

    private void sendThisPortToOtherNodes() throws JsonProcessingException {
        for (Entity destinationEntity : arrayListOfEntities) {
            Linker destinationLinker = destinationEntity.getLinker();
            String jsonPortMessage = jsonMessage.createJSONPortMessage(nodeServerListener.getLocalPort());
            destinationLinker.sendMessage(jsonPortMessage);
        }
    }

    private void sendOutput() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromUser;

        while(true) {
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                for (Entity destinationEntity : arrayListOfEntities) {
                    Linker destinationLinker = destinationEntity.getLinker();
                    destinationLinker.sendMessage(fromUser);
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {
        Node node = new Node();
    }

}