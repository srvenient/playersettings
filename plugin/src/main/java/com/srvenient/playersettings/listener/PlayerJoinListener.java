package com.srvenient.playersettings.listener;

import com.srvenient.playersettings.user.User;
import com.srvenient.playersettings.user.UserHandler;
import com.srvenient.playersettings.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final UserManager userManager;
    private final UserHandler userHandler;

    public PlayerJoinListener(
            UserManager userManager,
            UserHandler userHandler
    ) {
        this.userManager = userManager;
        this.userHandler = userHandler;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        final User user = userManager.getUser(uuid);

        this.userManager.updateUser(user);

        if (this.userHandler.isWorldDenied(player.getWorld())) {
            return;
        }

        this.userHandler.performActions(user);
    }

}
