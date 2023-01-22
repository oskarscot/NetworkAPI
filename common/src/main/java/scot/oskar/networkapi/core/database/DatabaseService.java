package scot.oskar.networkapi.core.database;

import com.google.inject.Inject;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import scot.oskar.networkapi.core.database.annotation.DatabaseField;
import scot.oskar.networkapi.core.database.annotation.Id;
import scot.oskar.networkapi.core.database.annotation.TableName;
import scot.oskar.networkapi.core.database.serializer.Serializer;
import scot.oskar.networkapi.core.database.serializer.SerializerService;

public class DatabaseService {

  private final DatabaseProvider databaseProvider;
  private final SerializerService serializerService;

  private final String createTableFormat = "create table if not exists %s (%s);";
  private final String insertFormat = "insert into %s (%s) values ('%s');";
  private final String selectFormat = "select %s from %s;";
  private final String deleteFormat = "delete from %s where %s = %s;";
  private final String dropFormat = "drop table %s;";
  private final String deleteFromFormat = "delete from %s;";

  @Inject
  public DatabaseService(DatabaseProvider provider, SerializerService serializerService) {
    this.databaseProvider = provider;
    this.serializerService = serializerService;
    provider.connect();
    System.out.println("DatabaseService created: " + provider);
  }

  public <T> void createEntity(T entity) {
    final Class<?> clazz = entity.getClass();
    final List<DatabaseField> typedFieldList = List.ofAll(Arrays.stream(clazz.getDeclaredFields()))
        .filter(field -> field.isAnnotationPresent(DatabaseField.class) && !field.isAnnotationPresent(Id.class))
        .map(field -> field.getDeclaredAnnotation(DatabaseField.class));

    String patternColumn = typedFieldList
        .map(DatabaseField::columnName)
        .mkString(", ");

    String patternValue = typedFieldList
        .map(databaseField -> Try.of(() -> clazz.getDeclaredField(databaseField.columnName()))
            .peek(f -> f.setAccessible(true))
            .map(Function1.of(f -> Try.of(() -> {
              final Serializer<?> serializer = serializerService.getSerializer(f.getType());
              if(serializer != null){
                return serializer.serialize(f.get(entity));
              } else {
                return f.get(entity).toString();
              }
            }).getOrElse("null")))
            .getOrElse("null"))
        .mkString("', '");

    final String tableName = clazz.getAnnotation(TableName.class).value();
    if(tableName == null || tableName.isEmpty()){
      throw new IllegalArgumentException("TableName annotation is not present on class " + clazz.getName());
    }

    String query = String.format(insertFormat, tableName, patternColumn, patternValue);

    System.out.println("Query: " + query);

    Future.run(() -> {
          Statement statement = databaseProvider.getConnection().createStatement();

          statement.executeUpdate(query);
          statement.close();
    })
    .onFailure(Throwable::printStackTrace);

  }

  public <T> void createTable(Class<T> clazz){
    final List<DatabaseField> typedFieldList = List.ofAll(Arrays.stream(clazz.getDeclaredFields()))
        .filter(field -> field.isAnnotationPresent(DatabaseField.class))
        .map(field -> field.getDeclaredAnnotation(DatabaseField.class));

    String pattern = typedFieldList
        .map(databaseField -> Try.of(() -> clazz.getDeclaredField(databaseField.columnName()))
            .peek(f -> f.setAccessible(true))
            .filter(f -> f.isAnnotationPresent(Id.class))
            .map(Function1.of(f -> databaseField.columnName() + " " + databaseField.columnType() + " primary key"))
            .getOrElse(databaseField.columnName() + " " + databaseField.columnType()))
        .mkString(", ");

    final String tableName = clazz.getAnnotation(TableName.class).value();
    if(tableName == null || tableName.isEmpty()){
      throw new IllegalArgumentException("TableName annotation is not present on class " + clazz.getName());
    }

    String query = String.format(createTableFormat, tableName, pattern);

    System.out.println("Query: " + query);

    Future.run(() -> {
          Statement statement = databaseProvider.getConnection().createStatement();

          statement.executeUpdate(query);
          statement.close();
        })
        .onFailure(Throwable::printStackTrace);
  }

