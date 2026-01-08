package net.zffu.hollowboard.task;

import net.zffu.hollowboard.HollowBoardPlugin;
import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.board.HollowBoard;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.function.Supplier;

/**
 * Manages the board ticking task.
 */
public class BoardTickingManager {

    public long lastTaskStart;
    public HashSet<HollowPlayer> tickingPlayers;

    private BukkitRunnable tickingTask;
    private ShutdownTickingTaskRunnable shutdownTickingTaskRunnable;
    private StartTickingTaskRunnable startTickingTaskRunnable;

    public BoardTickingManager() {
        this.lastTaskStart = 0;
        this.tickingPlayers = new HashSet<>();

        this.tickingTask = null;
        this.shutdownTickingTaskRunnable = null;
        this.startTickingTaskRunnable = null;
    }

    public boolean shouldBeActive() {
        return !this.tickingPlayers.isEmpty();
    }

    public void appendTickPlayer(HollowPlayer player) {
        this.tickingPlayers.add(player);

        this.tryStartingTick();
    }

    public void removeTickPlayer(HollowPlayer player) {
        this.tickingPlayers.remove(player);

        if(this.tickingPlayers.isEmpty()) this.tryStopTick();
    }

    private void tryStartingTick() {
        if(this.startTickingTaskRunnable != null || this.tickingTask != null) return;

        this.startTickingTaskRunnable = new StartTickingTaskRunnable(this);
        this.startTickingTaskRunnable.runTaskLater(HollowBoardPlugin.INSTANCE, 25);
    }

    private void tryStopTick() {
        if(this.shutdownTickingTaskRunnable != null || this.tickingTask == null) return;

        this.shutdownTickingTaskRunnable = new ShutdownTickingTaskRunnable(this);
        this.shutdownTickingTaskRunnable.runTaskLater(HollowBoardPlugin.INSTANCE, 25);
    }

    public static class ShutdownTickingTaskRunnable extends BukkitRunnable {

        private BoardTickingManager manager;

        public ShutdownTickingTaskRunnable(BoardTickingManager manager) {
            this.manager = manager;
        }

        @Override
        public void run() {
            if(this.manager.tickingTask == null) {
                HollowBoardPlugin.INSTANCE.getLogger().severe("[BoardTickingManager] Tried using shutdown ticking task without any ticking task!");
                return;
            }

            if(this.manager.tickingPlayers.isEmpty()) {
                HollowBoardPlugin.INSTANCE.getLogger().info("[BoardTickingManager] Shutting down board ticking task");

                this.manager.lastTaskStart = System.currentTimeMillis();

                this.manager.tickingTask.cancel();
                this.manager.tickingTask = null;

                this.manager.shutdownTickingTaskRunnable = null;
            }
        }
    }

    public static class StartTickingTaskRunnable extends BukkitRunnable {

        private BoardTickingManager manager;

        public StartTickingTaskRunnable(BoardTickingManager manager) {
            this.manager = manager;
        }

        @Override
        public void run() {
            if(this.manager.tickingTask != null) {
                HollowBoardPlugin.INSTANCE.getLogger().severe("[BoardTickingManager] Tried using start ticking task with a ticking task!");
                return;
            }

            if(!this.manager.tickingPlayers.isEmpty()) {
                HollowBoardPlugin.INSTANCE.getLogger().info("[BoardTickingManager] Starting up board ticking task");

                this.manager.tickingTask = new BoardTickingTask(this.manager);
                this.manager.tickingTask.runTaskTimer(HollowBoardPlugin.INSTANCE, 0, 20 * 2);

                this.manager.startTickingTaskRunnable = null;
            }
        }
    }





}
