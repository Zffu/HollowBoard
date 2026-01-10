package net.zffu.hollowboard.spigot;

import net.zffu.hollowboard.HollowBoardAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class HollowBoardSpigotPlugin extends JavaPlugin {

    public static HollowBoardSpigotPlugin instance;

    public HollowBoardAPI api;
    public SpigotPlatform platform;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("Loading HollowBoard API platform...");
        this.platform = new SpigotPlatform();
        this.api = new HollowBoardAPI(this.platform);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
