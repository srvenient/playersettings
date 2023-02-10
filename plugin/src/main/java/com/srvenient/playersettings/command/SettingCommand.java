package com.srvenient.playersettings.command;

import com.srvenient.playersettings.user.UserHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.srvenient.playersettings.utils.ColorUtils.colorize;

public class SettingCommand implements CommandExecutor, TabCompleter {

    private final FileConfiguration config;
    private final UserHandler userHandler;

    public SettingCommand(
            FileConfiguration config,
            UserHandler userHandler
    ) {
        this.config = config;
        this.userHandler = userHandler;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().info(config.getString("only-players"));

            return true;
        }

        if (args.length == 0) {
            player.sendMessage(colorize(config.getString("messages.help")));
        } else {
            if (args[0].equals("menu")) {
                userHandler.createMenu(player);
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
