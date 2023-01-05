package team.emptyte.mercurysettings.api.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.emptyte.mercurysettings.api.registry.Keyed;
import team.emptyte.mercurysettings.api.user.internal.Setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class User implements Keyed {

    private final String id;

    private Map<String, Setting> settings;

    public User(String id) {
        this.id = id;

        this.settings = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(id));
    }

    public void setSettings(Map<String, Setting> settings) {
        this.settings = settings;
    }

    public Setting getSetting(String key) {
        return settings.get(key);
    }

    public void changeState(String key, byte value) {
        settings.get(key).setState(value);
    }

    public Map<String, Setting> getSettings() {
        return settings;
    }
}
