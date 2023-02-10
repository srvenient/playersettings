package com.srvenient.playersettings.executor.type;

import com.srvenient.playersettings.executor.SettingExecutor;
import com.srvenient.playersettings.user.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class FlyModelExecutor implements SettingExecutor {

    @Override
    public @NotNull String getId() {
        return "fly";
    }

    @Override
    public void execute(@NotNull User user) {
        Player player = user.getPlayer();

        if (user.getSettingState(getId()) == 0) {
            user.updateState(getId(), (byte) 1);

            player.setAllowFlight(false);
            player.setFlying(false);

            return;
        }

        if (user.getSettingState(getId()) == 1) {
            user.updateState(getId(), (byte) 0);

            player.setAllowFlight(true);
            player.setFlying(true);
        }
    }
}
