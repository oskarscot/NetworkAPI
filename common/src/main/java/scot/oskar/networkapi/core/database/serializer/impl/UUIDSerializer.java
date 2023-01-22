package scot.oskar.networkapi.core.database.serializer.impl;

import java.util.UUID;
import scot.oskar.networkapi.core.database.serializer.Serializer;

public class UUIDSerializer implements Serializer<UUID> {

  @Override
  public String serialize(Object object) {
    return object.toString();
  }

  @Override
  public UUID deserialize(String string, Class<?> clazz) {
    return UUID.fromString(string);
  }
}
