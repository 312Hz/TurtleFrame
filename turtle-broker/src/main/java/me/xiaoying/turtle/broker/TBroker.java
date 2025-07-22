package me.xiaoying.turtle.broker;

import me.xiaoying.sqlfactory.SqlFactory;
import me.xiaoying.turtle.api.option.Option;
import me.xiaoying.turtle.api.option.OptionManager;
import me.xiaoying.turtle.broker.command.PluginsCommand;
import me.xiaoying.turtle.broker.file.FileManager;
import me.xiaoying.turtle.broker.file.SimpleFileManager;
import me.xiaoying.turtle.broker.file.TBrokerFile;
import me.xiaoying.turtle.broker.option.SimpleOptionManager;
import me.xiaoying.turtle.broker.processor.OptionRequestProcessor;
import net.afyer.afybroker.server.plugin.Plugin;

public class TBroker extends Plugin {
    private static FileManager fileManager;
    private static OptionManager optionManager;
    private static Plugin instance;

    private static SqlFactory sqlFactory;

    @Override
    public void onLoad() {
        System.out.println("[*] 正在初始化 " + this.getDescription().getName() + " .");

        // processors
        System.out.println("[*] 注册 Processors .");
        this.getServer().registerUserProcessor(new OptionRequestProcessor());
    }

    @Override
    public void onEnable() {
        TBroker.instance = this;

        // files
        System.out.println("[*] 初始化配置文件.");
        TBroker.fileManager = new SimpleFileManager();
        TBroker.fileManager.register(new TBrokerFile());
        TBroker.fileManager.loads();

        // commands
        System.out.println("[*] 注册命令.");
        this.getServer().getPluginManager().registerCommand(this, new PluginsCommand());

        // options
        TBroker.optionManager = new SimpleOptionManager();

        System.out.println("[√] 初始化完成.");
    }

    @Override
    public void onDisable() {
        System.out.println("[#] " + this.getDescription().getName() + " 已卸载.");
    }

    public static OptionManager getOptionManager() {
        return TBroker.optionManager;
    }

    public static Plugin getInstance() {
        return TBroker.instance;
    }
}