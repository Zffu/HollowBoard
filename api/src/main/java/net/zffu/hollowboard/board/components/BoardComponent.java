package net.zffu.hollowboard.board.components;

import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.Visibility;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * <p><b>A fully featured {@link net.zffu.hollowboard.board.HollowBoard} component.</b></p>
 * <p><b>NOTE:</b> Should only be really used with the HollowBoard API</p><br>
 * <br>
 * Furthermore, also contains some experiments & new features with the Hollow Board API such as:<br>
 * <br> - Usage of {@link HollowPlayer} instead of {@link Player}
 * <br> - Visibility toggle for players
 * <br> - Direct update calls for players
 * @since 1.0.0
 */
public abstract class BoardComponent implements BoardContentLike {

    private boolean visibleByDefault;

    /**
     * Creates a {@link BoardComponent}.
     * @param visibleByDefault should the component be visible by default
     */
    public BoardComponent(boolean visibleByDefault) {
        this.visibleByDefault = visibleByDefault;
    }

    /**
     * Creates a {@link BoardComponent}.
     */
    public BoardComponent() {
        this.visibleByDefault = true;
    }

    /**
     * Determines whether the component is visible by default.
     */
    public boolean isVisibleByDefault() {
        return this.visibleByDefault;
    }

    /**
     * Defines if the component is visible by default
     */
    public BoardComponent visibleByDefault(boolean visibleByDefault) {
        this.visibleByDefault = visibleByDefault;
        return this;
    }

    /**
     * Shows the component to the given player.
     * @param player the player as an {@link HollowPlayer}
     *
     * <br>
     * <p><b>NOTE: </b> this bypasses default visibility</p>
     */
    public void show(HollowPlayer player) {
        if(this.canSee(player)) return;

        player.markComponentVisibility(this, Visibility.VISIBLE);
        //player.visibility.put(this, true);
        player.updateCurrentBoard();
    }

    /**
     * Hides the component to the given player.
     * @param player the player as an {@link HollowPlayer}
     *
     * <br>
     * <p><b>NOTE: </b> this bypasses default visibility</p>
     */
    public void hide(HollowPlayer player) {
        if(!this.canSee(player)) return;

        player.markComponentVisibility(this, Visibility.HIDDEN);
        //player.visibility.put(this, false);
        player.updateCurrentBoard();
    }

    /**
     * Determines whether the given player can see the component.
     * @param player the player as an {@link HollowPlayer}.
     */
    public boolean canSee(HollowPlayer player) {
        return player.getComponentVisibility(this) != Visibility.DEFAULT ? player.getComponentVisibility(this) == Visibility.VISIBLE : this.visibleByDefault;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public abstract List<String> getContent(HollowPlayer player);

    @Override
    public List<String> write(HollowPlayer player) {
        return this.getContent(player);
    }

    @Override
    public int getSize(HollowPlayer player) {
        return this.write(player).size();
    }

}
