package main.MessageHandler;

import main.Entity.Entity;
import main.Entity.Interface.Interface;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

public class InterfaceMessageHandler extends AbstractMessageHandler {

    private Interface clientInterface;
    private String message;
    private JSONMessage jsonMessage;

    public InterfaceMessageHandler(Entity entity, Interface clientInterface) {
        this.clientInterface = clientInterface;
        this.entity = entity;
        jsonMessage = new JSONMessage();

        sendFirstMessage();
        this.start();
    }

    private void sendFirstMessage() {
        Linker linker = entity.getLinker();
        message = jsonMessage.getConnectInterfaceMessage(entity);
        linker.sendMessage(message);
    }

    public void processMessage(String message) {
        System.out.println("Interface receiving: " + message);
    }

}
