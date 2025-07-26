package me.xiaoying.turtle.api.option;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import me.xiaoying.sqlfactory.annotation.Column;
import me.xiaoying.sqlfactory.annotation.Param;
import me.xiaoying.sqlfactory.annotation.Table;

import java.io.Serializable;

@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "option_info")
public class Option implements Serializable {
    @Column(length = 255)
    private final String classification;

    @Column(length = 255)
    private final String key;

    @Column(length = 255)
    private String value;

    @Column(length = 255)
    private String description;

    @Setter
    private boolean needInsert = false;

    private boolean modified = false;

    public Option(String classification, String key) {
        this.classification = classification;
        this.key = key;
    }

    public Option(@Param("classification") String classification, @Param("key") String key, @Param("value") String value, @Param("description") String description) {
        this.classification = classification;
        this.key = key;
        this.value = value;
        this.description = description;
    }

    public void setValue(String value) {
        this.value = value;
        this.modified = true;
    }

    public void setDescription(String description) {
        this.description = description;
        this.modified = true;
    }

    public boolean modified() {
        return this.modified;
    }

    public void save() {
        if (this.needInsert) {

            return;
        }
    }

    public void remove() {

    }
}