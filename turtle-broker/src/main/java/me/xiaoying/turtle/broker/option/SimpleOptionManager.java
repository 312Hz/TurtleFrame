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

    public void addOption(Option option) {
        Map<String, Option> options;
        if ((options = this.options.get(option.getClassification())) == null) {
            this.options.put(option.getClassification(), new HashMap<>());
            options = this.options.get(option.getClassification());
        }

        options.put(option.getKey(), option);
        this.options.put(option.getClassification(), options);
    }

    public void removeOption(String classification, String key) {

    }

    public void removeOption(Option option) {
        this.removeOption(option.getClassification(), option.getKey());
    }
}