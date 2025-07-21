package me.xiaoying.turtle.api.core;

import org.bukkit.plugin.Plugin;

/**
 * Core 代码，类似 Bukkit
 */
public class TCore {
    private static Plugin instance;

    public static Plugin getInstance() {
        return TCore.instance;
    }

    public static void setInstance(Plugin instance) {
        if (TCore.instance != null)
            return;

        TCore.instance = instance;
    }
}