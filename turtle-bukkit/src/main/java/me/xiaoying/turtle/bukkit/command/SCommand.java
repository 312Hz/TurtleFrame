package me.xiaoying.turtle.bukkit.command;

import me.xiaoying.turtle.api.factory.VariableFactory;
import me.xiaoying.turtle.bukkit.option.Option;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Command SubCommand
 */
public abstract class SCommand {
    private SCommand parent = null;

    private final Map<String, List<RegisteredCommand>> registeredCommands = new HashMap<>();

    private List<String> permissions = new ArrayList<>();

    private String description = null;

    /**
     * Get parent of this command
     *
     * @return Parent command
     */
    public SCommand getParent() {
        return this.parent;
    }

    /**
     * Set the command's parent command<br>
     * eg: sb reload "sb" is parent for "reload"
     *
     * @param command parent command
     */
    public void setParent(SCommand command) {
        this.parent = command;
    }

    /**
     * Get registered commands
     *
     * @return HashMap
     */
    public Map<String, List<RegisteredCommand>> getRegisteredCommands() {
        return this.registeredCommands;
    }

    /**
     * Register new command
     *
     * @param subCommand SubCommand
     */
    public void registerCommand(SCommand subCommand) {
        Command command = subCommand.getClass().getAnnotation(Command.class);

        if (command == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFined some command(" + subCommand.getClass().getName() + ") don't use Command annotation, please check your code!"));
            return;
        }

        subCommand.setParent(this);

        for (String s : command.values()) {
            List<RegisteredCommand> list = new ArrayList<>();
            for (int i : command.length())
                list.add(new RegisteredCommand(i, subCommand));

            this.registeredCommands.put(s, list);
        }
    }

    /**
     * Get command set value
     *
     * @return command's names
     */
    public List<String> getValues() {
        Command command = this.getClass().getAnnotation(Command.class);
        return new ArrayList<>(Arrays.asList(command.values()).subList(0, command.values().length));
    }

    /**
     * Get command supported length
     *
     * @return ArrayList
     */
    public List<Integer> getLengths() {
        List<Integer> list = new ArrayList<>();
        for (int i : this.getClass().getAnnotation(Command.class).length())
            list.add(i);
        return list;
    }

    /**
     * Get command's description
     *
     * @return command's description
     */
    public String getDescription() {
        if (this.description != null)
            return this.description;

        Command command = this.getClass().getAnnotation(Command.class);
        if (command.description().isEmpty() && (this.description == null || this.description.isEmpty()))
            return Option.COMMAND_MISSING_DESCRIPTION.getValue();

        return this.description = command.description();
    }

    /**
     * Set command's description
     *
     * @param description command's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get command's parameters
     *
     * @return command's parameters
     */
    public List<String> getParameters() {
        Command command = this.getClass().getAnnotation(Command.class);
        return new ArrayList<>(Arrays.asList(command.parameters()));
    }

    /**
     * Get command's permissions
     *
     * @return command's permissions
     */
    public List<String> getPermissions() {
        if (!this.permissions.isEmpty())
            return this.permissions;

        Command command = this.getClass().getAnnotation(Command.class);

        return this.permissions = new ArrayList<>(Arrays.asList(command.permission()));
    }

    /**
     * Get help command for this command
     *
     * @return ArrayList
     */
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();

        List<String> values = this.getValues();
        // master
        VariableFactory masterFactory = new VariableFactory(Option.COMMAND_MAIN.toString());

        String commandHead = this.getValues().get(0);
        SCommand parent = this.getParent();

        while (parent != null) {
            commandHead = parent.getValues().get(0) + " " + commandHead;
            parent = parent.getParent();
        }

        masterFactory = masterFactory.command(commandHead).description(this.getDescription());

        // alias
        List<String> alias = new ArrayList<>();
        for (int i = 1; i < values.size(); i++)
            alias.add(values.get(i));

        masterFactory = masterFactory.command_alias(alias);

