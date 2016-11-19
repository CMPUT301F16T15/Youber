package com.youber.cmput301f16t15.youber.commands;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


import java.lang.reflect.Type;

/**
 * Created by Jess on 2016-11-19.
 */

public class CommandSerializer implements JsonSerializer<Command>, JsonDeserializer<Command> {
    @Override
    public Command deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject wrapper = (JsonObject)jsonElement;
        final JsonElement typeName = get(wrapper, "type");
        final JsonElement data = get(wrapper, "data");
        final Type actualType = typeForName(typeName);

        return jsonDeserializationContext.deserialize(data, actualType);
    }

    @Override
    public JsonElement serialize(Command command, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject wrapper =  new JsonObject();
        wrapper.addProperty("type", command.getClass().getName());
        wrapper.add("data", jsonSerializationContext.serialize(command));

        return wrapper;
    }

    private Type typeForName(final JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if(elem == null)
            throw new JsonParseException("no " + memberName + " member found in what was expected to be an interface wrapper");

        return elem;
    }
}
