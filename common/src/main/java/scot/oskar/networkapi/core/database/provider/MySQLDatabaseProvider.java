package scot.oskar.networkapi.core.database.provider;

import java.sql.Connection;
import scot.oskar.networkapi.core.database.DatabaseProvider;

public class MySQLDatabaseProvider implements DatabaseProvider {

  @Override
  public void connect() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void disconnect() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Connection getConnection() {
    return null;
  }
}
