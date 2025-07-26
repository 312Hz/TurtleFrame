package me.xiaoying.turtle.bukkit;

import me.xiaoying.turtle.api.core.TCore;
import me.xiaoying.turtle.api.utils.ServerUtil;
import me.xiaoying.turtle.bukkit.option.Option;
import org.bukkit.plugin.java.JavaPlugin;

public class Turtle extends JavaPlugin {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        ServerUtil.sendMessage("&6[*] &b初始化 &e" + this.getName() + " &b.");

        TCore.setInstance(this);

        // initialize
        Turtle.initialize();
        ServerUtil.sendMessage("&a[√] &b初始化完成.");
    }

    @Override
    public void onDisable() {
        Turtle.unInitialize();
        ServerUtil.sendMessage("&6[#] &e" + this.getName() + " &b已卸载.");
    }

    public static void initialize() {
        // 初始化 Option
        ServerUtil.sendMessage("&6[*] &b尝试获取 &eOption &b信息.");
        Option.initialize();
    }

    public static void unInitialize() {

    }
}