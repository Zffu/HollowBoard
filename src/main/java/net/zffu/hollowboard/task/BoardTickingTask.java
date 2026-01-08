package net.zffu.hollowboard.task;

import net.zffu.hollowboard.HollowPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class BoardTickingTask extends BukkitRunnable {

    private BoardTickingManager manager;

    public BoardTickingTask(BoardTickingManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        for(HollowPlayer player : this.manager.tickingPlayers) {
            player.updateCurrentBoard();
        }
    }

}
