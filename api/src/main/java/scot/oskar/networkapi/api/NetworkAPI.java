package scot.oskar.networkapi.api;

import scot.oskar.networkapi.api.database.DatabaseProvider;
import scot.oskar.networkapi.api.database.DatabaseService;

public class NetworkAPI {
    private static NetworkAPI instance;
    private final Network network;
    private final DatabaseService databaseService;
    private final DatabaseProvider databaseProvider;

    public NetworkAPI(Network network, DatabaseService databaseService, DatabaseProvider databaseProvider) {
        this.network = network;
        this.databaseService = databaseService;
        this.databaseProvider = databaseProvider;
    }


    public static void setInstance(NetworkAPI instance) {
        if (NetworkAPI.instance != null) {
            throw new IllegalStateException("NetworkAPI is initialized");
        }
        NetworkAPI.instance = instance;
    }

    public static Network getNetwork() {
        return instance.network;
    }

    public static DatabaseService getDatabaseService() {
        return instance.databaseService;
    }

    public static DatabaseProvider getDatabaseProvider() {
        return instance.databaseProvider;
    }
}
