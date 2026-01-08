package net.zffu.hollowboard;

import net.zffu.hollowboard.board.BoardLine;
import net.zffu.hollowboard.board.HollowBoard;
import net.zffu.hollowboard.utils.ClientSideScoreboard;
import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class HollowPlayer {

    private Player player;
    private HollowBoard board;
    private ClientSideScoreboard scoreboard;

    private long lastUpdate;

    public HollowPlayer(Player player) {
        this.player = player;
        this.board = null;
        this.scoreboard = new ClientSideScoreboard(((CraftPlayer)player).getHandle(), "");
    }

    public void setCurrentBoard(HollowBoard board) {
        if(board == null) {
            this.scoreboard.clear();
            this.board = null;

            HollowBoardPlugin.INSTANCE.tickingManager.removeTickPlayer(this);

            return;
        }

        boolean spawnScoreboard = this.board == null;

        this.board = board;

        this.scoreboard.changeTitle(this.board.getTitle());

        for(int i = 0; i < this.board.getLines().size(); ++i) {
            BoardLine line = this.board.getLines().get(i);

            this.scoreboard.change(i, line.write(this.player));
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

        for(HollowBoard.IndexedLine line : this.board.getUpdatableLines()) {
            this.scoreboard.change(line.index, line.line.write(player));
        }
    }

}
