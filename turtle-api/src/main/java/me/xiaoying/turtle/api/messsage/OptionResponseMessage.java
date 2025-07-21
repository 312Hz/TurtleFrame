package me.xiaoying.turtle.api.messsage;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OptionResponseMessage implements Serializable {
    private static final long serialVersionUID = 5348571088185651815L;

    private final String classification;
    private final String key;
    private final String value;
    private final String description;

    public OptionResponseMessage(String classification, String key, String value, String description) {
        this.classification = classification;
        this.key = key;
        this.value = value;
        this.description = description;
    }
}