        // usage
        if (this.getRegisteredCommands().isEmpty()) {
            List<String> parameters = this.getParameters();
            StringBuilder parameterBuilder = new StringBuilder();

            VariableFactory usageFactory = parameters.isEmpty() || (parameters.size() == 1 && parameters.get(0).isEmpty()) ? new VariableFactory(Option.COMMAND_USAGE_MISSING_PARAMETER.toString()) : new VariableFactory(Option.COMMAND_USAGE_PARAMETER.toString());

            // get the biggest length of this command
            int biggest = 0;
            for (Integer length : this.getLengths()) {
                if (length == -1) {
                    biggest = -1;
                    break;
                }

                if (length < biggest)
                    continue;

                biggest = length;
            }

            if (biggest == -1) {
                usageFactory = new VariableFactory(Option.COMMAND_USAGE_PARAMETER.toString());
                parameterBuilder.append(Option.COMMAND_PARAMETER_INFINITY);
            } else {
                for (int i = 0; i < biggest; i++) {
                    parameterBuilder.append(new VariableFactory(Option.COMMAND_PARAMETER_DEFAULT.toString()).parameter(parameters.get(i)));

                    if (i >= parameters.size())
                        continue;

                    parameterBuilder.append(" ");
                }
            }

            list.add(masterFactory.prefix(Option.OVERALL_SITUATION_VARIABLE_PREFIX.toString())
                    .date(Option.OVERALL_SITUATION_VARIABLE_DATEFORMAT.toString())
                    .command_usage(usageFactory.command(commandHead).parameter(parameterBuilder.toString()).toString())
                    .description(this.getDescription())
                    .color()
                    .toString());
            return list;
        }

        List<String> usageList = new ArrayList<>();
        for (String s : this.getRegisteredCommands().keySet()) {
            StringBuilder parameterBuilder = new StringBuilder();

            // get the biggest length of the command
            int biggest = 0;
            List<String> parameters = new ArrayList<>();
            String description = "";
            for (RegisteredCommand command : this.getRegisteredCommands().get(s)) {
                if (command.getLength() == -1) {
                    biggest = -1;
                    parameters = command.getSubCommand().getParameters();
                    description = command.getSubCommand().getDescription();
                    break;
                }

                if (command.getLength() < biggest)
                    continue;

                biggest = command.getLength();
                parameters = command.getSubCommand().getParameters();
                description = command.getSubCommand().getDescription();
            }

            VariableFactory usageFactory = (parameters.isEmpty() && biggest != -1) || (parameters.size() == 1 && parameters.get(0).isEmpty()) ? new VariableFactory(Option.COMMAND_USAGE_MISSING_PARAMETER.toString()) : new VariableFactory(Option.COMMAND_USAGE_PARAMETER.toString());

            if (biggest == -1) {
                usageFactory = new VariableFactory(Option.COMMAND_USAGE_PARAMETER.toString());
                parameterBuilder.append(Option.COMMAND_PARAMETER_INFINITY);
                usageList.add(usageFactory.command(commandHead + " " + s).parameter(parameterBuilder.toString()).description(description).toString());
                continue;
            }

            for (int i = 0; i < biggest; i++) {
                if (parameters.isEmpty())
                    break;

                String parameter = parameters.get(i);

                if (!parameter.isEmpty())
                    parameterBuilder.append(new VariableFactory(Option.COMMAND_PARAMETER_DEFAULT.toString()).parameter(parameter));

                if (i >= parameters.size() - 1) {
                    int result = Math.abs(biggest - parameters.size());
                    if (result != 0)
                        parameterBuilder.append(" ").append(new VariableFactory(Option.COMMAND_PARAMETER_MISSING.toString()).amount(Math.abs(biggest - parameters.size())));
                    break;
                }

                parameterBuilder.append(" ");
            }

            usageList.add(usageFactory.command(commandHead + " " + s).parameter(parameterBuilder.toString()).description(description).toString());
        }

