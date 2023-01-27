package scot.oskar.networkapi.core.database.provider;

import com.google.inject.Inject;
import scot.oskar.networkapi.core.configuration.DatabaseConfiguration;
import scot.oskar.networkapi.api.database.DatabaseProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MariaDBDatabaseProvider implements DatabaseProvider {

  private Connection connection;
  private final DatabaseConfiguration configuration;
  private final Logger logger;

  @Inject
  public MariaDBDatabaseProvider(DatabaseConfiguration configuration, Logger logger){
    this.configuration = configuration;
    this.logger = logger;
    if (!configuration.isDisableLogger()) {
      System.out.println("MariaDBDatabaseProvider created: " + configuration);
    }
  }

  @Override
  public void connect() {
    try {
      connection = DriverManager.getConnection(
              "jdbc:mariadb://" + configuration.getHost() + ":" + configuration.getPort() + "/" + configuration.getDatabase(),
              configuration.getUsername(),
              configuration.getPassword()
      );
      if (!configuration.isDisableLogger()) {
        logger.info("MariaDBDatabaseProvider connected: " + connection);
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
