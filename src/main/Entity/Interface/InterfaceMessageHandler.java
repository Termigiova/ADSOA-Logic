package main.Entity.Interface;

import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

import java.io.IOException;

public class InterfaceMessageHandler extends Thread {

    private Linker linker;
    private Interface clientInterface;
    private String message;
    private String response;
    private JSONMessage jsonMessage;

    InterfaceMessageHandler(Linker linker, Interface clientInterface) {
        this.clientInterface = clientInterface;
        this.linker = linker;
        jsonMessage = new JSONMessage();
    }

    @Override
    public void run() {
        while (true) {
            try {
                message = linker.readMessage();
                processMessage(message);
            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }

    private void processMessage(String message) {
        clientInterface.readIncomingInput(message);
        System.out.println(message);
    }

}
