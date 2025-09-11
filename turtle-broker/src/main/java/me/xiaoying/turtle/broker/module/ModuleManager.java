package me.xiaoying.turtle.broker.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    private final Map<String, Module> knownModules = new HashMap<>();

    public void registerModule(Module module) {
        this.knownModules.put(module.getName(), module);
    }

    public void unregisterModule(String key) {
        this.knownModules.remove(key);
    }

    public void unregisterModule(Module module) {
        this.knownModules.remove(module.getKey());
    }

    public Module getModule(String name) {
        return this.knownModules.get(name);
    }

    public List<Module> getModules() {
        return new ArrayList<>(this.knownModules.values());
    }
}