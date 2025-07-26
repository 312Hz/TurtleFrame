package me.xiaoying.turtle.bukkit.option;

import com.alipay.remoting.exception.RemotingException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.xiaoying.turtle.api.messsage.OptionClassificationRequestMessage;
import me.xiaoying.turtle.api.messsage.OptionClassificationResponseMessage;
import net.afyer.afybroker.client.Broker;

import java.util.*;

@Accessors(chain = true)
@Getter
public enum Option {
    OVERALL_SITUATION_VARIABLE_PREFIX("OverallSituation", "Variable.Prefix"),
    OVERALL_SITUATION_VARIABLE_DATEFORMAT("OverallSituation", "Variable.DateFormat"),

    COMMAND_MAIN("Command", "Main"),
    COMMAND_USAGE_PARAMETER("Command", "Usage.Parameter"),
    COMMAND_USAGE_MISSING_PARAMETER("Command", "Usage.MissingParameter"),
    COMMAND_PARAMETER_DEFAULT("Command", "Parameter.Default"),
    COMMAND_PARAMETER_MISSING("Command", "Parameter.Missing"),
    COMMAND_PARAMETER_INFINITY("Command", "Parameter.Infinity"),
    COMMAND_MISSING_DESCRIPTION("Command", "MissingDescription");

    private final String classification;

    private final String key;

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

    /**
     * 初始化 Option
     */
    public static void initialize() {
        List<OptionClassificationRequestMessage> messages = new ArrayList<>();
        messages.add(new OptionClassificationRequestMessage("OverallSituation"));
        messages.add(new OptionClassificationRequestMessage("Command"));

        messages.forEach(message -> {
            try {
                OptionClassificationResponseMessage response = Broker.invokeSync(message);
                response.getOptions().forEach(option -> {
                    Option o = Option.getOption(option.getClassification(), option.getKey());

                    if (o == null)
                        return;

                    o.setValue(option.getValue());
                });
            } catch (RemotingException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}

final class Values {
    public static Map<String, Map<String, Option>> options = new HashMap<>();
}