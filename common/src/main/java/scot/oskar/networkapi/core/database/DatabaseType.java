package scot.oskar.networkapi.core.database;

import scot.oskar.networkapi.api.database.DatabaseProvider;
import scot.oskar.networkapi.core.database.provider.MariaDBDatabaseProvider;
import scot.oskar.networkapi.core.database.provider.MySQLDatabaseProvider;
import scot.oskar.networkapi.core.database.provider.PostgreSQLProvider;

public enum DatabaseType {
    MYSQL(MySQLDatabaseProvider.class), POSTGRESQL(PostgreSQLProvider.class), MARIADB(MariaDBDatabaseProvider.class) ;
    private final Class<? extends DatabaseProvider> databaseProvider;

    DatabaseType(Class<? extends DatabaseProvider> databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public Class<? extends DatabaseProvider> getDatabaseProvider() {
        return databaseProvider;
    }
}
