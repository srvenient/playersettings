package team.emptyte.playersettings.api.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import team.emptyte.playersettings.api.storage.model.Model;

import java.util.HashMap;

import java.util.Map;
import java.util.UUID;

public class User implements Model {

    private final String id;

    private Map<String, Byte> settings;

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

    public Map<String, Byte> getSettings() {
        return settings;
    }

    public byte getSettingStatus(String id) {
        return settings.get(id);
    }

    public void updateSettingStatus(String id, byte status) {
        settings.put(id, status);
    }

    public void removeSettingStatus(String id) {
        settings.remove(id);
    }

    public void setSettings(Map<String, Byte> settings) {
        this.settings = settings;
    }
}
