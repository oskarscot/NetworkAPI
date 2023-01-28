package scot.oskar.networkapi.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import scot.oskar.networkapi.core.NetworkAPI;

import java.io.File;
import java.nio.file.Path;

@Plugin(id = "networkapi", version = "1.0.0", url = "https://github.com/oskarscot/NetworkAPI", authors = {"robalmeister", "oskarscot"})
public class NetworkAPIPlugin {
    @Inject
    @DataDirectory
    Path dataDirectory;
    private NetworkAPI networkAPI;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        networkAPI = networkAPI.build(new File(dataDirectory.toFile(), "config.json").toPath());
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        networkAPI.getDatabaseProvider().disconnect();
    }
}
