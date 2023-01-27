package scot.oskar.networkapi.core.database.provider;

import com.google.inject.Inject;
import scot.oskar.networkapi.api.database.DatabaseProvider;
import scot.oskar.networkapi.core.configuration.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MySQLDatabaseProvider implements DatabaseProvider {

    private final DatabaseConfiguration configuration;
    private final Logger logger;
    private Connection connection;

    @Inject
    public MySQLDatabaseProvider(DatabaseConfiguration configuration, Logger logger) {
        this.configuration = configuration;
        this.logger = logger;
        if (!configuration.isDisableLogger()) {
            System.out.println("MySQLDatabaseProvider created: " + configuration);
        }
    }

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + configuration.getHost() + ":" + configuration.getPort() + "/" + configuration.getDatabase(),
                    configuration.getUsername(),
                    configuration.getPassword()
            );
            if (!configuration.isDisableLogger()) {
                logger.info("MySQLDatabaseProvider connected: " + connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {

            logger.severe("Failed to close connection to the database.");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
