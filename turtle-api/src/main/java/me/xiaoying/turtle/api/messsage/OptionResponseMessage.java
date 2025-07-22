package me.xiaoying.turtle.api.messsage;

import lombok.Getter;
import me.xiaoying.turtle.api.option.Option;

import java.io.Serializable;

@Getter
public class OptionResponseMessage implements Serializable {
    private static final long serialVersionUID = 5348571088185651815L;

    private final Option option;

    public OptionResponseMessage(Option option) {
        this.option = option;
    }
}