package me.xiaoying.turtle.api.core;

import me.xiaoying.sqlfactory.SqlFactory;
import me.xiaoying.turtle.api.broker.BrokerClient;
import me.xiaoying.turtle.api.module.ModuleManager;
import me.xiaoying.turtle.api.pluginmanager.PluginManager;
import org.bukkit.plugin.Plugin;

/**
 * Core 代码，类似 Bukkit
 */
public class TCore {
    private static Plugin instance;
    private static SqlFactory sqlFactory;

    private static PluginManager pluginManager;

    private static ModuleManager moduleManager;

    private static BrokerClient brokerClient;

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

    /**
     * Get manager of plugin
     *
     * @return PluginManager
     */
    public static PluginManager getPluginManager() {
        return TCore.pluginManager;
    }

    /**
     * Set manager of plugin
     *
     * @param pluginManager PluginManager
     */
    public static void setPluginManager(PluginManager pluginManager) {
        if (TCore.pluginManager != null)
            return;

        TCore.pluginManager = pluginManager;
    }

    public static void setModuleManager(ModuleManager moduleManager) {
        if (TCore.moduleManager != null)
            return;

        TCore.moduleManager = moduleManager;
    }

    public static BrokerClient getBrokerClient() {
        return TCore.brokerClient;
    }

    public static void setBrokerClient(BrokerClient brokerClient) {
        if (TCore.brokerClient != null)
            return;

        TCore.brokerClient = brokerClient;
    }

    public static ModuleManager getModuleManager() {
        return TCore.moduleManager;
    }
}