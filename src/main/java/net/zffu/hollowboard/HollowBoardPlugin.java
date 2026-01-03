package net.zffu.hollowboard;

import net.minecraft.server.level.EntityPlayer;
import net.zffu.hollowboard.utils.ClientSideScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public final class HollowBoardPlugin extends JavaPlugin implements Listener {

    public static HollowBoardPlugin INSTANCE;

    public static HashMap<UUID, ClientSideScoreboard> boards = new HashMap<>();


    @Override
    public void onEnable() {
        INSTANCE = this;

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        EntityPlayer player = ((CraftPlayer)event.getPlayer()).getHandle();

        ClientSideScoreboard scoreboard = new ClientSideScoreboard(player, "Zote is asss");

        scoreboard.change(0, "yeah");
        scoreboard.change(1, "absolute radiance is painn");

        new BukkitRunnable() {
            @Override
            public void run() {
                scoreboard.change(3, UUID.randomUUID().toString());
            }
        }.runTaskTimer(this, 0, 2000);

        long start = System.currentTimeMillis();
        scoreboard.initialSpawn();
        long time = System.currentTimeMillis() - start;

        event.getPlayer().sendMessage("§aScoreboard took " + time + "ms to spawn! uwu");

        boards.put(event.getPlayer().getUniqueId(), scoreboard);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
