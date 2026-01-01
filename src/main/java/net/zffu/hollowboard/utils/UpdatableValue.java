package net.zffu.hollowboard.utils;

import java.util.function.Supplier;

/**
 * Utility to handle update
 * @param <T>
 */
public class UpdatableValue<T> {

    private T val;
    private long lastUpdate;

    private final Supplier<T> updateSource;
    private final long updateCooldown;

    public UpdatableValue(Supplier<T> source, long updateCooldown) {
        this.updateSource = source;
        this.updateCooldown = updateCooldown;
    }

    public T gather() {
        if(System.currentTimeMillis() - this.lastUpdate < this.updateCooldown) {
            return this.val;
        }

        this.val = this.updateSource.get();
        this.lastUpdate = System.currentTimeMillis();

        return this.val;
    }

}
