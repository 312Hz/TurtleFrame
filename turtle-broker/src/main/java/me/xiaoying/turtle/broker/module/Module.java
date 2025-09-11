package me.xiaoying.turtle.broker.module;

import me.xiaoying.turtle.api.option.Option;

public abstract class Module {
    private boolean enabled = false;

    public abstract String getKey();

    public abstract String getName();

    public boolean enabled() {
        return this.enabled;
    }

    public void enable() {
        this.onEnable();

        this.enabled = true;
    }

    public void disable() {
        this.onDisable();

        this.enabled = false;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract Option[] getOptions();
}