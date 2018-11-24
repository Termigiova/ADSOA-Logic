package main.MessageHandler;

import main.Entity.Entity;
import main.Enum.EnumContentCode;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;

import java.io.IOException;

public abstract class AbstractMessageHandler extends Thread {

    protected Entity entity;

    @Override
    public void run() {
        receiveFirstMessageToGetEntityInformation();
        manageMessages();
    }

    private void manageMessages() {
        String message;
        Linker linker = entity.getLinker();
        System.out.println("Ready to manage messages: ");
        while (true) {
            try {
                message = linker.readMessage();
                System.out.println(message);
                processMessage(message);
            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }

    abstract void processMessage(String message) throws IOException;

    private void receiveFirstMessageToGetEntityInformation() {
        String message;
        Linker linker = entity.getLinker();

        while (true) {
            try {
                message = linker.readMessage();
                System.out.println("First Message: " + message);
                JSONMessage jsonMessage = new JSONMessage();
                Integer contentCode = jsonMessage.getInteger("contentCode", message);
                if (contentCodeIsInitialConfiguration(contentCode)) {
                    processMessage(message);
                    break;
                }
            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }

    private Boolean contentCodeIsInitialConfiguration(Integer contentCode) {
        return (contentCode == EnumContentCode.INITIAL_NODE_CONF) ||
                (contentCode == EnumContentCode.INITIAL_INTERFACE_CONF) ||
                (contentCode == EnumContentCode.INITIAL_OPERATION_CONF);
    }

    public Entity getEntity() { return entity; }

}