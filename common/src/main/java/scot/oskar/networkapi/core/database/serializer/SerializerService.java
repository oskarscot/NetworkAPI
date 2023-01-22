package scot.oskar.networkapi.core.database.serializer;

import java.util.HashMap;
import java.util.Map;

public class SerializerService {

  private final Map<Class<?>, Serializer<?>> serializerMap = new HashMap<>();

  public <T> void registerSerializer(Class<T> clazz, Serializer<T> serializer) {
    serializerMap.put(clazz, serializer);
  }

  public <T> Serializer<T> getSerializer(Class<T> clazz) {
    return (Serializer<T>) serializerMap.get(clazz);
  }

  public Map<Class<?>, Serializer<?>> getSerializerMap() {
    return serializerMap;
  }
}
