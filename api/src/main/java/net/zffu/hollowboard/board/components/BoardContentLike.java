package net.zffu.hollowboard.board.components;

import net.zffu.hollowboard.HollowPlayer;

import java.util.List;

/**
 * <p>A container / content piece contained in a board.</p>
 * @since 1.0.0
 */
public interface BoardContentLike {

    /**
     * <p>Determines if the given element can update in time.</p>
     * @return true or false.
     */
    boolean canUpdate();

    /**
     * Writes the board content for a given user.
     * @param player the player
     * @return the written strings
     */
    List<String> write(HollowPlayer player);

    /**
     * Gets the size of the element in lines.
     * @param player the player
     * @return the size in lines
     */
    @Deprecated(forRemoval = true)
    int getSize(HollowPlayer player);

}
