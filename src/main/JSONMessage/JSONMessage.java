package main.JSONMessage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.Entity.Entity;
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

    public String createConnectNodeMessage(Integer portNumber, Entity entity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("contentCode", EnumContentCode.INITIAL_NODE_CONF);
        objectNode.put("portNumber", portNumber);
        objectNode.put("type", entity.getType());
        objectNode.put("footprint", entity.getFootprint());

        String parsedJSONMessage = objectNode.toString();

        return parsedJSONMessage;
    }

    public String getConnectInterfaceMessage(Entity entity) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("contentCode", EnumContentCode.INITIAL_INTERFACE_CONF);
        objectNode.put("type", entity.getType());
        objectNode.put("footprint", entity.getFootprint());

        return objectNode.toString();
    }

    public String createInterfaceMessage(InterfaceMessage interfaceMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(interfaceMessage);
    }

    public String updateOriginMessage(Integer origin, String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader reader = objectMapper.reader();
        String parsedJSONMessage = "";

        try {
            JsonNode jsonNode = reader.readTree(message);
            ObjectNode objectNode = (ObjectNode) jsonNode;
            objectNode.put("origin", origin);
            parsedJSONMessage = objectNode.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedJSONMessage;
    }

    public Integer getInteger(String name, String JSONMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONMessage);
        JsonNode message = rootNode.path(name);

        return message.asInt();
    }

    public String getString(String name, String JSONMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONMessage);
        JsonNode footprint = rootNode.path(name);

        return footprint.toString();
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
