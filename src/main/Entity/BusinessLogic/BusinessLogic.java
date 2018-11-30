package main.Entity.BusinessLogic;

import main.Entity.BusinessLogic.Operations.*;
import main.Entity.Entity;
import main.JSONMessage.JSONMessage;
import main.MessageHandler.BusinessLogicMessageHandler;
import main.Sockets.Linker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static main.Enum.EnumContentCode.*;

public class BusinessLogic {

    private Entity entity;
    private JSONMessage jsonMessage;

    private Operation operation;
    private Integer value1;
    private Integer value2;

    public BusinessLogic(Integer portNumber, Integer entityContentCode) throws IOException {
        entity = new Entity();
        jsonMessage = new JSONMessage();

        initializeEntityValues(portNumber, entityContentCode);
        initializeOperationType(entityContentCode);
        initializeMessageHandler();
    }

    private void initializeEntityValues(Integer portNumber, Integer entityContentCode) throws IOException {
        Linker linker = createLinker(portNumber);
        this.entity.setLinker(linker);
        this.entity.setType(entityContentCode);
        this.entity.generateFootprint();
    }

    private Linker createLinker(Integer portNumber) throws IOException {
        Socket socket = new Socket(getHostname(), portNumber);
        return new Linker(socket);
    }

    private String getHostname() {
        String hostName = "";

        try {
            hostName = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()).toString();
            hostName = hostName.replace("/","");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostName;
    }

    private void initializeOperationType(Integer entityContentCode) {
        switch(entityContentCode) {
            case SUM:
                operation = new Sum();
                break;
            case SUBSTRACTION:
                operation = new Substraction();
                break;
            case MULTIPLICATION:
                operation = new Multiplication();
                break;
            case DIVISION:
                operation = new Division();
                break;
        }
    }

    private void initializeMessageHandler() {
        BusinessLogicMessageHandler businessLogicMessageHandler = new BusinessLogicMessageHandler(entity, this);
    }

    public Integer performOperation(String message) throws IOException {
        value1 = jsonMessage.getInteger("firstValue", message);
        value2 = jsonMessage.getInteger("secondValue", message);
        return operation.solve(value1, value2);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a port number: ");
        Integer portNumber = Integer.valueOf(stdIn.readLine());
        System.out.println("Enter a entity type");
        System.out.println("3 - SUM");
        System.out.println("4 - SUBSTRACTION");
        System.out.println("5 - MULTIPLICATION");
        System.out.println("6 - DIVISION");
        Integer entityType = Integer.valueOf(stdIn.readLine());

//        Integer portNumber = Integer.valueOf(args[0]);
//        Integer entityType = Integer.valueOf(args[1]);
//        System.out.println("Port number " + portNumber);
//        System.out.println("Entity type " + entityType);

        BusinessLogic businessLogic = new BusinessLogic(portNumber, entityType);

    }

}
