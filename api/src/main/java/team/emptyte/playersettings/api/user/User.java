package team.emptyte.playersettings.api.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.emptyte.playersettings.api.storage.model.Model;

import java.util.HashMap;

import java.util.Map;
import java.util.UUID;

public class User implements Model {

    private final String id;

    private Map<String, Integer> settings;

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

    public Map<String, Integer> getSettings() {
        return settings;
    }

    public Integer getSettingStatus(String id) {
        return settings.get(id);
    }

    public void updateSettingStatus(String id, Integer status) {
        settings.put(id, status);
    }

    public void removeSettingStatus(String id) {
        settings.remove(id);
    }

    public void setSettings(Map<String, Integer> settings) {
        this.settings = settings;
    }

}
