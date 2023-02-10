package com.srvenient.playersettings.executor;

import com.srvenient.playersettings.user.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface SettingExecutor {

    @NotNull String getId();

    void execute(
            @NotNull final User user
    );
    
}
