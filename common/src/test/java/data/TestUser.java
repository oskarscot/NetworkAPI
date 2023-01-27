package data;


import java.util.UUID;
import scot.oskar.networkapi.api.database.annotation.DatabaseField;
import scot.oskar.networkapi.api.database.annotation.Id;
import scot.oskar.networkapi.api.database.annotation.TableName;

@TableName("dupagang")
public class TestUser {

  @DatabaseField(columnName = "id", columnType = "SERIAL")
  @Id
  private int id;

  @DatabaseField(columnName = "name", columnType = "TEXT")
  public String name;

  @DatabaseField(columnName = "uuid", columnType = "TEXT")
  public UUID uuid;

  @DatabaseField(columnName = "email", columnType = "TEXT")
  private String email;

  @DatabaseField(columnName = "levelData", columnType = "TEXT")
  private TestSerializableObject levelData;

  @DatabaseField(columnName = "gameData", columnType = "TEXT")
  private GameData gameData;

  public TestUser() {
    this.name = "Oskar";
    this.uuid = UUID.randomUUID();
    this.email = "dupa@dupa.com";
    this.levelData = new TestSerializableObject(1, 100);
    this.gameData = new GameData("ffa", 100);
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return "TestUser{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", uuid=" + uuid +
        ", email='" + email + '\'' +
        ", levelData=" + levelData +
        ", gameData=" + gameData +
        '}';
  }
}
