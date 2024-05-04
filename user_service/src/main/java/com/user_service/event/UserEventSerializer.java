package com.user_service.event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class UserEventSerializer extends JsonSerializer<UserEvent> {
    @Override
    public void serialize(UserEvent value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("eventType", value.getEventType());
        gen.writeStringField("userId", value.getUserId());
        gen.writeStringField("userName", value.getUserName());
        gen.writeStringField("message", value.getMessage());
        gen.writeEndObject();
    }
}
