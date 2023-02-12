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

    private final UserHandler userHandler;
    private final FileConfiguration configuration;

    public SettingCommand(
            FileConfiguration configuration,
            UserHandler userHandler
    ) {
        this.userHandler = userHandler;
        this.configuration = configuration;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().info(configuration.getString("messages.only-players"));

            return true;
        }

        if (args.length == 0) {
            if (player.isOp()) {
                player.sendMessage(colorize("&fEste complemento esta en su version &a2.1.0&f."));
                player.sendMessage(colorize("                     &7Creado por &cSrVenient"));

                return true;
            }

            player.sendMessage(colorize(configuration.getString("messages.help")));
        } else {
            if (args[0].equals("menu")) {
                if (userHandler.isWorldDenied(player.getWorld())) {
                    return true;
                }

                player.openInventory(userHandler.createMenu(player));
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
