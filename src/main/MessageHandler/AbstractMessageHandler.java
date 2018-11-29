package main.MessageHandler;

import main.Entity.Entity;
import main.JSONMessage.JSONMessage;
import main.Sockets.Linker;
import main.Queue.InputQueue;

import java.io.IOException;

import static main.Enum.EnumContentCode.INITIAL_ENTITY_CONF;

public abstract class AbstractMessageHandler extends Thread implements MessageHandler{

    protected Entity entity;
    protected InputQueue inputQueue;

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
                addMessageToQueue(message);
            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }

    public void addMessageToQueue(String message) {
        inputQueue.addMessage(message);
    }

    public abstract void processMessage(String message) throws IOException;

    private void receiveFirstMessageToGetEntityInformation() {
        String message;
        Linker linker = entity.getLinker();

        while (true) {
            try {
                message = linker.readMessage();
                JSONMessage jsonMessage = new JSONMessage();
                Integer contentCode = jsonMessage.getInteger("contentCode", message);
                if (contentCode == INITIAL_ENTITY_CONF) {
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