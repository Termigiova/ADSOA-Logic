package main.MessageHandler;

import main.Entity.Entity;
import main.Entity.Interface.Interface;
import main.JSONMessage.JSONMessage;
import main.MessageStorage.ReceiptAcknowledgmentRegister;
import main.Sockets.Linker;
import main.MessageStorage.InputQueue;

import java.io.IOException;

import static main.Enum.EnumContentCode.RESULT;

public class InterfaceMessageHandler extends AbstractMessageHandler {

    private Interface clientInterface;
    private String message;
    private JSONMessage jsonMessage;
    private ReceiptAcknowledgmentRegister receiptRegister;

    public static Integer MINIMUM_NUMBER_OF_RECEIPTS = 3;

    public InterfaceMessageHandler(Entity entity, Interface clientInterface) {
        this.clientInterface = clientInterface;
        this.entity = entity;
        jsonMessage = new JSONMessage();
        inputQueue = new InputQueue(this);
        try {
            receiptRegister = new ReceiptAcknowledgmentRegister(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendFirstMessage();
        this.start();
    }

    private void sendFirstMessage() {
        Linker linker = entity.getLinker();
        message = jsonMessage.createEntityConnectionMessage(entity);
        linker.sendMessage(message);
    }

    public void processMessage(String message) throws IOException {

        System.out.println("Procesing message");
        try {
            Integer contentCode = jsonMessage.getInteger("contentCode", message);
            if (contentCode == RESULT) {
                String receipt = jsonMessage.getString("receiptAcknowledgment", message);
                receiptRegister.addMessageAndReceipt(message);
                receiptRegister.lookForReceipt(receipt);
            }
        } catch (IOException e) {
            System.out.println("Error while processing message");
        }
    }

    public void printMessage(String message) {
        System.out.println("Correct number of receipts");
        System.out.println("Message: " + message);
    }

}
