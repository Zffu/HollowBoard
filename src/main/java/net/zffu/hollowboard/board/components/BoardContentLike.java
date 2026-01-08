package net.zffu.hollowboard.board.components;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Represents a content container for a board.
 */
public interface BoardContentLike {

    boolean canUpdate();

    /**
     * Writes the board content for a given user.
     * @param player the player
     * @return the written strings
     */
    List<String> write(Player player);

}
