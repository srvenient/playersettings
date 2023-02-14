package com.srvenient.playersettings.listener;

import com.srvenient.playersettings.user.User;
import com.srvenient.playersettings.user.UserHandler;
import com.srvenient.playersettings.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.UUID;

public class PlayerChangedWorldListener implements Listener {

    private final UserManager userManager;
    private final UserHandler userHandler;

    public PlayerChangedWorldListener(
            UserManager userManager,
            UserHandler userHandler
    ) {
        this.userManager = userManager;
        this.userHandler = userHandler;
    }

    @EventHandler
    public void onPlayerChanged(PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        final User user = this.userManager.getSync(player.getUniqueId().toString());

        if (user == null) {
            return;
        }

        this.userHandler.performActions(user);
    }
}
