package me.xiaoying.turtle.api.option;

public interface OptionManager {
    Option getOption(String classification, String key);

    void addOption(Option option);

    void removeOption(String classification, String key);

    void removeOption(Option option);

    void save();
}