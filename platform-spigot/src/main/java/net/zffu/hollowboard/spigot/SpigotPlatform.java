package net.zffu.hollowboard.spigot;

import net.zffu.hollowboard.HollowPlatform;
import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.platforms.TickingManager;
import net.zffu.hollowboard.spigot.tasks.SpigotScheduler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class SpigotPlatform implements HollowPlatform {

    private final HashMap<UUID, HollowPlayer> players;
    private final TickingManager tickingManager;

    private SpigotScheduler scheduler;

    private JavaPlugin plugin;

    public SpigotPlatform(JavaPlugin plugin) {
        this.plugin = plugin;

        this.players = new HashMap<>();
        this.scheduler = new SpigotScheduler(this.plugin);
        this.tickingManager = new TickingManager(this.scheduler);
    }

    @Override
    public @Nullable HollowPlayer getPlayer(@NotNull UUID playerUUID) {
        return players.get(playerUUID);
    }

    @Override
    public void appendUpdatablePlayer(HollowPlayer player) {
        this.tickingManager.appendPlayer(player);
    }

    @Override
    public void removeUpdatablePlayer(HollowPlayer player) {
        this.tickingManager.removePlayer(player);
    }

    public void removePlayer(UUID playerUUID) {
        players.remove(playerUUID);
    }

    public void addPlayer(UUID playerUUID, HollowPlayer player) {
        players.put(playerUUID, player);
    }

}
