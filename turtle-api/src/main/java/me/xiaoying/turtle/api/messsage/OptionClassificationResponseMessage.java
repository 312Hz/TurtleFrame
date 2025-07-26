package me.xiaoying.turtle.api.messsage;

import lombok.Getter;
import me.xiaoying.turtle.api.option.Option;

import java.io.Serializable;
import java.util.List;

@Getter
public class OptionClassificationResponseMessage implements Serializable {
    private static final long serialVersionUID = -6910267930087397140L;

    private final List<Option> options;

    public OptionClassificationResponseMessage(List<Option> options) {
        this.options = options;
    }
}