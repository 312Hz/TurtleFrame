package me.xiaoying.turtle.api.factory;

import me.xiaoying.turtle.api.utils.ColorUtil;
import me.xiaoying.turtle.api.utils.DateUtil;

import java.util.List;

public class VariableFactory {
    private String string;

    public VariableFactory(String string) {
        this.string = string;
    }

    public VariableFactory amount(int amount) {
        this.string = this.string.replace("%amount%", String.valueOf(amount));
        return this;
    }

    public VariableFactory amount(String amount) {
        this.string = this.string.replace("%amount%", amount);
        return this;
    }
    public VariableFactory command(String command) {
        this.string = this.string.replace("%command%", command);
        return this;
    }


    public VariableFactory command_alias(String alias) {
        this.string = this.string.replace("%command_aliases%", alias);
        return this;
    }

    public VariableFactory command_alias(List<String> alias) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < alias.size(); i++) {
            stringBuilder.append(alias.get(i));

            if (i == alias.size() - 1)
                break;

            stringBuilder.append(", ");
        }
        this.string = this.string.replace("%command_aliases%", stringBuilder.toString());
        return this;
    }

    public VariableFactory command_usage(List<String> usage) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < usage.size(); i++) {
            stringBuilder.append(usage.get(i));

            if (i == usage.size() - 1)
                break;

            stringBuilder.append("\n");
        }

        this.command_usage(stringBuilder.toString());
        return this;
    }

    public VariableFactory command_usage(String usage) {
        this.string = this.string.replace("%command_usage%", usage);
        return this;
    }

    public VariableFactory date(String format) {
        this.string = this.string.replace("%date%", DateUtil.getDate(format));
        return this;
    }

    public VariableFactory description(String description) {
        this.string = this.string.replace("%description%", description);
        return this;
    }

    public VariableFactory parameter(String parameter) {
        this.string = this.string.replace("%parameter%", parameter);
        return this;
    }

    public VariableFactory prefix(String prefix) {
        this.string = this.string.replace("%prefix%", prefix);
        return this;
    }

    public VariableFactory color() {
        this.string = ColorUtil.translate(this.string);
        return this;
    }

    @Override
    public String toString() {
        return this.string;
    }
}