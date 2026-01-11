package net.zffu.hollowboard.spigot.tasks;

import net.zffu.hollowboard.platforms.tasks.Task;
import net.zffu.hollowboard.platforms.tasks.TaskScheduler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpigotScheduler implements TaskScheduler {

    private final JavaPlugin plugin;

    public SpigotScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Task runTaskIn(Runnable runnable, int ticks) {
        SpigotTask task = new SpigotTask(runnable);
        task.runnable.runTaskLater(this.plugin, ticks);

        return task;
    }

    @Override
    public Task runTaskTimer(Runnable runnable, int intervalInTicks) {
        SpigotTask task = new SpigotTask(runnable);
        task.runnable.runTaskTimer(this.plugin, 0, intervalInTicks);

        return task;
    }

    public static class SpigotTask implements Task {
        public BukkitRunnable runnable;

        public SpigotTask(Runnable runnable) {
            this.runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            };
        }

        @Override
        public void cancel() {
            this.runnable.cancel();
        }

    }

}
