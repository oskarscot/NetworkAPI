package scot.oskar.networkapi.core.module;

import com.google.inject.AbstractModule;
import scot.oskar.networkapi.core.database.serializer.SerializerService;

public class SerializerModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(SerializerService.class).toInstance(new SerializerService());
    }

}
