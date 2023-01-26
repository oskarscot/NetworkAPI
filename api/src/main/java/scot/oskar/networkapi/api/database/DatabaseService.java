package scot.oskar.networkapi.api.database;

public interface DatabaseService {
    <T> void createEntity(T entity);

    <T> void createTable(Class<T> clazz);

    <T> java.util.List<T> getAll(Class<T> clazz);

    <T> T get(Class<T> clazz, String key, String id);

    void dropTable(Class<?> clazz);

    void deleteAll(Class<?> clazz);

}
