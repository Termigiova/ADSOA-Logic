package main.JSONMessage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.Entity.Entity;
import main.Utilities.Footprint;

import java.io.IOException;

import static main.Enum.EnumContentCode.INITIAL_ENTITY_CONF;
import static main.Enum.EnumContentCode.NOTDECLARED;
import static main.Enum.EnumContentCode.RESULT;

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
        header.setContentCode(NOTDECLARED);
        header.setFolio("folio");
        header.setOriginFootprint("originFootprint");
        header.setTransmitterType(NOTDECLARED);
        message.setMessage("This is a message");

        String parsedJSONMessage = objectMapper.writeValueAsString(this);

        return parsedJSONMessage;

    }

    public String createConnectNodeMessage(Integer portNumber, Entity entity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("contentCode", INITIAL_ENTITY_CONF);
        objectNode.put("portNumber", portNumber);
        objectNode.put("type", entity.getType());
        objectNode.put("footprint", entity.getFootprint());

        String parsedJSONMessage = objectNode.toString();

        return parsedJSONMessage;
    }

    public String createEntityConnectionMessage(Entity entity) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("contentCode", INITIAL_ENTITY_CONF);
        objectNode.put("type", entity.getType());
        objectNode.put("footprint", entity.getFootprint());

        return objectNode.toString();
    }

    public String createInterfaceMessage(InterfaceMessage interfaceMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(interfaceMessage);
    }

    public String createResultMessage(Entity entity, Integer result, String receiptAcknowledgment) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("contentCode", RESULT);
        objectNode.put("origin", entity.getType());
        objectNode.put("originFootprint", entity.getFootprint());
        objectNode.put("receiptAcknowledgment", receiptAcknowledgment);
        objectNode.put("result", result);

        return objectNode.toString();
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
