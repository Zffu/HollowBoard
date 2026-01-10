package net.zffu.hollowboard.spigot;

import net.zffu.hollowboard.HollowPlatform;
import net.zffu.hollowboard.HollowPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class SpigotPlatform implements HollowPlatform {

    private final HashMap<UUID, HollowPlayer> players = new HashMap<>();

    @Override
    public @Nullable HollowPlayer getPlayer(@NotNull UUID playerUUID) {
        return players.get(playerUUID);
    }

    public void removePlayer(UUID playerUUID) {
        players.remove(playerUUID);
    }

    public void addPlayer(UUID playerUUID, HollowPlayer player) {
        players.put(playerUUID, player);
    }

}
