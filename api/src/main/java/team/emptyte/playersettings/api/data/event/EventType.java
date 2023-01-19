package team.emptyte.playersettings.api.data.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum EventType {
    VISIBILITY((plugin, user) -> {
        Player player = user.getPlayer();
        String id = "visibility";

        if (user.getSettingStatus(id) == 0) {
            user.updateSettingStatus(id, 1);

            Bukkit.getOnlinePlayers().forEach(players -> {
                if (!players.hasPermission("playersettings.rank")) {
                    player.hidePlayer(plugin, players);
                } else {
                    player.showPlayer(plugin, players);
                }
            });

            return;
        }

        if (user.getSettingStatus(id) == 1) {
            user.updateSettingStatus(id, 2);

            Bukkit.getOnlinePlayers().forEach(players -> player.hidePlayer(plugin, players));

            return;
        }

        if (user.getSettingStatus(id) == 2) {
            user.updateSettingStatus(id, 0);

            Bukkit.getOnlinePlayers().forEach(players -> player.showPlayer(plugin, players));
        }
    }),
    CHAT((plugin, user) -> {
        String id = "chat";

        if (user.getSettingStatus(id) == 0) {
            user.updateSettingStatus(id, 1);

            return;
        }

        if (user.getSettingStatus(id) == 1) {
            user.updateSettingStatus(id, 0);
        }

    }),
    DOUBLE_JUMP((plugin, user) -> {
        Player player = user.getPlayer();
        String id = "double-jump";

        if (user.getSettingStatus(id) == 0) {
            player.sendMessage("No puedes interactuar con esta opciones si tienes activado la opciones de double jump.");
            player.closeInventory();

            return;
        }

        if (user.getSettingStatus(id) == 0) {
            user.updateSettingStatus(id, 1);

            return;
        }

        if (user.getSettingStatus(id) == 1) {
            user.updateSettingStatus(id, 0);
        }
    }),
    MOUNT((plugin, user) -> {
        String id = "mount";

        if (user.getSettingStatus(id) == 0) {
            user.updateSettingStatus(id, 1);

            return;
        }

        if (user.getSettingStatus(id) == 1) {
            user.updateSettingStatus(id, 0);
        }
    }),
    FLY((plugin, user) -> {
        Player player = user.getPlayer();
        String id = "fly";

        if (user.getSettingStatus("double-jump") == 0) {
            player.sendMessage("No puedes interactuar con esta opciones si tienes activado la opciones de double jump.");
            player.closeInventory();

            return;
        }

        if (user.getSettingStatus(id) == 0) {
            user.updateSettingStatus(id, 1);

            player.setAllowFlight(false);
            player.setFlying(false);

            return;
        }

        if (user.getSettingStatus(id) == 1) {
            user.updateSettingStatus(id, 0);

            player.setAllowFlight(true);
            player.setFlying(true);
        }
    });

    private final SettingEvent action;

    EventType(SettingEvent action) {
        this.action = action;
    }

    public SettingEvent getAction() {
        return action;
    }
}
