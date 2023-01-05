package team.emptyte.mercurysettings.plugin.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import team.emptyte.mercurysettings.api.registry.KeyedRegistry;
import team.emptyte.mercurysettings.api.user.User;
import team.emptyte.mercurysettings.plugin.MercurySettings;

public class PlayerMoveListener implements Listener {

    private final KeyedRegistry<User> users;

    public PlayerMoveListener(KeyedRegistry<User> users) {
        this.users = users;
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        User user = users.get(player.getUniqueId().toString());

        if (user.getSetting("double-jump").getState() == 0
                && player.getGameMode() != GameMode.CREATIVE
                && player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR
                && !player.isFlying()) {

            player.setAllowFlight(true);
        }
    }

}
