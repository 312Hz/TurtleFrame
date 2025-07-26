package me.xiaoying.turtle.api.messsage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OptionRemoveMessage implements Serializable {
    private static final long serialVersionUID = -8876892907973702954L;

    private final String classification;
    private final String key;

    public OptionRemoveMessage(String classification, String key) {
        this.classification = classification;
        this.key = key;
    }
}