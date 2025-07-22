package me.xiaoying.turtle.broker.command;

import me.xiaoying.turtle.broker.TBroker;
import net.afyer.afybroker.server.plugin.Command;
import net.afyer.afybroker.server.plugin.Plugin;
import net.afyer.afybroker.server.plugin.PluginDescription;
import net.afyer.afybroker.server.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.Map;

public class PluginsCommand extends Command {
    public PluginsCommand() {
        super("plugins", "pl");
    }

    @Override
    public void execute(String[] strings) {
        try {
            PluginManager pluginManager = TBroker.getInstance().getServer().getPluginManager();
            Field declaredField = pluginManager.getClass().getDeclaredField("plugins");
            declaredField.setAccessible(true);

            Map<String, Plugin> plugins = (Map<String, Plugin>) declaredField.get(pluginManager);
            System.out.println("[*] 插件总数量: " + plugins.size());
            plugins.values().forEach(plugin -> {
                PluginDescription description = plugin.getDescription();
                String des;
                if ((des = description.getDescription()) == null)
                    des = "无";
                System.out.println("|- " + description.getName() + " -> " + des + "\n   └─ Author: " + description.getAuthor());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}