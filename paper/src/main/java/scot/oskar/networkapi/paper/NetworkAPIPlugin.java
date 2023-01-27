package scot.oskar.networkapi.paper;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import scot.oskar.networkapi.core.NetworkAPI;
import scot.oskar.networkapi.paper.serializer.ItemStackSerializer;

import java.io.File;

public class NetworkAPIPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        NetworkAPI networkAPI = NetworkAPI.builder().build(new File(getDataFolder(), "config.yml").toPath());
        networkAPI.registerDatabaseSerializer(ItemStack.class, new ItemStackSerializer());
    }
}
