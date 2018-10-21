package main.Entity.Node;

import main.Entity.AbstractEntity;
import main.Entity.Entity;
import main.Entity.EntityThread;
import main.Enum.EnumType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Node extends AbstractEntity {
    private final Integer MIN_PORT_NUMBER = 5000;
    private final Integer MAX_PORT_NUMBER = 5010;
    private ServerSocket serverSocket;
    private ArrayList<Entity> arrayListOfEntities;
    private Object[] message;
    private String result;

    private Node() throws IOException {
        boolean couldConnect;

        setType(EnumType.NODE);
        couldConnect = tryToConnectToNonUsedPort();
        if(couldConnect) {
            connectToOtherNodes();
        }
    }

    private boolean tryToConnectToNonUsedPort() {
        for (int portNumber = MIN_PORT_NUMBER; portNumber <= MAX_PORT_NUMBER; portNumber++) {
            try {
                serverSocket = new ServerSocket(portNumber);
                arrayListOfEntities = new ArrayList<>();
                return true;
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        return false;
    }

    private void connectToOtherNodes() {
        for (int portNumber = MIN_PORT_NUMBER; portNumber <= MAX_PORT_NUMBER; portNumber++) {
            if (serverSocket.getLocalPort() == portNumber)
                continue;
            try {
                connectToPort(portNumber);
            } catch (Exception e) {
//                System.out.println("Could not connect to other nodes");
//                e.printStackTrace();
            }
        }
    }

    private void runServices() {
        Socket socket;
        EntityThread entityThread;
        Entity entity;
        while (true) {
            try {
                socket = serverSocket.accept();
                entityThread = new EntityThread(socket,this);
                entityThread.start();

                entity = new Entity(entityThread);
                arrayListOfEntities.add(entity);
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void readFromIncomingInput() throws IOException, ClassNotFoundException {
        Object operation;

        while (true) {
            operation = in.readObject();
            System.out.println("Node reading: " + Arrays.toString((Object[]) operation));
            result = Arrays.toString((Object[]) operation);
            message = (Object[]) operation;
            sendOutput();
        }
    }

    @Override
    public void sendOutput() throws IOException {
        System.out.println("Node sending: " + result);
    }

    public void processMessage(Object message, EntityThread origin) throws IOException {
        if(comesFromNode(origin))
            sendMessageToConnectedEntitiesExceptNode(message);
        else
            sendMessageToConnectedEntities(message);
    }

    private boolean comesFromNode(EntityThread origin) {
        Entity originEntity = origin.getNode();
        return originEntity.getType() == EnumType.NODE;
    }

    private void sendMessageToConnectedEntitiesExceptNode(Object message) throws IOException {
        EntityThread destination;
        for (Entity destinationEntity : arrayListOfEntities) {
            if (destinationEntity.getType() != EnumType.NODE) {
                destination = destinationEntity.getEntityThread();
                destination.sendMessage(message);
                EnumType type = new EnumType();
                System.out.println("Sent to: " + type.toString(destinationEntity.getType()));
            }
        }
    }

    private void sendMessageToConnectedEntities(Object message) throws IOException {
        EntityThread destination;
        for (Entity destinationEntity : arrayListOfEntities) {
            destination = destinationEntity.getEntityThread();
            destination.sendMessage(message);
            EnumType type = new EnumType();
            System.out.println("Sent to: " + type.toString(destinationEntity.getType()));
        }
    }

    public Entity getNode(EntityThread entityThread) {
        for (Entity entity : arrayListOfEntities) {
            if (entityThread == entity.getEntityThread())
                return entity;
        }
        return null;
    }

    public void sendMessage(Object message) {
        System.out.println("Node message: " + Arrays.toString((Object[]) message));
    }

    public static void main(String args[]) throws IOException {
        Node node = new Node();
        node.start();
        node.runServices();
    }
}