package com.srvenient.playersettings.listener;

import com.srvenient.playersettings.user.User;
import com.srvenient.playersettings.user.UserHandler;
import com.srvenient.playersettings.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

import static com.srvenient.playersettings.utils.ColorUtils.colorize;

public class AsyncPlayerChatListener implements Listener {

    private final UserManager userManager;
    private final UserHandler userHandler;
    private final FileConfiguration configuration;

    public AsyncPlayerChatListener(
            UserManager userManager,
            UserHandler userHandler,
            FileConfiguration configuration
    ) {
        this.userManager = userManager;
        this.userHandler = userHandler;
        this.configuration = configuration;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        final User user = userManager.getUser(uuid);

        if (userHandler.isWorldDenied(player.getWorld())) {
            return;
        }

        if (user.getSettingState("chat") == 1) {
            event.setCancelled(true);
            event.getRecipients().remove(player);

            player.sendMessage(colorize(configuration.getString("config.chat-deactivated")));

            return;
        }

        for (Player players :
                Bukkit.getOnlinePlayers()) {
            final User users = userManager.getUser(players.getUniqueId());

            if (users.getSettingState("chat") == 1) {
                event.getRecipients().remove(players);
            }
        }
    }

}
