package net.zffu.hollowboard;

import net.zffu.hollowboard.board.HollowBoard;
import org.bukkit.entity.Player;

public class HollowPlayer {

    private Player player;
    private HollowBoard board;

    private long lastUpdate;

    public HollowPlayer(Player player) {
        this.player = player;
        this.board = null;
    }

}
