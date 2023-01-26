import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import data.GameData;
import scot.oskar.networkapi.core.NetworkAPI;
import data.TestSerializableObject;
import data.TestUser;
import scot.oskar.networkapi.core.database.DatabaseServiceImpl;
import scot.oskar.networkapi.core.database.serializer.impl.ObjectToJsonSerializer;

public class ORMTest {

  final static NetworkAPI networkAPI = NetworkAPI.buildDefault(null);

  final DatabaseServiceImpl databaseService = networkAPI.getDatabaseService();

  @BeforeAll
  public static void setup() {
    networkAPI.registerDatabaseSerializer(GameData.class, new ObjectToJsonSerializer<>());
    networkAPI.registerDatabaseSerializer(TestSerializableObject.class, new ObjectToJsonSerializer<>());
  }

  @AfterEach
  public void teardown() {
    databaseService.deleteAll(TestUser.class);
  }

  @Test
  void testOrm() {
    //Arrange
    Class<TestUser> clazz = TestUser.class;
    TestUser testUser = new TestUser();
    int numEntities = 10;

    //Act
    databaseService.createTable(clazz);
    databaseService.createEntity(testUser);

    for (int i = 0; i < numEntities; i++) {
      databaseService.createEntity(new TestUser());
    }
    List<TestUser> allEntitiesAfterInserting = databaseService.getAll(clazz);

    allEntitiesAfterInserting.forEach(System.out::println);

    //Assert
    assertNotEquals(0, allEntitiesAfterInserting.size());
  }
}