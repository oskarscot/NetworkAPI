package scot.oskar.networkapi.core.database;

import java.sql.Connection;

public interface DatabaseProvider {

  void connect();

  void disconnect();

  Connection getConnection();

}
