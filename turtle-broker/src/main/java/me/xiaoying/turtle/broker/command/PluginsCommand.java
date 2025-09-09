package me.xiaoying.turtle.broker.command;

import me.xiaoying.moebroker.api.Broker;
import me.xiaoying.moebroker.server.bootstrap.api.BCore;
import me.xiaoying.moebroker.server.bootstrap.api.command.Command;
import me.xiaoying.moebroker.server.bootstrap.api.command.CommandSender;
import me.xiaoying.moebroker.server.bootstrap.api.plugin.JavaPlugin;
import me.xiaoying.moebroker.server.bootstrap.api.plugin.Plugin;
import me.xiaoying.moebroker.server.bootstrap.api.plugin.PluginDescription;
import me.xiaoying.moebroker.server.bootstrap.api.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class PluginsCommand extends Command {
    public PluginsCommand() {
        super("pl", "插件列表", "/pl", Collections.singletonList("plugins"));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            PluginManager pluginManager = BCore.getPluginManager();

            Broker.getLogger().info("[*] 插件总数量: " + pluginManager.getPlugins().length);
            for (Plugin plugin : pluginManager.getPlugins()) {
                PluginDescription description = ((JavaPlugin) plugin).getDescription();
                String des;
                if ((des = description.getDescription()) == null)
                    des = "无";
                Broker.getLogger().info("|- " + description.getName() + " -> " + des);
                Broker.getLogger().info("   └─ Author: " + description.getAuthors());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}