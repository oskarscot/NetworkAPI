package scot.oskar.networkapi.core.database.provider;

import com.google.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import scot.oskar.networkapi.core.configuration.DatabaseConfiguration;
import scot.oskar.networkapi.core.database.DatabaseProvider;

public class PostgreSQLProvider implements DatabaseProvider {

  private Connection connection;
  private final DatabaseConfiguration configuration;

  @Inject
  public PostgreSQLProvider(DatabaseConfiguration configuration){
    this.configuration = configuration;
    System.out.println("PostgreSQLProvider created: " + configuration);
  }

  @Override
  public void connect() {
    try {
      connection = DriverManager.getConnection(
          "jdbc:postgresql://" + configuration.getHost() + ":" + configuration.getPort() + "/" + configuration.getDatabase(),
          configuration.getUsername(),
          configuration.getPassword()
      );

      System.out.println("PostgreSQLProvider connected: " + connection);
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
