package main.Entity.Interface;

import main.Entity.Entity;
import main.Enum.EnumContentCode;
import main.Enum.EnumType;
import main.JSONMessage.JSONMessage;
import main.MessageHandler.InterfaceMessageHandler;
import main.Sockets.Linker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Interface {

    private Entity entity;
    private JSONMessage jsonMessage;

    private Interface() throws IOException {
        entity = new Entity();
        jsonMessage = new JSONMessage();

        initializeEntityValues();
        initializeMessageHandler();

        sendOutput();
    }

    private void initializeEntityValues() throws IOException {
        Linker linker = getLinker();
        this.entity.setLinker(linker);
        this.entity.setType(EnumType.INTERFACE);
        this.entity.generateFootprint();
    }

    private Linker getLinker() throws IOException {
        Socket socket = createSocketInPort(5000);
        return new Linker(socket);
    }

    private void initializeMessageHandler() {
        InterfaceMessageHandler interfaceMessageHandler = new InterfaceMessageHandler(entity, this);
        interfaceMessageHandler.start();
    }

    private Socket createSocketInPort(Integer portNumber) {
        Socket socket;
        try {
            socket = new Socket(getHostname(), portNumber);
            return socket;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHostname() {
        String hostName = "";

        try {
            hostName = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()).toString();
            hostName = hostName.replace("/","");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostName;
    }

    private void sendOutput() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromUser;
        Linker linker = entity.getLinker();

        while(true) {
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                String message = jsonMessage.createInterfaceMessage(EnumContentCode.SUM);
//                linker.sendMessage(fromUser);
                linker.sendMessage(message);
            }
        }
    }

    public void readIncomingInput(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) throws IOException {
        Interface client = new Interface();
    }
}
