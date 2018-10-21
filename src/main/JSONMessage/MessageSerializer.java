package main.JSONMessage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MessageSerializer extends StdSerializer<JSONMessage> {

    public MessageSerializer(Class<JSONMessage> jsonMessageClass) {
        super(jsonMessageClass);
    }

    public void serialize(JSONMessage jsonMessage, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeObjectFieldStart("header");
        jsonGenerator.writeNumberField("tipoEntidadEmisora", jsonMessage.header.getTransmitterType());
        jsonGenerator.writeStringField("folio", jsonMessage.header.getFolio());
        jsonGenerator.writeStringField("origen", jsonMessage.header.getOriginFootprint());
        jsonGenerator.writeNumberField("contentCode", jsonMessage.header.getContentCode());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeStringField("mensaje", jsonMessage.message.getMessage());

        jsonGenerator.close();

    }

}
