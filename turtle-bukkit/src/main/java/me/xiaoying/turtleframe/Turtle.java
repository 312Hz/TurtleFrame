package me.xiaoying.turtleframe;

import me.xiaoying.turtle.api.core.TCore;
import me.xiaoying.turtle.api.utils.ServerUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class Turtle extends JavaPlugin {
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

    }

    public static void unInitialize() {

    }
}