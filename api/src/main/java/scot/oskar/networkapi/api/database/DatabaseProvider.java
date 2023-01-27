package scot.oskar.networkapi.api.database;

import java.sql.Connection;

public interface DatabaseProvider {

  void connect();

  void disconnect();

  Connection getConnection();

}
