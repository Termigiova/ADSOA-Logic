package main.MessageHandler;

import main.Entity.BusinessLogic.BusinessLogic;
import main.Entity.Entity;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;
import main.MessageStorage.InputQueue;

import java.io.IOException;

import static main.Enum.EnumContentCode.*;

public class BusinessLogicMessageHandler extends AbstractMessageHandler {

    private BusinessLogic businessLogic;
    private JSONMessage jsonMessage;
    private String message;

    private Integer result;
    private String receiptAcknowledgment;

    public BusinessLogicMessageHandler(Entity entity, BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
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

    public void processMessage(String message) throws IOException {
        Integer contentCode = jsonMessage.getInteger("contentCode", message);
        System.out.println("BL processing: " + message);
        switch(contentCode) {
            case SUM:
            case SUBSTRACTION:
            case MULTIPLICATION:
            case DIVISION:
                processOperation(contentCode, message);
                break;
            default:
                System.out.println("Non defined content code, discarding message");
        }
    }

    private void processOperation(Integer contentCode, String message) throws IOException {
        if (contentCode == entity.getType()) {
            result = businessLogic.performOperation(message);
            receiptAcknowledgment = jsonMessage.getString("receiptAcknowledgment", message);
            sendResultMessage();
        } else {
            System.out.println("Discarding message due to different content code");
        }
    }

    private void sendResultMessage() {
        Linker linker = entity.getLinker();
        message = jsonMessage.createResultMessage(entity, result, receiptAcknowledgment);
        linker.sendMessage(String.valueOf(message));
    }
}
