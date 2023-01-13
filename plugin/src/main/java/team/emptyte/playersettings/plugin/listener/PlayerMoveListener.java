package team.emptyte.playersettings.plugin.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.user.User;

public class PlayerMoveListener implements Listener {

    private final ModelService<User> users;

    public PlayerMoveListener(ModelService<User> users) {
        this.users = users;
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        User user = users.findSync(player.getUniqueId().toString());

        if (user == null) {
            System.out.printf("User not exist");

            return;
        }

        if (user.getSettingStatus("double-jump") == 0
                && player.getGameMode() != GameMode.CREATIVE
                && player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR
                && !player.isFlying()) {

            player.setAllowFlight(true);
        }
    }

}
