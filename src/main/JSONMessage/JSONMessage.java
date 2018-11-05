package main.JSONMessage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.Enum.EnumContentCode;
import main.Enum.EnumType;

import java.io.IOException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSONMessage {

    public Header header;
    public Message message;

    public JSONMessage() {
        header = new Header();
        message = new Message();
    }

    public String createJSONMessage() throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapperWithModule("MessageSerializer");

        // Set parameters
        header.setContentCode(EnumContentCode.NOTDECLARED);
        header.setFolio("folio");
        header.setOriginFootprint("originFootprint");
        header.setTransmitterType(EnumType.NOTDECLARED);
        message.setMessage("This is a message");

        String parsedJSONMessage = objectMapper.writeValueAsString(this);

        return parsedJSONMessage;

    }

    public String createJSONConnectNodeMessage(Integer portNumber, Integer type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("entity", "node");
        objectNode.put("portNumber", portNumber);
        objectNode.put("type", type);

        String parsedJSONMessage = objectNode.toString();

        return parsedJSONMessage;
    }

    public String createJSONConnectInterfaceMessage() {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("entity", "interface");
        objectNode.put("type", EnumType.INTERFACE);

        String parsedJSONMessage = objectNode.toString();

        return parsedJSONMessage;
    }

    public Integer readJSONPortMessage(String JSONPortMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONPortMessage);
        JsonNode portNumber = rootNode.path("portNumber");

        return portNumber.asInt();
    }

    public Integer readJSONTypeMessage(String JSONTypeMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONTypeMessage);
        JsonNode type = rootNode.path("type");

        return type.asInt();
    }

    private ObjectMapper getObjectMapperWithModule(String moduleName) {
        MessageSerializer messageSerializer = new MessageSerializer(JSONMessage.class);
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule(moduleName);
        module.addSerializer(JSONMessage.class, messageSerializer);

        objectMapper.registerModule(module);

        return objectMapper;
    }



}
