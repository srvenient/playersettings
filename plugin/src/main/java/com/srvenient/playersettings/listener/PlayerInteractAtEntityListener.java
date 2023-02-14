package com.srvenient.playersettings.listener;

import com.srvenient.playersettings.user.User;
import com.srvenient.playersettings.user.UserHandler;
import com.srvenient.playersettings.user.UserManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import static com.srvenient.playersettings.utils.ColorUtils.colorize;

public class PlayerInteractAtEntityListener implements Listener {

    private final UserManager userManager;
    private final UserHandler userHandler;
    private final FileConfiguration configuration;

    public PlayerInteractAtEntityListener(
            UserManager userManager,
            UserHandler userHandler, FileConfiguration configuration
    ) {
        this.userManager = userManager;
        this.userHandler = userHandler;
        this.configuration = configuration;
    }

    @EventHandler
    public void onMount(PlayerInteractAtEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        final Entity entity = event.getRightClicked();

        if (entity.hasMetadata("NPC")) {
            return;
        }

        if (entity instanceof Player player) {
            if (this.userHandler.isWorldDenied(player.getWorld())) {
                return;
            }

            final User user = this.userManager.getSync(player.getUniqueId().toString());

            if (user == null) {
                return;
            }

            if (user.getSettingState("mount") == 0) {
                if (!player.hasPermission("playersettings.mount.rank")) {
                    player.sendMessage(colorize(configuration.getString("messages.cannot-be-mounted")));

                    return;
                }
                
                if (player.getPassengers().size() > 0) {
                    for (Entity passenger:
                         player.getPassengers()) {

                        passenger.leaveVehicle();
                    }

                    return;
                }

                entity.addPassenger(player);

                return;
            }

            player.sendMessage(colorize(configuration.getString("messages.has-mount-disabled")));
        }
    }

}
