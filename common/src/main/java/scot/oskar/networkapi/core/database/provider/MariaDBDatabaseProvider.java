package scot.oskar.networkapi.core.database.provider;

import com.google.inject.Inject;
import scot.oskar.networkapi.core.configuration.DatabaseConfiguration;
import scot.oskar.networkapi.api.database.DatabaseProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBDatabaseProvider implements DatabaseProvider {

  private Connection connection;
  private final DatabaseConfiguration configuration;

  @Inject
  public MariaDBDatabaseProvider(DatabaseConfiguration configuration){
    this.configuration = configuration;
    System.out.println("MariaDBDatabaseProvider created: " + configuration);
  }

  @Override
  public void connect() {
    try {
      connection = DriverManager.getConnection(
              "jdbc:mariadb://" + configuration.getHost() + ":" + configuration.getPort() + "/" + configuration.getDatabase(),
              configuration.getUsername(),
              configuration.getPassword()
      );

      System.out.println("MariaDBDatabaseProvider connected: " + connection);
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
      System.err.println("Failed to close connection to the database.");
      e.printStackTrace();
    }
  }

  @Override
  public Connection getConnection() {
    return connection;
  }
}
