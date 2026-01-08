package net.zffu.hollowboard;

import net.minecraft.server.level.EntityPlayer;
import net.zffu.hollowboard.board.BoardLine;
import net.zffu.hollowboard.board.HollowBoard;
import net.zffu.hollowboard.task.BoardTickingManager;
import net.zffu.hollowboard.utils.ClientSideScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class HollowBoardPlugin extends JavaPlugin implements Listener {

    public static HollowBoardPlugin INSTANCE;

    public static HashMap<UUID, ClientSideScoreboard> boards = new HashMap<>();
    public static HashMap<UUID, HollowPlayer> players = new HashMap<>();

    public HollowBoard testBoard;

    public BoardTickingManager tickingManager;


    @Override
    public void onEnable() {
        INSTANCE = this;

        this.tickingManager = new BoardTickingManager();

        this.testBoard = new HollowBoard("§c§lZote's return");

        BoardLine firstLine = BoardLine.compileLine("§b§lZote's the might: §r{bob::%player%}");

        BoardLine secondLine = BoardLine.compileLine("§etest.zffu.dev");

        this.testBoard.append(firstLine);
        this.testBoard.append(BoardLine.empty);
        this.testBoard.append(secondLine);


        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        HollowPlayer player = new HollowPlayer(event.getPlayer());

        player.setCurrentBoard(this.testBoard);

        players.put(event.getPlayer().getUniqueId(), player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HollowPlayer player = players.get(event.getPlayer().getUniqueId());

        player.setCurrentBoard(null);

        players.remove(event.getPlayer().getUniqueId());
    }

    @Override
    public void onDisable() {

    }
}
