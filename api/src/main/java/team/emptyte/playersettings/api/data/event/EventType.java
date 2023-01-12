package team.emptyte.playersettings.api.data.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.emptyte.playersettings.api.user.internal.SettingStorage;

public enum EventType {
    VISIBILITY((plugin, user) -> {
        Player player = user.getPlayer();
        String id = "visibility";
        SettingStorage setting = user.getSetting(id);

        if (setting.getState() == (byte) 0) {
            user.changeState(id, (byte) 1);

            Bukkit.getOnlinePlayers().forEach(players -> {
                if (!players.hasPermission("mercurysettings.rank")) {
                    player.hidePlayer(plugin, players);
                } else {
                    player.showPlayer(plugin, players);
                }
            });

            return;
        }

        if (setting.getState() == (byte) 1) {
            user.changeState(id, (byte) 2);

            Bukkit.getOnlinePlayers().forEach(players -> player.hidePlayer(plugin, players));

            return;
        }

        if (setting.getState() == (byte) 2) {
            user.changeState(id, (byte) 0);

            Bukkit.getOnlinePlayers().forEach(players -> player.showPlayer(plugin, players));
        }
    }),
    CHAT((plugin, user) -> {
        String id = "chat";
        SettingStorage setting = user.getSetting(id);

        if (setting.getState() == (byte) 0) {
            user.changeState(id, (byte) 1);

            return;
        }

        if (setting.getState() == (byte) 1) {
            user.changeState(id, (byte) 2);

            return;
        }

        if (setting.getState() == (byte) 2) {
            user.changeState(id, (byte) 0);
        }
    }),
    DOUBLE_JUMP((plugin, user) -> {
        Player player = user.getPlayer();
        String id = "double-jump";
        SettingStorage setting = user.getSetting(id);

        if (user.getSetting("fly").getState() == 0) {
            player.sendMessage("No puedes interactuar con esta opciones si tienes activado la opciones de double jump.");
            player.closeInventory();

            return;
        }

        if (setting.getState() == (byte) 0) {
            user.changeState(id, (byte) 1);

            return;
        }

        if (setting.getState() == (byte) 1) {
            user.changeState(id, (byte) 0);
        }
    }),
    MOUNT((plugin, user) -> {
        String id = "mount";
        SettingStorage setting = user.getSetting(id);

        if (setting.getState() == (byte) 0) {
            user.changeState(id, (byte) 1);

            return;
        }

        if (setting.getState() == (byte) 1) {
            user.changeState(id, (byte) 0);
        }
    }),
    FLY((plugin, user) -> {
        Player player = user.getPlayer();
        String id = "fly";
        SettingStorage setting = user.getSetting(id);

        if (user.getSetting("double-jump").getState() == 0) {
            player.sendMessage("No puedes interactuar con esta opciones si tienes activado la opciones de double jump.");
            player.closeInventory();

            return;
        }

        if (setting.getState() == 0) {
            user.changeState(id, (byte) 1);

            player.setAllowFlight(false);
            player.setFlying(false);

            return;
        }

        if (setting.getState() == 1) {
            user.changeState(id, (byte) 0);

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
