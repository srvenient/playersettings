package team.emptyte.playersettings.plugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.emptyte.playersettings.api.user.UserManager;

public class PlayerLeaveListener implements Listener {

    private final UserManager userManager;

    public PlayerLeaveListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        userManager.save(event.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        userManager.save(event.getPlayer().getUniqueId().toString());
    }

}
