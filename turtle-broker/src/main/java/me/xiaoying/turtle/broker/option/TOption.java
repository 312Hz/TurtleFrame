package me.xiaoying.turtle.broker.option;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import me.xiaoying.sqlfactory.annotation.Column;
import me.xiaoying.sqlfactory.annotation.Param;
import me.xiaoying.sqlfactory.annotation.Table;
import me.xiaoying.sqlfactory.sentence.Delete;
import me.xiaoying.sqlfactory.sentence.Insert;
import me.xiaoying.sqlfactory.sentence.Update;
import me.xiaoying.turtle.api.core.TCore;
import me.xiaoying.turtle.api.option.Option;

import java.io.Serializable;

@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "option_info")
public class TOption implements Option, Serializable {
    private static final long serialVersionUID = -5751428627060913267L;

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

    public TOption(String classification, String key) {
        this.classification = classification;
        this.key = key;
    }

    public TOption(@Param("classification") String classification, @Param("key") String key, @Param("value") String value, @Param("description") String description) {
        this.classification = classification;
        this.key = key;
        this.value = value;
        this.description = description;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
        this.modified = true;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
        this.modified = true;
    }

    public boolean modified() {
        return this.modified;
    }

    public void save() {
        if (this.needInsert) {
            TCore.getSqlFactory().run(new Insert(this));
            return;
        }

        TCore.getSqlFactory().run(new Update(this));
    }

    public void remove() {
        if (this.needInsert)
            return;

        TCore.getSqlFactory().run(new Delete(this));
    }
}