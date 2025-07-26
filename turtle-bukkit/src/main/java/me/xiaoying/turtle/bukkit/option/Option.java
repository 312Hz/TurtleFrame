package me.xiaoying.turtle.bukkit.option;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;

@Accessors(chain = true)
@Getter
public enum Option {
    COMMAND_MAIN("command", "Main"),
    COMMAND_MISSING_DESCRIPTION("command", "MissingDescription"),
    CHATFORMAT_FORMAT("chatformat", "format");

    private String classification;

    private String key;

    @Setter
    private String value;

    Option(String classification, String key) {
        Map<String, Option> map;

        if ((map = Values.options.get(classification)) == null)
            map = new HashMap<>();

        map.put(key, this);
        Values.options.put(classification, map);

        this.classification = classification;
        this.key = key;
    }

    public static Option getOption(String classification, String key) {
        Map<String, Option> map = Values.options.get(classification);

        if (map == null)
            return null;

        return map.get(key);
    }

    public static List<Option> getOptions(String classification) {
        if (Values.options.get(classification) == null)
            return Collections.emptyList();

        return new ArrayList<>(Values.options.get(classification).values());
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}

final class Values {
    public static Map<String, Map<String, Option>> options = new HashMap<>();
}