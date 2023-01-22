package scot.oskar.networkapi.core.database.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseField {

  String columnName();
  String columnType();

}
