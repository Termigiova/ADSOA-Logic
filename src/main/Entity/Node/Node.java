package main.Entity.Node;

import main.Entity.Entity;
import main.Entity.EntityThread;
import main.Sockets.Linker;
import main.Sockets.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Node {
    private ArrayList<Entity> arrayListOfEntities;
    private NodeServerListener nodeServerListener;
    private ServerSocket serverSocket;
    private String message;
    private Linker linker;
    MessageHandler messageHandler;

    private Node() throws IOException {
        arrayListOfEntities = new ArrayList<>();

        createNodeListener();
        createNodeLinker();

    }

    private void createNodeListener() throws IOException {
        nodeServerListener = new NodeServerListener(this);
        nodeServerListener.start();
    }

    private void createNodeLinker() throws IOException {
        Socket socketToThisServer = new Socket(nodeServerListener.getHostname(), nodeServerListener.getLocalPort());
        linker = new Linker(socketToThisServer);
        linker.sendMessage("Testing createNodeLinker");
    }

    public void connectToPort(Integer portNumber) throws IOException {
        Socket socketToThisServer = new Socket(nodeServerListener.getHostname(), portNumber);
        Linker linker = new Linker(socketToThisServer);
        linker.sendMessage("Testing connect to port");
    }

    void addIncomingSocket(Socket incomingSocket) {
        EntityThread entityThread;
        try {
            entityThread = new EntityThread(incomingSocket,this);
            entityThread.start();

            Entity entity = new Entity(entityThread);
            arrayListOfEntities.add(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entity getNode(EntityThread entityThread) {
        for (Entity entity : arrayListOfEntities) {
            if (entityThread == entity.getEntityThread())
                return entity;
        }
        return null;
    }

    public void printReadObject(String message) {
        System.out.println(message);
    }

    public static void main(String args[]) throws IOException {
        Node node = new Node();
        node.connectToPort(5000);
    }

}