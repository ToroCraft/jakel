package net.torocraft.jakel.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@SuppressWarnings("unchecked")
public class EnumAdapter implements JsonDeserializer<Enum> {

  @Override
  public Enum deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context)
      throws JsonParseException {
    try {
      if (type instanceof Class && ((Class<?>) type).isEnum()) {
        return Enum.valueOf((Class<Enum>) type, json.getAsString());
      }
      return null;
    } catch (Exception e) {
      throw new RuntimeException("Invalid " + type + " of " + json.getAsString());
    }
  }
}
