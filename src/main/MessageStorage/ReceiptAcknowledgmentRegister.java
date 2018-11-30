package main.MessageStorage;

import main.JSONMessage.JSONMessage;
import main.MessageHandler.InterfaceMessageHandler;
import main.Reflection.Reflection;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import static main.MessageHandler.InterfaceMessageHandler.MINIMUM_NUMBER_OF_RECEIPTS;

public class ReceiptAcknowledgmentRegister extends Thread {

    private Map<String, String> receiptsHashTable;
    private Integer count;
    private InterfaceMessageHandler interfaceMessageHandler;
    private String receipt;
    private JSONMessage jsonMessage;

    public ReceiptAcknowledgmentRegister(InterfaceMessageHandler interfaceMessageHandler) throws InterruptedException {
        receiptsHashTable = new Hashtable<>();
        count = 0;
        this.interfaceMessageHandler = interfaceMessageHandler;
        jsonMessage = new JSONMessage();

        this.start();
    }

    public void lookForReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void addMessageAndReceipt(String message) {
        try {
            String receipt = jsonMessage.getString("receiptAcknowledgment", message);
            receiptsHashTable.put(message, receipt);
            // Inverted the message and receipt since the hash table key can not be repeated
        } catch (IOException e) {

        }

    }

    @Override
    public void run() {
        String receiptKey;
        String message = "Default message";
        Iterator<String> iterator;
        while(true) {
                try {
                    Thread.sleep(10000);
                    iterator = receiptsHashTable.keySet().iterator();
//                    System.out.println("Looking for receipts...");
                    while(iterator.hasNext()) {
                        message = iterator.next();
                        receiptKey = receiptsHashTable.get(message);
                        if(receiptKey.equals(this.receipt)) {
                            count++;
                        }
                        iterator.remove();
                    }
//                    System.out.println("Received: " + count + " receipts");
                    if (count > 0)
                        processReceiptsBasedOnItsCounter(message);
                    resetCounter();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    private void processReceiptsBasedOnItsCounter(String message) {
        Integer contentCode = null;
        try {
            contentCode = jsonMessage.getInteger("contentCode", message);
            if (count < MINIMUM_NUMBER_OF_RECEIPTS) {
                System.out.println("Replicating");
                // Replicate
//                for (int i = 0; i < count; i++) {
//                    new Reflection("BusinessLogic", "5000 " + contentCode);
//                }
            } else {
                interfaceMessageHandler.printMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetCounter() { count = 0; }

    public static void main(String args[]) throws InterruptedException {
//        ReceiptAcknowledgmentRegister receiptRegister = new ReceiptAcknowledgmentRegister();
//
//        receiptRegister.addMessageAndReceipt("1");
//        receiptRegister.addMessageAndReceipt("1");
//        receiptRegister.addMessageAndReceipt("1");
//        receiptRegister.addMessageAndReceipt("2");
//
//        Thread.sleep(5000);
//        receiptRegister.lookForReceipt("1");
//
//        Thread.sleep(5000);
//        receiptRegister.lookForReceipt("2");
    }

}
