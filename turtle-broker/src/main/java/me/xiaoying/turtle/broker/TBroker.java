package me.xiaoying.turtle.broker;

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
import me.xiaoying.turtle.broker.option.SimpleOptionManager;
import me.xiaoying.turtle.broker.processor.OptionClassificationRequestProcessor;
import me.xiaoying.turtle.broker.processor.OptionRequestProcessor;
import net.afyer.afybroker.server.plugin.Plugin;

import java.io.File;
import java.util.Locale;

public class TBroker extends Plugin {
    private static FileManager fileManager;
    private static OptionManager optionManager;
    private static Plugin instance;

    @Override
    public void onLoad() {
        System.out.println("[*] 正在初始化 " + this.getDescription().getName() + " .");

        // processors
        System.out.println("[*] 注册 Processors .");
        this.getServer().registerUserProcessor(new OptionRequestProcessor());
        this.getServer().registerUserProcessor(new OptionClassificationRequestProcessor());
    }

    @Override
    public void onEnable() {
        TBroker.instance = this;

        // files
        System.out.println("[*] 初始化配置文件.");
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
        System.out.println("[*] 注册命令.");
        this.getServer().getPluginManager().registerCommand(this, new PluginsCommand());

        // options
        TBroker.optionManager = new SimpleOptionManager();

        System.out.println("[√] 初始化完成.");
    }

    @Override
    public void onDisable() {
        TBroker.getOptionManager().save();

        System.out.println("[#] " + this.getDescription().getName() + " 已卸载.");
    }

    public static OptionManager getOptionManager() {
        return TBroker.optionManager;
    }

    public static Plugin getInstance() {
        return TBroker.instance;
    }
}