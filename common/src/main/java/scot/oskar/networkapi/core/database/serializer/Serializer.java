package scot.oskar.networkapi.core.database.serializer;

public interface Serializer<T> {

  String serialize(Object object);

   T deserialize(String string, Class<?> clazz);

}
