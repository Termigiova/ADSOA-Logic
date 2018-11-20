package main.MessageHandler;

import main.Entity.Entity;
import main.Entity.Interface.Interface;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

public class InterfaceMessageHandler extends AbstractMessageHandler {

    private Interface clientInterface;
    private String message;
    private String response;
    private JSONMessage jsonMessage;

    public InterfaceMessageHandler(Entity entity, Interface clientInterface) {
        this.clientInterface = clientInterface;
        this.entity = entity;
        jsonMessage = new JSONMessage();

        sendSetTypeMessage();
    }

    private void sendSetTypeMessage() {
        String jsonTypeMessage = jsonMessage.createConnectInterfaceMessage();
        Linker linker = entity.getLinker();
        linker.sendMessage(jsonTypeMessage);
    }

    public boolean processMessage(String message) {
        System.out.println(message);
        return false;
    }

}
