package main.MessageHandler;

import main.Entity.Entity;
import main.Entity.Interface.Interface;
import main.JSONMessage.JSONMessage;

public class InterfaceMessageHandler extends AbstractMessageHandler {

    private Interface clientInterface;
    private String message;
    private String response;
    private JSONMessage jsonMessage;

    public InterfaceMessageHandler(Entity entity, Interface clientInterface) {
        this.clientInterface = clientInterface;
        this.entity = entity;
        jsonMessage = new JSONMessage();
    }

    public void processMessage(String message) {
        System.out.println(message);
    }

}
