package team.emptyte.playersettings.plugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import team.emptyte.playersettings.api.user.UserManager;

public class PlayerJoinListener implements Listener {

    private final UserManager userManager;

    public PlayerJoinListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        userManager.load(event.getPlayer().getUniqueId().toString());
    }

}
