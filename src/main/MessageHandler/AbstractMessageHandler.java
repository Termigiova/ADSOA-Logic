package main.MessageHandler;

import main.Entity.Entity;
import main.Sockets.Linker;

import java.io.IOException;

public abstract class AbstractMessageHandler extends Thread {

    protected Entity entity;

    @Override
    public void run() {
        String message;
        Linker linker = entity.getLinker();
        while (true) {
            try {
                message = linker.readMessage();
                processMessage(message);
            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }

    public void processMessage(String message) throws IOException {}

    public Entity getEntity() { return entity; }

}