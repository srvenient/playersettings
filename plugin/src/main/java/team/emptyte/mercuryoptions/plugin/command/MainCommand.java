package team.emptyte.mercuryoptions.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.emptyte.mercuryoptions.api.SettingHandler;

public class MainCommand implements CommandExecutor {

    private final SettingHandler settingHandler;

    public MainCommand(SettingHandler settingHandler) {
        this.settingHandler = settingHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            switch (args[0]) {
                case "menu" -> settingHandler.openMenu(player);
                case "es" -> {

                }
                default -> {
                }
            }
        }

        return false;
    }
}
