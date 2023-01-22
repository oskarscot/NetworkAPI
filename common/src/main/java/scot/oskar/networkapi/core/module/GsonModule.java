package scot.oskar.networkapi.core.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;

public class GsonModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(Gson.class).toInstance(new GsonBuilder().setPrettyPrinting().create());
    }

}
