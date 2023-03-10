package scot.oskar.networkapi.core.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import scot.oskar.networkapi.core.database.DatabaseType;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class DatabaseConfiguration extends OkaeriConfig {

  private String host = "localhost";
  private int port = 5432;
  private String database = "network_api";
  private String username = "postgres";
  private String password = "dupa";
  @Comment({"POSTGRESQL", "MARIADB", "MYSQL"})
  private DatabaseType type = DatabaseType.POSTGRESQL;
  private boolean disableLogger = true;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public boolean isDisableLogger() {
    return disableLogger;
  }

  public DatabaseType getType() {
    return type;
  }

  public void setType(DatabaseType type) {
    this.type = type;
  }

  public void setDisableLogger(boolean disableLogger) {
    this.disableLogger = disableLogger;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "DatabaseConfiguration{" +
        ", host='" + host + '\'' +
        ", port=" + port +
        ", database='" + database + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
