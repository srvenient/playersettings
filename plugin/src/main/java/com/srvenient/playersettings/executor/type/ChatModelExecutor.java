package com.srvenient.playersettings.executor.type;

import com.srvenient.playersettings.executor.SettingExecutor;
import com.srvenient.playersettings.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ChatModelExecutor implements SettingExecutor {

    @Override
    public @NotNull String getId() {
        return "chat";
    }

    @Override
    public void execute(@NotNull User user) {
        if (user.getSettingState(getId()) == 0) {
            user.updateState(getId(), (byte) 1);

            return;
        }

        if (user.getSettingState(getId()) == 1) {
            user.updateState(getId(), (byte) 0);
        }
    }
}
