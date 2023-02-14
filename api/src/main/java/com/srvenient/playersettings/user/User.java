package com.srvenient.playersettings.user;

import com.srvenient.playersettings.storage.model.Model;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class User implements Model {

    @NotNull
    private final String id;

    private final Map<String, Byte> settings;

    public User(@NotNull String id) {
        this.id = id;
        this.settings = new HashMap<>();
    }

    public User(@NotNull String id, Map<String, Byte> settings) {
        this.id = id;
        this.settings = settings;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @NotNull
    public Player getPlayer() {
        return Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(id)));
    }

    public Map<String, Byte> getSettings() {
        return settings;
    }

    @NotNull
    public Byte getSettingState(final String settingId) {
        return settings.get(settingId);
    }

    public void updateState(
            final @NotNull String settingId,
            byte state
    ) {
        settings.put(settingId, state);
    }

}
