package me.xiaoying.turtle.api.option;

import java.util.List;

public interface OptionManager {
    /**
     * 获取 Option
     *
     * @param classification 分类
     * @param key 关键值
     * @return Option
     */
    Option getOption(String classification, String key);

    /**
     * 获取 classification 的所有 Option
     *
     * @param classification 分类
     * @return Option
     */
    List<Option> getOptions(String classification);

    /**
     * 添加 Option
     *
     * @param option Option 对象
     */
    void addOption(Option option);

    /**
     * 移除 Option
     *
     * @param classification 分类
     * @param key 关键值
     */
    void removeOption(String classification, String key);

    void removeOption(Option option);

    /**
     * 存储数据
     */
    void save();
}