package scot.oskar.networkapi.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import scot.oskar.networkapi.core.NetworkAPI;

import java.io.File;

public class NetworkAPIPlugin extends Plugin {
    private NetworkAPI networkAPI;
    @Override
    public void onEnable() {
        networkAPI = NetworkAPI.builder().build(new File(getDataFolder(), "config.json").toPath());
    }

    @Override
    public void onDisable() {
        networkAPI.getDatabaseProvider().disconnect();
    }
}
