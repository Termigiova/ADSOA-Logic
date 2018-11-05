package main.Entity.Node;

import main.MessageHandler.NodeMessageHandler;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.*;

public class NodeServerListener extends Thread{

    private final Integer MIN_PORT_NUMBER = 5000;
    private final Integer MAX_PORT_NUMBER = 5010;
    private Node node;
    private ServerSocket serverSocket;

    NodeServerListener(Node node) {
        this.node = node;
        tryToConnectToAvailablePort();
    }

    private void tryToConnectToAvailablePort() {
        for (int portNumber = MIN_PORT_NUMBER; portNumber <= MAX_PORT_NUMBER ; portNumber++) {
            try {
                serverSocket = ServerSocketFactory.getDefault().createServerSocket(portNumber);
                System.out.println("Connected to port " + portNumber);
                break;
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socketToEntity = serverSocket.accept();
                NodeMessageHandler nodeMessageHandler = new NodeMessageHandler(socketToEntity, node);
                node.addIncomingLinker(nodeMessageHandler.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Integer getLocalPort() { return serverSocket.getLocalPort(); }

    String getHostname() {
        String hostName = "";

        try {
            hostName = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()).toString();
            hostName = hostName.replace("/","");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostName;
    }

}
