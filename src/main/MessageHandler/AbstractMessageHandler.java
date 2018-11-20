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
        manageMessage();
    }

    private void manageMessage() {
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

    abstract boolean processMessage(String message) throws IOException;

    private void receiveFirstMessageToGetEntityInformation() {
        String message;
        Linker linker = entity.getLinker();

        while (true) {
            try {
                message = linker.readMessage();
                System.out.println("First Message: " + message);
                JSONMessage jsonMessage = new JSONMessage();
                Integer contentCode = jsonMessage.getContentCode(message);
                if (contentCode == EnumContentCode.INITIAL_NODE_CONF ||
                        contentCode == EnumContentCode.INITIAL_INTERFACE_CONF ||
                        contentCode == EnumContentCode.INITIAL_OPERATION_CONF) {
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

    public Entity getEntity() { return entity; }

}