package me.xiaoying.turtleframe;

import org.bukkit.plugin.java.JavaPlugin;

public class Turtle extends JavaPlugin {
    @Override
    public void onEnable() {
        System.out.println("TurtleFrame");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled TurtleFrame");
    }
}