package net.zffu.hollowboard.utils;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ClientSideScoreboard {

    public static Scoreboard NMS_SB = new Scoreboard();

    private final EntityPlayer player;

    /**
     * Current state reflections
     */
    private String title;
    private String[] lines;
    private boolean spawned;
    private ScoreboardObjective nmsInitialSpawn;

    public ClientSideScoreboard(EntityPlayer player, String title) {
        this.player = player;
        this.lines = new String[15];
        this.title = title;
        this.spawned = false;
    }

    public void initialSpawn() {
        if(this.spawned) return;

        this.nmsInitialSpawn = new ScoreboardObjective(NMS_SB, "hollowsbdk", IScoreboardCriteria.c, IChatBaseComponent.c(this.title), IScoreboardCriteria.EnumScoreboardHealthDisplay.a, false, null);
        this.player.g.b(new PacketPlayOutScoreboardObjective(this.nmsInitialSpawn, 0));

        for(int i = 0; i < 15; ++i) {
            String str = this.lines[i];

            if(str == null) continue;

            this.player.g.b(new PacketPlayOutScoreboardScore("hlw-line-" + i, "hollowsbdk", 15 - i, Optional.of(IChatBaseComponent.a(str)), Optional.empty()));
        }

        this.player.g.b(new PacketPlayOutScoreboardDisplayObjective(DisplaySlot.b, this.nmsInitialSpawn));
        this.spawned = true;
    }

    public void change(int line, String newLine) {
        // Check for cache first
        if(newLine.equals(this.lines[line])) return;

        this.lines[line] = newLine;

        if(this.spawned) {
            this.player.g.b(new PacketPlayOutScoreboardScore("hlw-line-" + line, "hollowsbdk", 15 - line, Optional.of(IChatBaseComponent.a(newLine)), Optional.empty()));
        }
    }

    public void despawn() {
        if(!this.spawned) return;

        this.player.g.b(new PacketPlayOutScoreboardObjective(this.nmsInitialSpawn, 1));

        this.nmsInitialSpawn = null;
        this.spawned = false;
    }

}
