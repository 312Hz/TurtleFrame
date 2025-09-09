package me.xiaoying.turtle.broker;

import me.xiaoying.moebroker.api.Broker;
import me.xiaoying.moebroker.server.bootstrap.api.BCore;
import me.xiaoying.moebroker.server.bootstrap.api.plugin.JavaPlugin;
import me.xiaoying.sqlfactory.SqlFactory;
import me.xiaoying.sqlfactory.config.MysqlConfig;
import me.xiaoying.sqlfactory.config.PostgreSqlConfig;
import me.xiaoying.sqlfactory.config.SqliteConfig;
import me.xiaoying.sqlfactory.factory.MysqlFactory;
import me.xiaoying.sqlfactory.factory.PostgreSqlFactory;
import me.xiaoying.sqlfactory.factory.SqliteFactory;
import me.xiaoying.turtle.api.core.TCore;
import me.xiaoying.turtle.api.option.OptionManager;
import me.xiaoying.turtle.broker.command.PluginsCommand;
import me.xiaoying.turtle.broker.file.FileManager;
import me.xiaoying.turtle.broker.file.SimpleFileManager;
import me.xiaoying.turtle.broker.file.TBrokerFile;
import me.xiaoying.turtle.broker.module.ModuleManager;
import me.xiaoying.turtle.broker.module.consumable.Consumable;
import me.xiaoying.turtle.broker.option.SimpleOptionManager;
import me.xiaoying.turtle.broker.processor.OptionClassificationRequestProcessor;
import me.xiaoying.turtle.broker.processor.OptionRequestProcessor;

import java.io.File;
import java.util.Locale;

public class TBroker extends JavaPlugin {
    private static FileManager fileManager;
    private static OptionManager optionManager;

    private static ModuleManager moduleManager;

    private static TBroker instance;

    @Override
    public void onLoad() {
        Broker.getLogger().info("&6[*] &f正在初始化 {} .", this.getDescription().getName());

        // processors
        Broker.getLogger().info("&6[*] &f注册 Processors .");
        BCore.getServer().getProcessorManager().registerProcessor(new OptionRequestProcessor());
        BCore.getServer().getProcessorManager().registerProcessor(new OptionClassificationRequestProcessor());
    }

    @Override
    public void onEnable() {
        TBroker.instance = this;

        // files
        Broker.getLogger().info("&6[*] &f初始化配置文件.");
        TBroker.fileManager = new SimpleFileManager();
        TBroker.fileManager.register(new TBrokerFile());
        TBroker.fileManager.loads();

        // sqlfactory
        SqlFactory sqlFactory;
        switch (TBrokerFile.DATABASE_TYPE.toUpperCase(Locale.ENGLISH)) {
            case "MYSQL":
                sqlFactory = new MysqlFactory(new MysqlConfig(TBrokerFile.DATABASE_MYSQL_USERNAME, TBrokerFile.DATABASE_MYSQL_PASSWORD, TBrokerFile.DATABASE_MYSQL_HOST, TBrokerFile.DATABASE_MYSQL_PORT, TBrokerFile.DATABASE_MYSQL_DATABASE));
                break;
            case "POSTGRESQL":
                sqlFactory = new PostgreSqlFactory(new PostgreSqlConfig(TBrokerFile.DATABASE_POSTGRESQL_USERNAME, TBrokerFile.DATABASE_POSTGRESQL_PASSWORD, TBrokerFile.DATABASE_POSTGRESQL_HOST, TBrokerFile.DATABASE_POSTGRESQL_PORT, TBrokerFile.DATABASE_POSTGRESQL_DATABASE));
                break;
            case "SQLITE":
            default:
                sqlFactory = new SqliteFactory(new SqliteConfig(new File(TBrokerFile.DATABASE_SQLITE_FILE)));
                break;
        }
        TCore.setSqlFactory(sqlFactory);

        // commands
        Broker.getLogger().info("&6[*] &f注册命令.");
        BCore.getCommandManager().registerCommand(this, new PluginsCommand());

        // options
        TBroker.optionManager = new SimpleOptionManager();

        // modules
        TBroker.moduleManager = new ModuleManager();
        TBroker.moduleManager.registerModule(new Consumable());
        TBroker.moduleManager.getModules().forEach(module -> {
            if (module.enabled())
                return;

            module.enable();
            Broker.getLogger().info("&6[*] &f已加载 &e{}&f(&7{}&f)", module.getName(), module.getKey());
        });

        Broker.getLogger().info("&a[√] &f初始化完成.");
    }

    @Override
    public void onDisable() {
        TBroker.getOptionManager().save();

        TBroker.moduleManager.getModules().forEach(module -> {
            if (!module.enabled())

                return;

            module.disable();
            Broker.getLogger().info("[*] 已卸载 {}({})", module.getName(), module.getKey());
        });

        Broker.getLogger().info("[#] {} 已卸载.", this.getDescription().getName());
    }

    public static OptionManager getOptionManager() {
        return TBroker.optionManager;
    }

    public static ModuleManager getModuleManager() {
        return TBroker.moduleManager;
    }

    public static JavaPlugin getInstance() {
        return TBroker.instance;
    }
}