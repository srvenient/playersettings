package team.emptyte.mercurysettings.api.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.emptyte.mercurysettings.api.user.internal.Setting;

public enum EventType {
    VISIBILITY((plugin, user) -> {
        Player player = user.getPlayer();
        String id = "visibility";
        Setting setting = user.getSetting(id);

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
        Setting setting = user.getSetting(id);

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
        Setting setting = user.getSetting(id);

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
        Setting setting = user.getSetting(id);

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
        Setting setting = user.getSetting(id);

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

    private final EventAction action;

    EventType(EventAction action) {
        this.action = action;
    }

    public EventAction getAction() {
        return action;
    }
}
