package main.JSONMessage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class JSONMessageSerializer extends StdSerializer<JSONMessage> {

    public JSONMessageSerializer(Class<JSONMessage> jsonMessageClass) {
        super(jsonMessageClass);
    }

    public void serialize(JSONMessage message, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeObjectFieldStart("header");
        jsonGenerator.writeNumberField("tipoEntidadEmisora", message.getTransmitterType());
        jsonGenerator.writeStringField("folio", message.getFolio());
        jsonGenerator.writeStringField("origen", message.getOriginFootprint());
        jsonGenerator.writeNumberField("contentCode", message.getContentCode());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeStringField("mensaje", message.getMessage());

        jsonGenerator.close();
    }

}
