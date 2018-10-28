package main.Entity;

import main.Enum.EnumType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class AbstractEntity extends Thread implements EntityInterface {
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    private Socket socket;
    private Integer type;
    private Integer contentCode;
    private String footprint;

    protected AbstractEntity() {
    }

    public void connectToPort(Integer portNumber) {
        try {
            String hostName = getHostname();
            socket = new Socket(hostName, portNumber);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Connected to port: " + portNumber);
        } catch (Exception e) {
//            System.out.println("Error: " + e);
        }
    }

    public abstract void sendOutput() throws IOException;
    public abstract void readFromIncomingInput() throws IOException, ClassNotFoundException;

    public String getHostname() {
        String hostName = "";

        try {
            hostName = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()).toString();
            hostName = hostName.replace("/","");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostName;
    }

    public void run() {
        try {
            readFromIncomingInput();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + socket.getLocalAddress());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + socket.getLocalAddress());
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setType(int typeNumber) throws IOException {
        this.type = typeNumber;

        // Show type in console
        EnumType type = new EnumType();
        System.out.println("Setting type: " + type.toString(this.type));
//        try {
//            out.writeObject("setType:" + type.getType(this.type));
//        } catch (Exception e) {
//            System.out.println("Could not send setType message");
//        }
    }

    public void getInfo() {
        System.out.println("Host: " + socket.getLocalAddress() + "| LocalPort: " + socket.getLocalPort());
    }
}
