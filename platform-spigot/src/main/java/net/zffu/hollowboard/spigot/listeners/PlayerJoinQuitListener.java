package net.zffu.hollowboard.spigot.listeners;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketEvent;
import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.spigot.HollowBoardSpigotPlugin;
import net.zffu.hollowboard.spigot.packets.SpigotScoreboardController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        HollowPlayer player = new HollowPlayer(new SpigotScoreboardController(PacketEvents.getAPI().getPlayerManager().getUser(event.getPlayer()), ""));

        HollowBoardSpigotPlugin.instance.platform.addPlayer(event.getPlayer().getUniqueId(), player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HollowBoardSpigotPlugin.instance.platform.removePlayer(event.getPlayer().getUniqueId());
    }

}
