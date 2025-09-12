package me.xiaoying.turtle.broker.module.consumable;

import me.xiaoying.turtle.api.option.Option;
import me.xiaoying.turtle.broker.module.Module;
import me.xiaoying.turtle.api.option.TOption;

public class Consumable extends Module {
    @Override
    public String getKey() {
        return "Consumable";
    }

    @Override
    public String getName() {
        return "消耗物";
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public Option[] getOptions() {
        return new Option[] {
                new TOption("Consumable", "Enable", "false", "开关")
        };
    }
}