package tests.JSONMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import main.Enum.EnumContentCode;
import main.Enum.EnumType;
import main.JSONMessage.JSONMessage;
import main.JSONMessage.MessageSerializer;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

class MessageTest {

    @Test
    void testToSetAllParametersAndGetACorrectJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapperWithModule("MessageSerializer");

        // Create an instance of Message and set parameters
        JSONMessage jsonMessage = new JSONMessage();
        jsonMessage.header.setContentCode(EnumContentCode.NOTDECLARED);
        jsonMessage.header.setFolio("folio");
        jsonMessage.header.setOriginFootprint("originFootprint");
        jsonMessage.header.setTransmitterType(EnumType.NOTDECLARED);
        jsonMessage.message.setMessage("This is a message");

        String parsedJSONMessage = objectMapper.writeValueAsString(jsonMessage);
        String expectedJSON = getJSONFromFile("./JSONTestFiles/AllParametersSet.json");

        JSONAssert.assertEquals(expectedJSON, parsedJSONMessage, false);
    }

    //TODO Research a way to ignore null fields on the class
//    @Test
//    void testToSetSomeParametersAndGetACorrectJSON() throws JsonProcessingException {
//        ObjectMapper objectMapper = getObjectMapperWithModule("MessageSerializer");
//
//        // Create an instance of Message and set parameters
//        JSONMessage jsonMessage = new JSONMessage();
//        jsonMessage.header.setTransmitterType(EnumType.NOTDECLARED);
//        jsonMessage.message.setMessage("This is a message");
//
//        String mappedJSONMessage = objectMapper.writeValueAsString(jsonMessage);
//        String expectedJSON = getJSONFromFile("./JSONTestFiles/SomeParametersSet.json");
//
//        JSONAssert.assertEquals(expectedJSON, mappedJSONMessage, false);
//    }

    private ObjectMapper getObjectMapperWithModule(String moduleName) {
        MessageSerializer messageSerializer = new MessageSerializer(JSONMessage.class);
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule(moduleName);
        module.addSerializer(JSONMessage.class, messageSerializer);

        objectMapper.registerModule(module);

        return objectMapper;
    }

    private String getJSONFromFile(String filename) {
        String JSONText = "";
        try {
            URL path = MessageTest.class.getResource(filename);
            JSONText = new Scanner(new File(path.getFile()))
                    .useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return JSONText;
    }
}