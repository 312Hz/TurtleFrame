package me.xiaoying.turtle.api.option;

public interface OptionManager {
    void addOption(Option option);

    void removeOption(String classification, String key);

    void removeOption(Option option);
}