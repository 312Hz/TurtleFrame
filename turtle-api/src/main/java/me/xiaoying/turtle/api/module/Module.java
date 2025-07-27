package me.xiaoying.turtle.api.module;

import me.xiaoying.turtle.api.command.Command;
import me.xiaoying.turtle.api.command.SCommand;
import me.xiaoying.turtle.api.core.TCore;
import me.xiaoying.turtle.api.file.SFile;
import me.xiaoying.turtle.api.placeholder.PlaceholderModule;
import me.xiaoying.turtle.api.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    protected boolean enabled = false;

    private final List<SFile> files = new ArrayList<>();
    private final List<SCommand> commands = new ArrayList<>();
    private final List<Listener> listeners = new ArrayList<>();
    private final List<Scheduler> schedulers = new ArrayList<>();
    private final List<PlaceholderModule> placeholders = new ArrayList<>();

    /**
     * Get name of Module
     *
     * @return String
     */
    public abstract String getName();

    /**
     * Get alias name of Module
     *
     * @return String
     */
    public abstract String getAliasName();

    /**
     * Decide whether to enable module
     *
     * @return Boolean
     */
    public abstract boolean ready();

    /**
     * Get Listeners
     *
     * @return ArrayList
     */
    public List<Listener> getListeners() {
        return this.listeners;
    }

    /**
     * Register listener
     *
     * @param listener Listener
     */
    public void registerListener(Listener listener) {
        if (this.listeners.contains(listener))
            return;

        this.listeners.add(listener);

        // register listener immediately if module opened
        if (!this.enabled)
            return;

        Bukkit.getPluginManager().registerEvents(listener, TCore.getInstance());
    }

    /**
     * Unregister listener
     *
     * @param listener Listener
     */
    public void unregisterListener(Listener listener) {
        if (!this.listeners.contains(listener))
            return;

        this.listeners.remove(listener);
        HandlerList.unregisterAll(listener);
    }

    /**
     * Unregister all listeners
     */
    public void unregisterListeners() {
        this.listeners.forEach(HandlerList::unregisterAll);
        this.listeners.clear();
    }

    /**
     * Register scheduler
     *
     * @param scheduler Scheduler
     */
    public void registerScheduler(Scheduler scheduler) {
        if (this.schedulers.contains(scheduler))
            return;

        this.schedulers.add(scheduler);

        // register listener immediately if module opened
        if (!this.enabled)
            return;

        scheduler.run();
    }

    /**
     * Unregister scheduler
     *
     * @param scheduler Scheduler
     */
    public void unregisterScheduler(Scheduler scheduler) {
        if (!this.schedulers.contains(scheduler))
            return;

        scheduler.stop();
        this.schedulers.remove(scheduler);
    }

    /**
     * Unregister all schedulers
     */
    public void unregisterSchedulers() {
        this.schedulers.forEach(Scheduler::stop);
        this.schedulers.clear();
    }

    /**
     * Register file
     *
     * @param SFile File
     */
    public void registerFile(SFile SFile) {
        if (this.files.contains(SFile))
            return;

        this.files.add(SFile);
        SFile.load();
    }

    /**
     * Get files of module
     *
     * @return ArrayList
     */
    public List<SFile> getFiles() {
        return this.files;
    }

    /**
     * Register Command
     *
     * @param command SCommand
     */
    public void registerCommand(SCommand command) {
        if (this.commands.contains(command))
            return;

        this.commands.add(command);

        // register command immediately if module opened
        if (!this.enabled)
            return;

        Command annotation = command.getClass().getAnnotation(Command.class);

        if (annotation == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFined some command(" + command.getClass().getName() + ") don't use Command annotation, please check your code!"));
            return;
        }

        for (String value : annotation.values())
            TCore.getPluginManager().registerCommand(value, command.getTabExecutor(), TCore.getInstance());
    }

    /**
     * Unregister command
     *
     * @param command SCommand
     */
    public void unregisterCommand(SCommand command) {
        command.getValues().forEach(string -> TCore.getPluginManager().unregisterCommand(string, TCore.getInstance()));
    }

    /**
     * Unregister all commands
     */
    public void unregisterCommands() {
        this.commands.forEach(this::unregisterCommand);
    }

    /**
     * Get registered commands of Module
     *
     * @return ArrayList
     */
    public List<SCommand> getCommands() {
        return this.commands;
    }

    /**
     * Register Placeholder
     *
     * @param placeholderModule PlaceholderModule
     */
    public void registerPlaceholder(PlaceholderModule placeholderModule) {
        if (this.placeholders.contains(placeholderModule))
            return;

        this.placeholders.add(placeholderModule);
    }

    /**
     * Unregister Placeholder
     *
     * @param placeholderModule PlaceholderModule
     */
    public void unregisterPlaceholder(PlaceholderModule placeholderModule) {
        if (!this.placeholders.contains(placeholderModule))
            return;

        this.placeholders.add(placeholderModule);
    }

    /**
     * Get registered placeholder of Module
     *
     * @return ArrayList
     */
    public List<PlaceholderModule> getPlaceholders() {
        return this.placeholders;
    }

    /**
     * Initialize module
     */
    public abstract void init();

    public void enable() {
        this.enabled = true;
        // register listeners
        this.listeners.forEach(listener ->  Bukkit.getPluginManager().registerEvents(listener, TCore.getInstance()));
        // register commands
        this.commands.forEach(command -> {
            Command annotation = command.getClass().getAnnotation(Command.class);

            if (annotation == null) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFined some command(" + command.getClass().getName() + ") don't use Command annotation, please check your code!"));
                return;
            }

            for (String value : annotation.values())
                TCore.getPluginManager().registerCommand(value, command.getTabExecutor(), TCore.getInstance());
        });
        // scheduler
        this.schedulers.forEach(Scheduler::run);
        // placeholder
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            this.placeholders.forEach(PlaceholderModule::register);

        this.onEnable();
    }

    public void disable() {
        this.enabled = false;
        this.onDisable();

        // unregister Scheduler
        this.schedulers.forEach(Scheduler::stop);
        this.schedulers.clear();

        // unregister listeners
        this.listeners.forEach(HandlerList::unregisterAll);
        this.listeners.clear();

        // unregister commands
        this.commands.forEach(command -> command.getValues().forEach(string -> TCore.getPluginManager().unregisterCommand(string, TCore.getInstance())));
        this.commands.clear();

        // unregister files
        this.files.forEach(SFile::disable);
        this.files.clear();

        // unregister placeholder
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            this.placeholders.forEach(PlaceholderModule::unregister);
        this.placeholders.clear();
    }

    public void reload() {
        this.disable();

        this.init();

        if (!this.ready()) {
            // keep files exclude scheduler
            this.schedulers.forEach(Scheduler::stop);
            this.schedulers.clear();

            this.listeners.forEach(HandlerList::unregisterAll);
            this.listeners.clear();

            this.commands.forEach(command -> command.getValues().forEach(string -> TCore.getPluginManager().unregisterCommand(string, TCore.getInstance())));
            this.commands.clear();

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
                this.placeholders.forEach(PlaceholderModule::unregister);
            this.placeholders.clear();
            return;
        }

        this.enable();
    }

    public abstract void onEnable();
    public abstract void onDisable();

    /**
     * Get module is enabled
     *
     * @return Boolean
     */
    public boolean isEnabled() {
        return this.enabled;
    }
}