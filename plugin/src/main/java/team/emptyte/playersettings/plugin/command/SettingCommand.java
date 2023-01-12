package team.emptyte.playersettings.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.emptyte.playersettings.api.SettingHandler;
import team.emptyte.playersettings.api.message.MessageHandler;

import java.util.Collections;
import java.util.List;

public class SettingCommand implements CommandExecutor, TabCompleter {

    private final MessageHandler messageHandler;
    private final SettingHandler settingHandler;

    public SettingCommand(MessageHandler messageHandler, SettingHandler settingHandler) {
        this.messageHandler = messageHandler;
        this.settingHandler = settingHandler;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args
    ) {
        if (!(sender instanceof Player)) {
            messageHandler.sendMessages(sender, "only-players");

            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            messageHandler.sendMessages(player, "help");
        } else {
            if (args[0].equals("menu")) {
                settingHandler.openMenu(player);
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args
    ) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        if (args[0].equals("menu")) {
            return Collections.singletonList("menu");
        }

        return Collections.emptyList();
    }
}