  public <T> java.util.List<T> getAll(Class<T> clazz) {
    final List<DatabaseField> typedFieldList = List.ofAll(Arrays.stream(clazz.getDeclaredFields()))
        .filter(field -> field.isAnnotationPresent(DatabaseField.class))
        .map(field -> field.getDeclaredAnnotation(DatabaseField.class));

    String pattern = typedFieldList
        .map(DatabaseField::columnName)
        .mkString(", ");

    final String tableName = clazz.getAnnotation(TableName.class).value();
    if(tableName == null || tableName.isEmpty()){
      throw new IllegalArgumentException("TableName annotation is not present on class " + clazz.getName());
    }

    String query = String.format(selectFormat, pattern, tableName);

    ResultSet resultSet = Future.of(() -> {
              Statement statement = databaseProvider.getConnection().createStatement();
              return statement.executeQuery(query);
            })
        .getOrElseThrow(() -> new RuntimeException("Error while getting all entities"));


    List<T> list = List.empty();

    try {
      while (resultSet.next()) {
        T entity = Try.of(() -> clazz.getDeclaredConstructor().newInstance())
            .getOrElseThrow(() -> new RuntimeException("Error while creating new instance of class " + clazz.getName() + "\nIs empty constructor present?"));
        for (DatabaseField databaseField : typedFieldList) {
          final Field declaredField = clazz.getDeclaredField(databaseField.columnName());
          final Serializer<?> serializer = serializerService.getSerializer(declaredField.getType());
          declaredField.setAccessible(true);
          if(serializer != null){
            declaredField.set(entity, serializer.deserialize(resultSet.getString(databaseField.columnName()), declaredField.getType()));
          } else {
            declaredField.set(entity, resultSet.getObject(databaseField.columnName()));
          }
        }
        list = list.append(entity);
      }
    } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }

    return list.toJavaList();
  }

  public <T> T get(Class<T> clazz, String key, String id) {
    final List<DatabaseField> typedFieldList = List.ofAll(Arrays.stream(clazz.getDeclaredFields()))
        .filter(field -> field.isAnnotationPresent(DatabaseField.class))
        .map(field -> field.getDeclaredAnnotation(DatabaseField.class));

    String pattern = typedFieldList
        .map(DatabaseField::columnName)
        .mkString(", ");

    final String tableName = clazz.getAnnotation(TableName.class).value();
    if(tableName == null || tableName.isEmpty()){
      throw new IllegalArgumentException("TableName annotation is not present on class " + clazz.getName());
    }

    String query = String.format(selectFormat, "*", tableName + " where " + key + " = " + id);

    System.out.println(query);
    return null;
  }

  public void dropTable(Class<?> clazz) {
    final String tableName = clazz.getAnnotation(TableName.class).value();
    if(tableName == null || tableName.isEmpty()){
      throw new IllegalArgumentException("TableName annotation is not present on class " + clazz.getName());
    }

    String query = String.format(dropFormat, tableName);

    System.out.println("Query: " + query);

    Future.run(() -> {
          Statement statement = databaseProvider.getConnection().createStatement();

          statement.executeUpdate(query);
          statement.close();
        })
        .onFailure(Throwable::printStackTrace);
  }

  public void deleteAll(Class<?> clazz) {
    final String tableName = clazz.getAnnotation(TableName.class).value();
    if(tableName == null || tableName.isEmpty()){
      throw new IllegalArgumentException("TableName annotation is not present on class " + clazz.getName());
    }

    String query = String.format(deleteFromFormat, tableName);

    System.out.println("Query: " + query);

    Future.run(() -> {
          Statement statement = databaseProvider.getConnection().createStatement();

          statement.executeUpdate(query);
          statement.close();
        })
        .onFailure(Throwable::printStackTrace);
  }
}
