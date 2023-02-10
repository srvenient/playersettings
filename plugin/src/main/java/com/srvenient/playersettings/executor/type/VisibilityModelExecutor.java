package com.srvenient.playersettings.executor.type;

import com.srvenient.playersettings.executor.SettingExecutor;
import com.srvenient.playersettings.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class VisibilityModelExecutor implements SettingExecutor {

    private final Plugin plugin;

    public VisibilityModelExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getId() {
        return "visibility";
    }

    @Override
    public void execute(@NotNull User user) {
        Player player = user.getPlayer();

        if (user.getSettingState(getId()) == 0) {
            user.updateState(getId(), (byte) 1);

            Bukkit.getOnlinePlayers().forEach(players -> {
                if (!players.hasPermission("playersettings.rank")) {
                    player.hidePlayer(plugin, players);
                } else {
                    player.showPlayer(plugin, players);
                }
            });

            return;
        }

        if (user.getSettingState(getId()) == 1) {
            user.updateState(getId(), (byte) 2);

            Bukkit.getOnlinePlayers().forEach(players -> player.hidePlayer(plugin, players));

            return;
        }

        if (user.getSettingState(getId()) == 2) {
            user.updateState(getId(), (byte) 0);

            Bukkit.getOnlinePlayers().forEach(players -> player.showPlayer(plugin, players));
        }
    }
}
