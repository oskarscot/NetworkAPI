package scot.oskar.networkapi.core;

import com.google.gson.Gson;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;
import scot.oskar.networkapi.core.configuration.DatabaseConfiguration;
import scot.oskar.networkapi.core.database.DatabaseProvider;
import scot.oskar.networkapi.core.database.DatabaseService;
import scot.oskar.networkapi.core.database.provider.PostgreSQLProvider;
import scot.oskar.networkapi.core.database.serializer.Serializer;
import scot.oskar.networkapi.core.database.serializer.SerializerService;
import scot.oskar.networkapi.core.database.serializer.impl.UUIDSerializer;
import scot.oskar.networkapi.core.module.GsonModule;
import scot.oskar.networkapi.core.module.OkaeriConfigModule;
import scot.oskar.networkapi.core.module.SerializerModule;

public class NetworkAPI implements Module {

  private Class<? extends DatabaseProvider> databaseProvider;
  private Injector injector;
  private DatabaseService service;
  private SerializerService serializerService;
  private Gson gson;
  private Logger logger;

  private NetworkAPI() {
  }

  @Override
  public void configure(Binder binder) {
    binder.bind(NetworkAPI.class).toInstance(this);
  }

  public DatabaseService getDatabaseService() {
    return service;
  }

  public SerializerService getSerializerService() {
    return serializerService;
  }

  public Gson getGson() {
    return gson;
  }

  public Logger getLogger() {
    return logger;
  }

  public static NetworkAPI builder(){
    return new NetworkAPI();
  }

  public static NetworkAPI buildDefault(){
    return new NetworkAPI().build();
  }

  public NetworkAPI setDatabaseProvider(Class<? extends DatabaseProvider> databaseProvider) {
    this.databaseProvider = databaseProvider;
    return this;
  }

  public <T> void registerDatabaseSerializer(Class<T> type, Serializer<T> serializer) {
    Objects.requireNonNull(serializerService, "Serializer service is not initialized");
    if(serializerService.getSerializer(type) != null) {
      this.logger.warning("Serializer for type " + type.getSimpleName() + " already exists");
    }
    serializerService.registerSerializer(type, serializer);
  }

  public NetworkAPI build(){
    injector = Guice.createInjector(
        new OkaeriConfigModule<>(null, DatabaseConfiguration.class),
        new SerializerModule(),
        new GsonModule(),
        binder -> binder.bind(DatabaseProvider.class).to(Objects.requireNonNullElse(databaseProvider, PostgreSQLProvider.class))
    );
    this.service = injector.getInstance(DatabaseService.class);
    this.serializerService = injector.getInstance(SerializerService.class);
    this.logger = Logger.getLogger("NetworkAPI");
    this.gson = injector.getInstance(Gson.class);

    // Register default serializers
    registerDatabaseSerializer(UUID.class, new UUIDSerializer());
    return this;
  }
}
