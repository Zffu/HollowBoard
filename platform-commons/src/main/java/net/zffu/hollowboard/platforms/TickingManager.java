package net.zffu.hollowboard.platforms;

import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.platforms.tasks.Task;
import net.zffu.hollowboard.platforms.tasks.TaskScheduler;

import java.util.HashSet;

/**
 * <p>Manager for the ticking for boards. Is seperated from main API since some platforms won't use it.</p>
 */
public class TickingManager {

    private long lastStartTask;
    private HashSet<HollowPlayer> ticking;

    private Task tickingTask;
    private Task shutdownTask;
    private Task startupTask;

    private final TaskScheduler scheduler;

    public TickingManager(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public boolean shouldBeActive() {
        return !this.ticking.isEmpty();
    }

    public void appendPlayer(HollowPlayer player) {
        this.ticking.add(player);

        this.tryStartingTick();
    }

    public void removePlayer(HollowPlayer player) {
        this.ticking.remove(player);

        if(this.ticking.isEmpty()) this.tryStopTick();
    }

    private void tryStartingTick() {
        if(this.startupTask != null || this.tickingTask != null) return;

        this.startupTask = this.scheduler.runTaskIn(new StartupTask(this), 25);
    }

    private void tryStopTick() {
        if(this.shutdownTask != null || this.tickingTask == null) return;

        this.shutdownTask = this.scheduler.runTaskIn(new StartupTask(this), 25);
    }

    public static class ShutdownTask implements Runnable {

        private TickingManager tickingManager;

        public ShutdownTask(TickingManager manager) {
            this.tickingManager = manager;
        }

        @Override
        public void run() {
            if(this.tickingManager.tickingTask == null) {
                throw new IllegalStateException("Board ticking task is null during shutdown task!");
            }

            if(this.tickingManager.ticking.isEmpty()) {
                this.tickingManager.lastStartTask = System.currentTimeMillis();

                this.tickingManager.tickingTask.cancel();
                this.tickingManager.tickingTask = null;

                this.tickingManager.shutdownTask = null;
            }
        }
    }

    public static class StartupTask implements Runnable {

        private TickingManager tickingManager;

        public StartupTask(TickingManager manager) {
            this.tickingManager = manager;
        }

        @Override
        public void run() {
            if(this.tickingManager.tickingTask != null) {
                throw new IllegalStateException("Board Ticking manager ticking state wasn't null during startup task!");
            }

            if(!this.tickingManager.ticking.isEmpty()) {
                this.tickingManager.tickingTask = this.tickingManager.scheduler.runTaskTimer(new TickingTask(this.tickingManager), 40);
            }
        }
    }

    public static class TickingTask implements Runnable {

        private TickingManager tickingManager;

        public TickingTask(TickingManager manager) {
            this.tickingManager = manager;
        }

        @Override
        public void run() {
            for(HollowPlayer player : this.tickingManager.ticking) {
                player.updateCurrentBoard();
            }
        }
    }

}
