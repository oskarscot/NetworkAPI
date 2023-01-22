package scot.oskar.networkapi.core.module;

import com.google.inject.AbstractModule;
import java.util.logging.Logger;

public class LoggerModule extends AbstractModule {

    @Override
    protected void configure() {
      Logger logger = Logger.getLogger("NetworkAPI");
      bind(Logger.class).toInstance(logger);
    }

}
