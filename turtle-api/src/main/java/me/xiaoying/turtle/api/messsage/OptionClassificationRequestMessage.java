package me.xiaoying.turtle.api.messsage;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OptionClassificationRequestMessage implements Serializable {
    private static final long serialVersionUID = -8843605846228146800L;

    private final String classification;

    public OptionClassificationRequestMessage(String classification) {
        this.classification = classification;
    }
}