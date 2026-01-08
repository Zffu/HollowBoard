package net.zffu.hollowboard;

import net.zffu.hollowboard.board.HollowBoard;
import net.zffu.hollowboard.board.components.BoardContentLike;
import net.zffu.hollowboard.utils.ClientSideScoreboard;
import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class HollowPlayer {

    public final Player player;
    public HollowBoard board;
    public ClientSideScoreboard scoreboard;

    public HashMap<BoardContentLike, Boolean> visibility = new HashMap<>();
    public HashMap<BoardContentLike, Integer> lastSizes = new HashMap<>();

    private long lastUpdate;

    public HollowPlayer(Player player) {
        this.player = player;
        this.board = null;
        this.scoreboard = new ClientSideScoreboard(((CraftPlayer)player).getHandle(), "");
    }

    public void setCurrentBoard(HollowBoard board) {

        this.lastSizes.clear();
        this.visibility.clear();

        if(board == null) {
            this.scoreboard.clear();
            this.board = null;

            HollowBoardPlugin.INSTANCE.tickingManager.removeTickPlayer(this);

            return;
        }

        boolean spawnScoreboard = this.board == null;

        this.board = board;

        this.scoreboard.changeTitle(this.board.getTitle());

        int index = 0;

        for(BoardContentLike like : this.board.getLines()) {
            List<String> strs = like.write(this.player);

            if(like.canUpdate()) {
                this.lastSizes.put(like, strs.size());
            }

            for(String str : strs) {
                this.scoreboard.change(index, str);
                ++index;
            }
        }

        if(this.board.isUpdatable()) {
            HollowBoardPlugin.INSTANCE.tickingManager.appendTickPlayer(this);
        }

        if(spawnScoreboard) {
            this.scoreboard.initialSpawn();
        }
    }

    public void updateCurrentBoard() {
        if(!this.board.isUpdatable()) return;

        for(HollowBoard.IndexedContentLike like : this.board.getUpdatableLines()) {
            this.board.update(like, this);
        }
    }
}
