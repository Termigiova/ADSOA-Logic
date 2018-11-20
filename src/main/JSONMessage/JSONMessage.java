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

    public String createJSONConnectNodeMessage(Integer portNumber, Entity entity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("contentCode", EnumContentCode.INITIAL_NODE_CONF);
        objectNode.put("portNumber", portNumber);
        objectNode.put("type", entity.getType());
        objectNode.put("footprint", entity.getFootprint());

        String parsedJSONMessage = objectNode.toString();

        return parsedJSONMessage;
    }

    public String createConnectInterfaceMessage() {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("contentCode", EnumContentCode.INITIAL_INTERFACE_CONF);
        objectNode.put("type", EnumType.INTERFACE);

        String parsedJSONMessage = objectNode.toString();

        return parsedJSONMessage;
    }

    public String createInterfaceMessage(Integer contentCode) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("contentCode", contentCode);
        objectNode.put("origin", EnumType.INTERFACE);
        objectNode.put("firstValue", 1);
        objectNode.put("secondValue", 1);

        String parsedJSONMessage = objectNode.toString();

        return parsedJSONMessage;
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

    public Integer getPortNumber(String JSONPortMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONPortMessage);
        JsonNode portNumber = rootNode.path("portNumber");

        return portNumber.asInt();
    }

    public Integer getType(String JSONTypeMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONTypeMessage);
        JsonNode type = rootNode.path("type");

        return type.asInt();
    }

    public Integer getContentCode(String JSONTypeMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONTypeMessage);
        JsonNode contentCode = rootNode.path("contentCode");

        return contentCode.asInt();
    }

    public Integer getOrigin(String JSONTypeMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONTypeMessage);
        JsonNode origin = rootNode.path("origin");

        return origin.asInt();
    }

    public String getFootprint(String JSONTypeMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(JSONTypeMessage);
        JsonNode footprint = rootNode.path("footprint");

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
