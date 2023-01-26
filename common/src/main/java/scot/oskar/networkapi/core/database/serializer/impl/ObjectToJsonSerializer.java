package scot.oskar.networkapi.core.database.serializer.impl;

import com.google.gson.Gson;
import scot.oskar.networkapi.api.serializer.Serializer;

public class ObjectToJsonSerializer<T> implements Serializer<T> {

    public Gson gson = new Gson();

    @Override
    public String serialize(Object object) {
        return gson.toJson(object);
    }

    @Override
    public T deserialize(String string, Class<?> clazz) {
        return (T) gson.fromJson(string, clazz);
    }
}
