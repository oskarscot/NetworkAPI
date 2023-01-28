package scot.oskar.networkapi.paper;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import scot.oskar.networkapi.core.NetworkAPI;
import scot.oskar.networkapi.paper.serializer.ItemStackSerializer;

import java.io.File;

public class NetworkAPIPlugin extends JavaPlugin {

    private NetworkAPI networkAPI;
    @Override
    public void onEnable() {
        networkAPI = NetworkAPI.builder().build(new File(getDataFolder(), "config.json").toPath());
        networkAPI.registerDatabaseSerializer(ItemStack.class, new ItemStackSerializer());
    }

    @Override
    public void onDisable() {
        networkAPI.getDatabaseProvider().disconnect();
    }

}
