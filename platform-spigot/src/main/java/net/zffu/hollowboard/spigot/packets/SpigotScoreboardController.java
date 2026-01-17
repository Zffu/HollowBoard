package net.zffu.hollowboard.spigot.packets;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.score.ScoreFormat;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisplayScoreboard;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateScore;
import net.kyori.adventure.text.Component;
import net.zffu.hollowboard.internals.PacketScoreboardController;

import java.util.Optional;

public class SpigotScoreboardController implements PacketScoreboardController {

    private User user;

    private String title;
    private String[] lines;
    private boolean spawned;

    public SpigotScoreboardController(User user, String title) {
        this.user = user;

        this.title = title;
        this.lines = new String[15];
        this.spawned = false;
    }

    @Override
    public void setLine(int index, String line) {
        this.lines[index] = line;

        if(this.spawned) {
            this.showLine(index);
        }
    }

    private void showLine(int index) {
        if(this.lines[index] == null) return;

        WrapperPlayServerUpdateScore score = new WrapperPlayServerUpdateScore("line-" + index, WrapperPlayServerUpdateScore.Action.CREATE_OR_UPDATE_ITEM, "hollowsbdk", 15 - index, Component.text(this.lines[index]), ScoreFormat.fixedScore(Component.text(this.lines[index])));

        this.user.sendPacket(score);
    }

    @Override
    public void remove(int index) {
        this.lines[index] = null;

        WrapperPlayServerUpdateScore score = new WrapperPlayServerUpdateScore("line-" + index, WrapperPlayServerUpdateScore.Action.REMOVE_ITEM, "hollowsbdk", Optional.empty());

        this.user.sendPacket(score);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;

        if(this.spawned) {
            WrapperPlayServerScoreboardObjective wrapperPlayServerScoreboardObjective = new WrapperPlayServerScoreboardObjective("hollowsbdk", WrapperPlayServerScoreboardObjective.ObjectiveMode.UPDATE, Component.text(this.title), WrapperPlayServerScoreboardObjective.RenderType.INTEGER);
            this.user.sendPacket(wrapperPlayServerScoreboardObjective);
        }
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getText(int index) {
        if(index < 0 || index >= 15) return null;

        return this.lines[index];
    }

    @Override
    public void show() {
        if(this.spawned) return;

        WrapperPlayServerScoreboardObjective wrapperPlayServerScoreboardObjective = new WrapperPlayServerScoreboardObjective("hollowsbdk", WrapperPlayServerScoreboardObjective.ObjectiveMode.CREATE, Component.text(this.title), WrapperPlayServerScoreboardObjective.RenderType.INTEGER);
        WrapperPlayServerDisplayScoreboard displayScoreboard = new WrapperPlayServerDisplayScoreboard(1, "hollowsbdk");

        this.user.sendPacket(wrapperPlayServerScoreboardObjective);
        this.user.sendPacket(displayScoreboard);

        for(int i = 0; i < 15; ++i) {
            this.showLine(i);
        }

        this.spawned = true;
    }

    @Override
    public void hide() {
        if(!this.spawned) return;

        WrapperPlayServerScoreboardObjective wrapperPlayServerScoreboardObjective = new WrapperPlayServerScoreboardObjective("hollowsbdk", WrapperPlayServerScoreboardObjective.ObjectiveMode.REMOVE, Component.empty(), WrapperPlayServerScoreboardObjective.RenderType.INTEGER);
        this.user.sendPacket(wrapperPlayServerScoreboardObjective);
        this.spawned = false;
    }

    @Override
    public void clear() {
        if(this.spawned) this.hide();

        for(int i = 0; i < 15; ++i) {
            this.lines[i] = null;
        }
    }
}
