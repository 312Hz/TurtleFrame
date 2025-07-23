package me.xiaoying.turtle.broker.option;

import me.xiaoying.turtle.api.option.Option;
import me.xiaoying.turtle.api.option.OptionManager;

import java.util.HashMap;
import java.util.Map;

public class SimpleOptionManager implements OptionManager {
    // 采用多 key 进行存储
    private final Map<String, Map<String, Option>> options;

    public SimpleOptionManager() {
        this.options = new HashMap<>();
    }

    @Override
    public Option getOption(String classification, String key) {
        Map<String, Option> map = this.options.get(classification);
        return map == null ? null : map.get(key);
    }

    public void addOption(Option option) {
        Map<String, Option> options;
        if ((options = this.options.get(option.getClassification())) == null) {
            this.options.put(option.getClassification(), new HashMap<>());
            options = this.options.get(option.getClassification());
        }

        options.put(option.getKey(), option);
        this.options.put(option.getClassification(), options);

        option.setNeedInsert(true);
    }

    public void removeOption(String classification, String key) {
        Map<String, Option> options = this.options.get(classification);

        if (options == null)
            return;

        Option option = options.get(key);
        if (option == null)
            return;

        options.remove(key);

        this.options.put(classification, options);
        option.remove();
    }

    public void removeOption(Option option) {
        this.removeOption(option.getClassification(), option.getKey());
    }

    public void save() {

    }
}