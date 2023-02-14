package com.srvenient.playersettings.listener;

import com.srvenient.playersettings.user.User;
import com.srvenient.playersettings.user.UserHandler;
import com.srvenient.playersettings.user.UserManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;

public class PlayerJoinListener implements Listener {

    private final FileConfiguration configuration;
    private final UserManager userManager;
    private final UserHandler userHandler;

    public PlayerJoinListener(
            FileConfiguration configuration,
            UserManager userManager,
            UserHandler userHandler
    ) {
        this.configuration = configuration;
        this.userManager = userManager;
        this.userHandler = userHandler;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String id = player.getUniqueId().toString();

        User user = userManager.getOrFindSync(id);

        if (user == null) {
            user = userManager.saveSync(new User(id, Map.of(
                    "visibility", (byte) configuration.getInt("config.default-values.visibility"),
                    "chat", (byte) configuration.getInt("config.default-values.chat"),
                    "jump", (byte) configuration.getInt("config.default-values.jump"),
                    "mount", (byte) configuration.getInt("config.default-values.mount"),
                    "fly", (byte) configuration.getInt("config.default-values.fly")
            )), true);
        }

        userManager.saveSync(user, false);

        if (this.userHandler.isWorldDenied(player.getWorld())) {
            return;
        }

        this.userHandler.performActions(user);
    }

}
