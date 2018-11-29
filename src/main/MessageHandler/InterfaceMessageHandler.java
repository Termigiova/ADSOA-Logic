package main.MessageHandler;

import main.Entity.Entity;
import main.Entity.Interface.Interface;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;
import main.Queue.InputQueue;

public class InterfaceMessageHandler extends AbstractMessageHandler {

    private Interface clientInterface;
    private String message;
    private JSONMessage jsonMessage;

    public InterfaceMessageHandler(Entity entity, Interface clientInterface) {
        this.clientInterface = clientInterface;
        this.entity = entity;
        jsonMessage = new JSONMessage();
        inputQueue = new InputQueue(this);

        sendFirstMessage();
        this.start();
    }

    private void sendFirstMessage() {
        Linker linker = entity.getLinker();
        message = jsonMessage.createEntityConnectionMessage(entity);
        linker.sendMessage(message);
    }

    public void processMessage(String message) {
        System.out.println("Interface receiving: " + message);
    }

}
