package me.xiaoying.turtle.api.option;

public interface Option {
    String getValue();

    void setValue(String value);

    String getClassification();

    String getKey();

    void setDescription(String description);
}