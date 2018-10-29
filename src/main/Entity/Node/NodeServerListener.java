package main.Entity.Node;

import main.Sockets.MessageHandler;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.*;

public class NodeServerListener extends Thread{

    private final Integer MIN_PORT_NUMBER = 5000;
    private final Integer MAX_PORT_NUMBER = 5010;
    private Node node;
    private ServerSocket serverSocket;

    NodeServerListener(Node node) throws IOException {
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

            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socketToEntity = serverSocket.accept();
                MessageHandler messageHandler = new MessageHandler(socketToEntity, node);
                messageHandler.start();
                node.addIncomingLinker(messageHandler.getLinker());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer getLocalPort() { return serverSocket.getLocalPort(); }

    public String getHostname() {
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
