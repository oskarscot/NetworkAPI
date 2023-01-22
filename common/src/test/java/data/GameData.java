package data;

public class GameData {

  private String gamemode;
  private int kills;

  public GameData(String gamemode, int kills) {
    this.gamemode = gamemode;
    this.kills = kills;
  }

  public String getGamemode() {
    return gamemode;
  }

  public int getKills() {
    return kills;
  }


  @Override
  public String toString() {
    return "GameData{" +
        "gamemode='" + gamemode + '\'' +
        ", kills=" + kills +
        '}';
  }
}
