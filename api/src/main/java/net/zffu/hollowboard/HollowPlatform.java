package net.zffu.hollowboard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * <p>A server platform supported by HollowBoard.</p>
 * @since 1.0.0
 */
public interface HollowPlatform {

    /**
     * Gets the {@link HollowPlayer} instance for the given player.
     * @param playerUUID the player's uuid.
     * @return the player instance for the HollowPlayer platform implementation.
     */
    @Nullable HollowPlayer getPlayer(@NotNull UUID playerUUID);

    void appendUpdatablePlayer(HollowPlayer player);
    void removeUpdatablePlayer(HollowPlayer player);

}
