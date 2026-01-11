package net.zffu.hollowboard.platforms.tasks;

/**
 * <p>Simple scheduler access.</p>
 * @since 1.0.0
 */
public interface TaskScheduler {

    Task runTaskIn(Runnable runnable, int ticks);
    Task runTaskTimer(Runnable runnable, int intervalInTicks);

}
