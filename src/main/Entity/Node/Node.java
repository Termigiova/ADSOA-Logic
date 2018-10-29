package main.Entity.Node;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.Entity.Entity;
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
    private Linker linker;
    private JSONMessage jsonMessage;
    private String message;

    private Node() throws IOException {
        arrayListOfEntities = new ArrayList<>();
        jsonMessage = new JSONMessage();

        createNodeListener();
        createNodeLinker();
        connectToOtherNodes();

        sendThisPortToOtherNodes();

//        message = jsonMessage.createJSONMessage();

        sendOutput();
    }

    private void createNodeListener() throws IOException {
        nodeServerListener = new NodeServerListener(this);
        nodeServerListener.start();
    }

    private void createNodeLinker() throws IOException {
        Socket socketToThisServer = new Socket(nodeServerListener.getHostname(), nodeServerListener.getLocalPort());
        linker = new Linker(socketToThisServer);
        linker.sendMessage("Testing createNodeLinker on port " + nodeServerListener.getLocalPort());
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

    private void connectToPort(Integer portNumber) throws IOException {
        Socket socketToThisServer = new Socket(nodeServerListener.getHostname(), portNumber);
        Linker linker = new Linker(socketToThisServer);
        addIncomingLinker(linker);
    }

    void addIncomingLinker(Linker incomingLinker) {
        Entity entity = new Entity(incomingLinker);
        arrayListOfEntities.add(entity);
    }

    void sendThisPortToOtherNodes() throws JsonProcessingException {
        for (Entity destinationEntity : arrayListOfEntities) {
            Linker destinationLinker = destinationEntity.getLinker();
            destinationLinker.sendMessage(jsonMessage.createJSONPortMessage(nodeServerListener.getLocalPort()));
        }
    }

    public void sendOutput() throws IOException {
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

    public void readInput(String message) throws IOException {
        if(message.contains("portNumber")) {
            Integer portNumber = jsonMessage.readJSONPortMessage(message);
            connectToPort(portNumber);
        }
        System.out.println(message);
    }

    public static void main(String args[]) throws IOException {
        Node node = new Node();
    }

}