package com.srvenient.playersettings.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record User(@NotNull UUID id, @NotNull Map<String, Byte> settings) {

    @NotNull
    public Player getPlayer() {
        return Objects.requireNonNull(Bukkit.getPlayer(id));
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
