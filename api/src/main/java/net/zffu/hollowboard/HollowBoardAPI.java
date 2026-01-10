package net.zffu.hollowboard;

import org.jetbrains.annotations.NotNull;

/**
 * The base of the HollowBoard API.
 * @since 1.0.0
 */
public class HollowBoardAPI {

    private static HollowBoardAPI instance;

    private @NotNull HollowPlatform platform;

    /**
     * <p>Creates a new instance of the HollowBoard API with the given platform.</p>
     * @param platform the platform.
     */
    public HollowBoardAPI(@NotNull HollowPlatform platform) {
        this.platform = platform;

        instance = this;
    }

    /**
     * Get the platform access.
     * @return the {@link HollowPlatform} object.
     */
    public HollowPlatform getPlatformAccess() {
        return this.platform;
    }

    /**
     * Get the currently running HollowBoard API instance.
     * @return
     */
    public static HollowBoardAPI getInstance() {
        return instance;
    }

}