        list.add(masterFactory.prefix(Option.OVERALL_SITUATION_VARIABLE_PREFIX.toString())
                .date(Option.OVERALL_SITUATION_VARIABLE_DATEFORMAT.toString())
                .command_usage(usageList)
                .description(this.getDescription())
                .color()
                .toString());

        return list;
    }

    /**
     * Default handle of child command which registered in this command
     *
     * @param sender Sender
     * @param args   Command's functions
     */
    protected void parentCommand(CommandSender sender, String[] args) {
        // 判断命令长度
        if (args.length == 0) {
            this.getHelpMessage().forEach(sender::sendMessage);
            return;
        }

        // 判断是否存在相应命令
        String head = args[0];
        if (!this.getRegisteredCommands().containsKey(head)) {
            this.getHelpMessage().forEach(sender::sendMessage);
            return;
        }

        // 移除 head
        List<String> list = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
        args = list.toArray(new String[0]);

        boolean isDo = false;
        for (RegisteredCommand registeredCommand : this.getRegisteredCommands().get(head)) {
            if (registeredCommand.getLength() != args.length && registeredCommand.getLength() != -1)
                continue;

            registeredCommand.getSubCommand().performCommand(sender, args);
            isDo = true;
        }

        if (isDo)
            return;

        // 未执行则发出帮助信息
        this.getRegisteredCommands().get(head).get(0).getSubCommand().getHelpMessage().forEach(sender::sendMessage);
    }

    /**
     * Perform command
     *
     * @param sender Sender
     * @param args   Command's functions
     */
    public abstract void performCommand(CommandSender sender, String[] args);

    /**
     * Perform tab<br>
     * If you don't want sender get command help message, you can override this method and return empty list
     *
     * @param sender  Sender
     * @param command Command
     * @param head    Command head
     * @param strings Command's functions
     * @return ArrayList
     */
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = new ArrayList<>();

        // determine sender has permissions
        this.registeredCommands.forEach((string, cmd) -> {
            if (cmd.isEmpty())
                return;

            List<String> permissions = cmd.get(0).getSubCommand().getPermissions();

            boolean hasPermission = true;
            // if any permission is missing that will skip this command
            for (String permission : permissions) {
                if (sender.hasPermission(permission) && sender.isOp())
                    continue;

                hasPermission = false;
                break;
            }

            if (!hasPermission)
                return;

            list.add(string);
        });

        if (strings.length == 1) {
            List<String> conditionList = new ArrayList<>();
            for (String s1 : list) {
                if (!s1.toUpperCase(Locale.ENGLISH).startsWith(strings[0].toUpperCase(Locale.ENGLISH)))
                    continue;
                conditionList.add(s1);
            }

            if (conditionList.isEmpty())
                return list;
            return conditionList;
        }

        List<RegisteredCommand> registeredCommand = this.registeredCommands.get(strings[0]);
        if (registeredCommand == null)
            return new ArrayList<>();

        strings = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length)).toArray(new String[0]);
        for (RegisteredCommand registeredCommand1 : registeredCommand) {
            List<String> l;
            if ((l = registeredCommand1.getSubCommand().onTabComplete(sender, command, head, strings)) == null)
                return null;

            return l;
        }
        return new ArrayList<>();
    }

    /**
     * Get bukkit TabExecutor
     *
     * @return TabExecutor
     */
    public TabExecutor getTabExecutor() {
        return new TabExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String s, @NotNull String[] strings) {
                if (!SCommand.this.getLengths().contains(strings.length) && !SCommand.this.getLengths().contains(-1)) {
                    SCommand.this.getHelpMessage().forEach(sender::sendMessage);
                    return false;
                }
                performCommand(sender, strings);
                return true;
            }

            @Nullable
            @Override
            public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String s, @NotNull String[] strings) {
                return SCommand.this.onTabComplete(sender, cmd, s, strings);
            }
        };
    }
}