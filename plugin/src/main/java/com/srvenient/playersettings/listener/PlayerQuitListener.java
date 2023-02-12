package com.srvenient.playersettings.listener;

import com.srvenient.playersettings.user.User;
import com.srvenient.playersettings.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final UserManager userManager;

    public PlayerQuitListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        final User user = userManager.getUser(uuid);

        this.userManager.removeUser(user);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        final User user = userManager.getUser(uuid);

        this.userManager.removeUser(user);
    }
}
