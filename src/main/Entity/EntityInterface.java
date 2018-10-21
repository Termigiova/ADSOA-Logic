package main.Entity;

import java.io.IOException;

public interface EntityInterface {
    void sendOutput() throws IOException;
    void readFromIncomingInput() throws IOException, ClassNotFoundException;
    void setType(int typeNumber) throws IOException;
    void getInfo();
    String getHostname();
}
