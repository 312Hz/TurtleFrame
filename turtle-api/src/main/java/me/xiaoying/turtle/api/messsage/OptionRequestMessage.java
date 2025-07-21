package me.xiaoying.turtle.api.messsage;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OptionRequestMessage implements Serializable {
    private static final long serialVersionUID = 6538317829791513150L;

    private final String classification;
    private final String key;

    public OptionRequestMessage(String classification) {
        this.classification = classification;
        this.key = null;
    }

    public OptionRequestMessage(String classification, String key) {
        this.classification = classification;
        this.key = key;
    }
}