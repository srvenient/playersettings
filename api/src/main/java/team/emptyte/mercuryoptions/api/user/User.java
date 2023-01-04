package team.emptyte.mercuryoptions.api.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.emptyte.mercuryoptions.api.registry.Keyed;

import java.util.Map;
import java.util.UUID;

public class User implements Keyed {

    private final String id;

    private Map<String, Byte> settings;

    public User(String id) {
        this.id = id;
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


    public Byte getSetting(@NotNull String key) {
        return settings.get(key);
    }

    public void setSettings(Map<String, Byte> settings) {
        this.settings = settings;
    }
}
