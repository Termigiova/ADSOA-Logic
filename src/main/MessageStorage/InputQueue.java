package main.MessageStorage;

import main.MessageHandler.MessageHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class InputQueue extends Thread {

    private Queue<String> queue;
    private MessageHandler messageHandler;

    public InputQueue(MessageHandler messageHandler) {
        queue = new LinkedList<>();
        this.messageHandler = messageHandler;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                checkMessages();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkMessages() throws IOException {
        if(!queue.isEmpty()) {
            String message = queue.remove();
            System.out.println("Pop " + message);
            messageHandler.processMessage(message);
        }
    }

    public void addMessage(String message) {
        System.out.println("Input MessageStorage Adding " + message);
        queue.add(message);
    }

    public static void main(String args[]) {
//        MessageStorage inputQueue = new MessageStorage();
//        for (int i = 0; i < 5; i++) {
//            inputQueue.addMessage("String " + i);
//        }
    }

}
