package main.Entity;

import main.Entity.Node.Node;
import main.Enum.EnumType;
import main.Sockets.Linker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EntityThread extends Thread {
    protected Linker linker;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Node node;

    public EntityThread(Linker linker, Node node) throws IOException {
        this.node = node;
        this.linker = linker;
    }

    public void run() {
        try {
            setEntityType();
            converseWithClient();
        } catch (IOException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setEntityType() throws IOException, ClassNotFoundException {
        int type = getEntityType();
//        Entity entity = node.getNode(linker,this);
//        entity.setType(type);
    }

    private int getEntityType() throws IOException, ClassNotFoundException {
        String inputLine = in.readObject().toString();
        while (true) {
            if (inputLine.contains("setType")) {
                String array[] = inputLine.split(":");
                EnumType type = new EnumType();
                return type.getType(Integer.parseInt(array[1]));
            }
        }
    }

    private void converseWithClient() throws IOException, ClassNotFoundException {
        Object inputLine;

        while (true) {
            inputLine = in.readObject();
//            node.processMessage(inputLine, this);
            sendMessage(inputLine);
            if (inputLine.equals("Bye."))
                break;
        }
    }

    public void sendMessage(Object message) throws IOException {
//        node.readInput((String) message);
//        out.writeObject(message);
    }

//    public Entity getNode() { return node.getNode(this);}
}
