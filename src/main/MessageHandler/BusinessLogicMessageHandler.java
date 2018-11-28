package main.MessageHandler;

import main.Entity.BusinessLogic.BusinessLogic;
import main.Entity.Entity;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

import java.io.IOException;

public class BusinessLogicMessageHandler extends AbstractMessageHandler {

    private BusinessLogic businessLogic;
    private JSONMessage jsonMessage;
    private String message;

    private Integer result;

    public BusinessLogicMessageHandler(Entity entity, BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        this.entity = entity;
        jsonMessage = new JSONMessage();

        sendFirstMessage();
        this.start();
    }

    private void sendFirstMessage() {
        Linker linker = entity.getLinker();
        message = jsonMessage.createEntityConnectionMessage(entity);
        linker.sendMessage(message);
    }

    void processMessage(String message) throws IOException {
        System.out.println("BL processing: " + message);

        Integer contentCode = jsonMessage.getInteger("contentCode", message);

        System.out.println("Processing message");

        if (contentCode == entity.getType()) {
            result = businessLogic.performOperation(message);
            sendResultMessage();
        } else {
            System.out.println("Discarding message due to different content code");
        }
    }

    private void sendResultMessage() {
        Linker linker = entity.getLinker();
        message = jsonMessage.createResultMessage(entity, result);
        linker.sendMessage(String.valueOf(message));
    }
}
