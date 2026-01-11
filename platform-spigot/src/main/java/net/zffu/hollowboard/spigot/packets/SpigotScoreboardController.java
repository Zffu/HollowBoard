package net.zffu.hollowboard.spigot.packets;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisplayScoreboard;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import net.kyori.adventure.text.Component;
import net.zffu.hollowboard.internals.PacketScoreboardController;

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

    }

    @Override
    public void remove(int index) {

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
