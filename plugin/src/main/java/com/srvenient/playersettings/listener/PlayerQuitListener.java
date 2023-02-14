package com.srvenient.playersettings.listener;

import com.srvenient.playersettings.user.User;
import com.srvenient.playersettings.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final UserManager userManager;

    public PlayerQuitListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        saveUser(event.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        saveUser(event.getPlayer().getUniqueId().toString());
    }


    private void saveUser(@NotNull final String id) {
        final User user = userManager.getSync(id);

        if (user == null) {
            return;
        }

        this.userManager.uploadSync(user);
        this.userManager.deleteSync(id);
    }

}
