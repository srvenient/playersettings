package team.emptyte.mercurysettings.plugin.listener;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import team.emptyte.mercurysettings.api.registry.KeyedRegistry;
import team.emptyte.mercurysettings.api.user.User;
import team.emptyte.mercurysettings.plugin.MercurySettings;

public class PlayerToggleFlightListener implements Listener {

    private final MercurySettings plugin;
    private final KeyedRegistry<User> users;

    public PlayerToggleFlightListener(MercurySettings plugin, KeyedRegistry<User> users) {
        this.plugin = plugin;
        this.users = users;
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        User user = users.get(player.getUniqueId().toString());


        if (user.getSetting("double-jump").getState() == (byte) 0) {
            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                return;
            }

            event.setCancelled(true);

            player.setAllowFlight(false);
            player.setFlying(false);

            player.setVelocity(player.getLocation().getDirection().multiply(1.5D).setY(1));
            player.playSound(player, Sound.ENTITY_MAGMA_CUBE_SQUISH, 1, 1);
        }
    }


}
