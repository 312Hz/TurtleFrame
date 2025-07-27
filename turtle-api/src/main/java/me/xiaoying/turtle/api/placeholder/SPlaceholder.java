package me.xiaoying.turtle.api.placeholder;

import org.bukkit.entity.Player;

public interface SPlaceholder {
    String getKey();

    String replace(Player player);
}