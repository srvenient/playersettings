package team.emptyte.playersettings.api.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import team.emptyte.playersettings.api.storage.model.Model;
import team.emptyte.playersettings.api.user.internal.SettingStorage;

import java.util.HashMap;

import java.util.Map;
import java.util.UUID;

public class User implements Model {

    private final String id;

    private Map<String, SettingStorage> settings;

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

    public void setSettings(Map<String, SettingStorage> settings) {
        this.settings = settings;
    }

    public SettingStorage getSetting(String key) {
        return settings.get(key);
    }

    public void changeState(String key, byte value) {
        settings.get(key).setState(value);
    }

    public Map<String, SettingStorage> getSettings() {
        return settings;
    }
}
