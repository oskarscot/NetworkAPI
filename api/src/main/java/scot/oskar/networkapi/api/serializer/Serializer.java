package scot.oskar.networkapi.api.serializer;

public interface Serializer<T> {

  String serialize(Object object);

   T deserialize(String string, Class<?> clazz);

}
