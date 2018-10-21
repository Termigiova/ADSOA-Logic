package main.JSONMessage;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSONMessage {

    public Header header;
    public Message message;

    public JSONMessage() {
        header = new Header();
        message = new Message();
    }

}
