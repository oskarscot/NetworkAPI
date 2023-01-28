package scot.oskar.networkapi.core;

import com.google.gson.Gson;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import scot.oskar.networkapi.api.Network;
import scot.oskar.networkapi.api.database.DatabaseProvider;
import scot.oskar.networkapi.api.serializer.Serializer;
import scot.oskar.networkapi.core.configuration.DatabaseConfiguration;
import scot.oskar.networkapi.core.database.DatabaseServiceImpl;
import scot.oskar.networkapi.core.database.DatabaseType;
import scot.oskar.networkapi.core.database.provider.PostgreSQLProvider;
import scot.oskar.networkapi.core.database.serializer.SerializerService;
import scot.oskar.networkapi.core.database.serializer.impl.UUIDSerializer;
import scot.oskar.networkapi.core.module.GsonModule;
import scot.oskar.networkapi.core.module.OkaeriConfigModule;
import scot.oskar.networkapi.core.module.SerializerModule;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

public class NetworkAPI implements Module, Network {

    private Class<? extends DatabaseProvider> databaseProvider;
    private Injector injector;
    private DatabaseServiceImpl service;
    private SerializerService serializerService;
    private Gson gson;
    private Logger logger;

    private NetworkAPI() {
    }

    public static NetworkAPI builder() {
        return new NetworkAPI();

    }

    public static NetworkAPI buildDefault() {
        return new NetworkAPI().build(null);
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(NetworkAPI.class).toInstance(this);
    }

    public DatabaseServiceImpl getDatabaseService() {
        return service;
    }

    public SerializerService getSerializerService() {
        return serializerService;
    }
    public DatabaseProvider getDatabaseProvider() {
        return injector.getInstance(DatabaseProvider.class);
    }

    public Gson getGson() {
        return gson;
    }

    public Logger getLogger() {
        return logger;
    }

    public NetworkAPI setDatabaseProvider(DatabaseType type) {
        this.databaseProvider = type.getDatabaseProvider();
        return this;
    }

    public NetworkAPI setDatabaseProvider(Class<? extends DatabaseProvider> clazz) {
        this.databaseProvider = clazz;
        return this;
    }


    @Override
    public <T> void registerDatabaseSerializer(Class<T> type, Serializer<T> serializer) {
        Objects.requireNonNull(serializerService, "Serializer service is not initialized");
        if (serializerService.getSerializer(type) != null) {
            this.logger.warning("Serializer for type " + type.getSimpleName() + " already exists");
        }
        serializerService.registerSerializer(type, serializer);
    }

    public NetworkAPI build(Path path) {
        injector = Guice.createInjector(
                new OkaeriConfigModule<>(path, DatabaseConfiguration.class),
                new SerializerModule(),
                new GsonModule()
        );
        injector = injector.createChildInjector(binder -> binder.bind(DatabaseProvider.class).to(Optional.ofNullable(databaseProvider).isPresent() ? databaseProvider : injector.getInstance(DatabaseConfiguration.class).getType().getDatabaseProvider()));

        this.service = injector.getInstance(DatabaseServiceImpl.class);
        this.serializerService = injector.getInstance(SerializerService.class);
        this.logger = Logger.getLogger("NetworkAPI");
        this.gson = injector.getInstance(Gson.class);

        // Register default serializers
        registerDatabaseSerializer(UUID.class, new UUIDSerializer());
        scot.oskar.networkapi.api.NetworkAPI.setInstance(new scot.oskar.networkapi.api.NetworkAPI(this, service, injector.getInstance(DatabaseProvider.class)));
        return this;
    }
}
