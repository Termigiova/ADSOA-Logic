package main.Entity.Interface;

import main.Entity.AbstractEntity;
import main.Enum.EnumType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Interface extends AbstractEntity {

    private Interface() throws IOException {
        super();
        setType(EnumType.INTERFACE);
    }

    @Override
    public void readFromIncomingInput() throws IOException, ClassNotFoundException {
        Object operation;

        while (true) {
            operation = in.readObject();
            System.out.println("Interface receiving: " + Arrays.toString((Object[]) operation));
        }
    }

    @Override
    public void sendOutput() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromUser;

        while(true) {
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                Object[] message = formatMessageToObjectArray(fromUser);
                System.out.println("Interface sending: " + Arrays.toString(message));
                out.writeObject(message);
            }
        }
    }

    private Object[] formatMessageToObjectArray(String fromUser) {
        String[] stringArray = fromUser.split(" ");
        Object[] objectArray = new Object[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            objectArray[i] = Integer.parseInt(stringArray[i]);
        }
        return objectArray;
    }

    public static void main(String[] args) throws IOException {

        Interface client = new Interface();
        client.connectToPort(5000);
        client.getInfo();
        client.start();
        client.sendOutput();

    }
}
