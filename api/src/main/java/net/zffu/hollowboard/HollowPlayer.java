package net.zffu.hollowboard;

import net.zffu.hollowboard.board.HollowBoard;
import net.zffu.hollowboard.board.components.BoardContentLike;
import net.zffu.hollowboard.internals.PacketScoreboardController;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * Represents a player in the HollowBoard API.
 * @since 1.0.0
 */
public class HollowPlayer {

    private HollowBoard board;

    public final PacketScoreboardController scoreboardController;

    private HashMap<BoardContentLike, Visibility> visibilityStates;
    public HashMap<BoardContentLike, Integer> lastSizes;

    private long lastUpdate;

    public HollowPlayer(PacketScoreboardController controller) {
        this.board = null;
        this.scoreboardController = controller;

        this.visibilityStates = new HashMap<>();
        this.lastSizes = new HashMap<>();
    }

    public @Nullable HollowBoard getCurrentBoard() {
        return this.board;
    }

    /**
     * Sets the current board of the player to the given board
     * @param board the board
     */
    public void setCurrentBoard(@Nullable HollowBoard board) {
        this.lastSizes.clear();
        this.visibilityStates.clear();

        if(board == null) {
            this.scoreboardController.clear();

            if(this.board != null && this.board.isUpdatable()) {
                HollowBoardAPI.getInstance().getPlatformAccess().removeUpdatablePlayer(this);
            }

            this.board = null;

            //HollowBoardPlugin.INSTANCE.tickingManager.removeTickPlayer(this);

            return;
        }

        boolean spawnScoreboard = this.board == null;

        if((this.board == null || !this.board.isUpdatable()) && board.isUpdatable()) {
            HollowBoardAPI.getInstance().getPlatformAccess().appendUpdatablePlayer(this);
        }

        this.board = board;

        this.scoreboardController.setTitle(this.board.getTitle());

        int index = 0;

        for(BoardContentLike like : this.board.getLines()) {
            List<String> strs = like.write(this);

            if(like.canUpdate()) {
                this.lastSizes.put(like, strs.size());
            }

            for(String str : strs) {
                this.scoreboardController.setLine(index, str);
                ++index;
            }
        }

        if(spawnScoreboard) {
            this.scoreboardController.show();
        }
    }

    /**
     * Updates the currently viewed player board
     */
    public void updateCurrentBoard() {
        if(this.board == null || !this.board.isUpdatable()) return;

        for(HollowBoard.IndexedContentLike like : this.board.getUpdatableLines()) {
            this.board.update(like, this);
        }
    }

    @Deprecated(forRemoval = true)
    public Visibility getComponentVisibility(BoardContentLike like) {
        return this.visibilityStates.getOrDefault(like, Visibility.DEFAULT);
    }

    @Deprecated(forRemoval = true)
    public void markComponentVisibility(BoardContentLike like, Visibility visibility) {
        if(visibility == Visibility.DEFAULT) {
            this.visibilityStates.remove(like);
            return;
        }

        this.visibilityStates.put(like, visibility);
    }

    @Override
    public String toString() {
        return "HollowPlayer{" +
                "lastUpdate=" + lastUpdate +
                ", board=" + board +
                '}';
    }
}
