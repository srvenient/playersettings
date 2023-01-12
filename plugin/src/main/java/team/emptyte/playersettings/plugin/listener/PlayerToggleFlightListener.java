package team.emptyte.playersettings.plugin.listener;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.user.User;

public class PlayerToggleFlightListener implements Listener {

    private final ModelService<User> users;

    public PlayerToggleFlightListener(ModelService<User> users) {
        this.users = users;
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        User user = users.findSync(player.getUniqueId().toString());

        if (user == null) {
            System.out.printf("User not exist.");

            return;
        }

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
