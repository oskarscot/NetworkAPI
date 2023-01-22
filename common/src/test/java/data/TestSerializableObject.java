package data;

public class TestSerializableObject {

  private int level;
  private int exp;

  public TestSerializableObject(int level, int exp) {
    this.level = level;
    this.exp = exp;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getExp() {
    return exp;
  }

  public void setExp(int exp) {
    this.exp = exp;
  }

  @Override
  public String toString() {
    return "TestSerializableObject{" +
        "level=" + level +
        ", exp=" + exp +
        '}';
  }
}
