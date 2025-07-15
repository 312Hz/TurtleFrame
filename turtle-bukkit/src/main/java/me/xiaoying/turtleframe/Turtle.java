package me.xiaoying.turtleframe;

import org.bukkit.plugin.java.JavaPlugin;

public class Turtle extends JavaPlugin {
    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled TurtleFrame");
    }
}