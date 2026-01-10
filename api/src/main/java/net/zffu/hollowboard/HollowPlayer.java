package net.zffu.hollowboard;

import net.zffu.hollowboard.board.HollowBoard;
import net.zffu.hollowboard.board.components.BoardContentLike;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a player in the HollowBoard API.
 * @since 1.0.0
 */
public interface HollowPlayer {

    @Nullable HollowBoard getCurrentBoard();

    /**
     * Sets the current board of the player to the given board
     * @param board the board
     */
    void setCurrentBoard(@Nullable HollowBoard board);

    /**
     * Updates the currently viewed player board
     */
    void updateCurrentBoard();

    @Deprecated(forRemoval = true)
    Visibility getComponentVisibility(BoardContentLike like);

    @Deprecated(forRemoval = true)
    void markComponentVisibility(BoardContentLike like, Visibility visibility);

}
