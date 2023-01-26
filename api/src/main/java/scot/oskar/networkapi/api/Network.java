package scot.oskar.networkapi.api;

import scot.oskar.networkapi.api.serializer.Serializer;

public interface Network {
    <T> void registerDatabaseSerializer(Class<T> type, Serializer<T> serializer);
}
