package net.zffu.hollowboard.board.lines.parts;

import net.zffu.hollowboard.HollowPlayer;

/**
 * Represents a part / piece of a line.
 * @since 1.0.0
 */
public interface LinePart {

    /**
     * Writes the result of the line part.
     * @param player the player.
     * @return the line part result.
     */
    String write(HollowPlayer player);

    /**
     * Determines if the line part is updatable / will change.
     * @return true or false
     */
    boolean isUpdatePossible();

}
