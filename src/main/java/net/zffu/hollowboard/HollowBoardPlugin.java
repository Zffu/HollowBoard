package net.zffu.hollowboard;

import org.bukkit.plugin.java.JavaPlugin;

public final class HollowBoardPlugin extends JavaPlugin {

    public static HollowBoardPlugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
