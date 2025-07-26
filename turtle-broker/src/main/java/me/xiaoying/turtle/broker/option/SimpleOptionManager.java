package me.xiaoying.turtle.broker.option;

import me.xiaoying.sqlfactory.sentence.Create;
import me.xiaoying.sqlfactory.sentence.Insert;
import me.xiaoying.sqlfactory.sentence.Select;
import me.xiaoying.sqlfactory.sentence.Update;
import me.xiaoying.turtle.api.core.TCore;
import me.xiaoying.turtle.api.option.Option;
import me.xiaoying.turtle.api.option.OptionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleOptionManager implements OptionManager {
    // 采用多 key 进行存储
    private final Map<String, Map<String, Option>> options;

    public SimpleOptionManager() {
        this.options = new HashMap<>();

        // 创建数据表
        TCore.getSqlFactory().run(new Create(Option.class));
        // 读取数据
        List<Object> run = TCore.getSqlFactory().run(new Select(Option.class));

        // 当数据为空，则写入数据
        if (run.isEmpty()) {
            // 初始化数据
            List<Option> list = new ArrayList<>();
            list.add(new Option("OverallSituation", "Variable.Prefix", "&a[&bTurtleFrame&a]&e>> ", "前缀(%prefix%)"));
            list.add(new Option("OverallSituation", "Variable.DateFormat", "yyyy/MM/dd-HH:mm:ss", "日期格式(%date%)"));

            list.add(new Option("Command", "Main", "\n&c! [帮助信息]\n&c|- &b%command% &8-> &a%description%\n   &8├─ &6Alias: &e%command_aliases%\n   &8└─ &6Usage: \n%command_usage%\n&r\n", "主题模板"));
            list.add(new Option("Command", "Usage.Parameter", "\\r\\r\\r\\r\\r\\r\\r\\r\\r\\r&8├─ &b/%command% &7%parameter% &8-> &a%description%", "当命令存在传递参数时显示的内容"));
            list.add(new Option("Command", "Usage.MissingParameter", "\\r\\r\\r\\r\\r\\r\\r\\r\\r\\r&8├─ &b/%command% &8-> &a%description%", "当命令不存在传递参数时显示的内容"));
            list.add(new Option("Command", "Parameter.Default", "< %parameter% >", "正常的命令传参提示"));
            list.add(new Option("Command", "Parameter.Missing", "< %amount% 个参数>", "当命令没有配置对应的传递参数则应该显示内容"));
            list.add(new Option("Command", "Parameter.Infinity", "< Infinity 个参数>", "当命令设置的传递参数需要无限个时显示内容"));
            list.add(new Option("Command", "MissingDescription", "无描述", "当没有描述的时候应该显示什么"));
            list.forEach(this::addOption);
            return;
        }

        for (Object object : run) {
            Option option = (Option) object;

            Map<String, Option> map = this.options.get(option.getClassification());

            if (map == null)
                map = new HashMap<>();

            map.put(option.getKey(), option);
            this.options.put(option.getClassification(), map);
        }
    }

    private void putOption(Option option) {
        Map<String, Option> map = this.options.get(option.getClassification());

        if (map == null)
            map = new HashMap<>();

        map.put(option.getKey(), option);
        this.options.put(option.getClassification(), map);
    }

    @Override
    public Option getOption(String classification, String key) {
        Map<String, Option> map = this.options.get(classification);

        if (map != null)
            return map.get(key);

        // 当缓存中不存在应该尝试从数据库中读取
        List<Object> objects = TCore.getSqlFactory().run(new Select(new Option(classification, key)));
        if (objects.isEmpty())
            return null;

        Option option = (Option) objects.get(0);
        this.putOption(option);
        return option;
    }

    @Override
    public List<Option> getOptions(String classification) {
        if (this.options.get(classification) == null)
            return new ArrayList<>();

        return new ArrayList<>(this.options.get(classification).values());
    }

    @Override
    public void addOption(Option option) {
        Map<String, Option> options;
        if ((options = this.options.get(option.getClassification())) == null) {
            this.options.put(option.getClassification(), new HashMap<>());
            options = this.options.get(option.getClassification());
        }

        options.put(option.getKey(), option);
        this.options.put(option.getClassification(), options);

        option.setNeedInsert(true);
    }

    @Override
    public void removeOption(String classification, String key) {
        Map<String, Option> options = this.options.get(classification);

        if (options == null)
            return;

        Option option = options.get(key);
        if (option == null)
            return;

        options.remove(key);

        this.options.put(classification, options);
        option.remove();
    }

    @Override
    public void removeOption(Option option) {
        this.removeOption(option.getClassification(), option.getKey());
    }

    @Override
    public void save() {
        this.options.values().forEach(map -> map.values().forEach(option -> {
            if (option.modified()) {
                TCore.getSqlFactory().run(new Update(option));
                return;
            }

            if (!option.isNeedInsert())
                return;

            Insert insert = new Insert(option);
            System.out.println(insert);
            TCore.getSqlFactory().run(insert);
        }));
    }
}