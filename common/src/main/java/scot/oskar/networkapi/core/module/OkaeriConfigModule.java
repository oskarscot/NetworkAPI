package scot.oskar.networkapi.core.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.json.gson.JsonGsonConfigurer;
import java.nio.file.Path;

public class OkaeriConfigModule<T extends OkaeriConfig> extends AbstractModule {

  private final T configuration;
  private final Class<T> configurationClazz;

  public OkaeriConfigModule(Path path, Class<T> configurationClazz) {
    this.configurationClazz = configurationClazz;

    if(path == null) {
      this.configuration = ConfigManager.create(this.configurationClazz);
    } else {
      this.configuration = ConfigManager.create(configurationClazz, (it) -> {
        it.withBindFile(path.toFile());
        it.withConfigurer(new JsonGsonConfigurer());
        it.saveDefaults();
        it.load(true);
      });
    }

  }

  @Override
  protected void configure() {
    bind(TypeLiteral.get(configurationClazz)).toInstance(configuration);
  }
}
