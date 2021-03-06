package tests.JSONMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import main.Enum.EnumContentCode;
import main.Enum.EnumType;
import main.JSONMessage.JSONMessage;
import main.JSONMessage.JSONMessageSerializer;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class JSONMessageTest {

    @Test
    void testToSetAllParametersAndGetACorrectJSON() throws JsonProcessingException {
        JSONMessageSerializer jsonMessageSerializer = new JSONMessageSerializer(JSONMessage.class);
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule("JSONMessageSerializer");
        module.addSerializer(JSONMessage.class, jsonMessageSerializer);

        objectMapper.registerModule(module);

        // Create an instance of JSONMessage and set parameters
        JSONMessage message = new JSONMessage();
        message.setContentCode(EnumContentCode.NOTDECLARED);
        message.setFolio("folio");
        message.setOriginFootprint("originFootprint");
        message.setTransmitterType(EnumType.NOTDECLARED);
        message.setMessage("This is a message");

        String jsonMessage = objectMapper.writeValueAsString(message);
        String expectedJSON = getJSONFromFile("AllParametersSet.json");

        JSONAssert.assertEquals(expectedJSON, jsonMessage, false);
    }

    private String getJSONFromFile(String filename) {
        String JSONText = "";
        try {
            URL path = JSONMessageTest.class.getResource(filename);
            JSONText = new Scanner(new File(path.getFile()))
                    .useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return JSONText;
    }
}