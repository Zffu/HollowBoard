package net.zffu.hollowboard.spigot;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import net.zffu.hollowboard.HollowBoardAPI;
import net.zffu.hollowboard.board.HollowBoard;
import net.zffu.hollowboard.board.components.BoardContentLike;
import net.zffu.hollowboard.board.components.DynamicComponent;
import net.zffu.hollowboard.board.lines.DynamicLine;
import net.zffu.hollowboard.spigot.listeners.PlayerJoinQuitListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class HollowBoardSpigotPlugin extends JavaPlugin {

    public static HollowBoardSpigotPlugin instance;

    public HashMap<String, HollowBoard> boards;
    public HashMap<String, BoardContentLike> components;
    public HollowBoard defaultBoard;

    public HollowBoardAPI api;
    public SpigotPlatform platform;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        instance = this;

        PacketEvents.getAPI().init();

        getLogger().info("Loading HollowBoard API platform...");
        this.platform = new SpigotPlatform(this);
        this.api = new HollowBoardAPI(this.platform);

        this.getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);

        getLogger().info("Loading config");
        this.saveDefaultConfig();

        this.boards = new HashMap<>();
        this.components = new HashMap<>();
        this.defaultBoard = null;

        this.loadConfig();
    }

    public void loadConfig() {

        getLogger().info("Loading components...");
        ConfigurationSection components = this.getConfig().getConfigurationSection("components");

        for(String key : components.getKeys(false)) {
            ConfigurationSection component = components.getConfigurationSection(key);

            DynamicComponent dynamic = new DynamicComponent();

            for(String line : component.getStringList("contents")) {
                dynamic.append(DynamicLine.compileLine(line));
            }

            getLogger().info("Loaded component " + key);
            this.components.put(key, dynamic);
        }


        getLogger().info("Loading boards...");
        ConfigurationSection boards = this.getConfig().getConfigurationSection("boards");

        for(String key : boards.getKeys(false)) {
            ConfigurationSection board = boards.getConfigurationSection(key);

            HollowBoard hollowBoard = new HollowBoard(board.getString("title"));

            List<DynamicLine> lines = new ArrayList<>();

            for(String line : board.getStringList("contents")) {
                if(this.components.containsKey(line)) {

                    if(!lines.isEmpty()) {
                        hollowBoard.append(new DynamicComponent(lines));
                        lines.clear();
                    }

                    hollowBoard.append(this.components.get(line));
                    continue;
                }

                lines.add(DynamicLine.compileLine(line));
            }

            if(!lines.isEmpty()) {
                hollowBoard.append(new DynamicComponent(lines));
            }

            getLogger().info("Loaded board " + key);
            this.boards.put(key, hollowBoard);
        }

        this.defaultBoard = this.boards.get(this.getConfig().getString("default-board"));
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}
