package me.xiaoying.turtle.api.core;

import me.xiaoying.sqlfactory.SqlFactory;
import org.bukkit.plugin.Plugin;

/**
 * Core 代码，类似 Bukkit
 */
public class TCore {
    private static Plugin instance;
    private static SqlFactory sqlFactory;

    public static Plugin getInstance() {
        return TCore.instance;
    }

    public static void setInstance(Plugin instance) {
        if (TCore.instance != null)
            return;

        TCore.instance = instance;
    }

    public static SqlFactory getSqlFactory() {
        return TCore.sqlFactory;
    }

    public static void setSqlFactory(SqlFactory sqlFactory) {
        if (TCore.sqlFactory != null)
            return;

        TCore.sqlFactory = sqlFactory;
    }
}