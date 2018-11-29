package main.MessageHandler;

import java.io.IOException;

public interface MessageHandler {

    void processMessage(String message) throws IOException;
    void addMessageToQueue(String message);

}
